package com.sergio.springboot.biblioteca2.springboot_biblioteca2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.databind.JsonMappingException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Captura errores de validación (Bean Validation)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
            errores.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(errores);
    }

    // Captura cualquier otra excepción genérica
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Ocurrió un error inesperado");
        error.put("detalle", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleInvalidJson(HttpMessageNotReadableException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Error en el formato del JSON");

        // Intentamos extraer el campo que causó el problema
        Throwable cause = ex.getMostSpecificCause();
        String mensaje;
        if (cause instanceof JsonMappingException) {
            JsonMappingException jme = (JsonMappingException) cause;
            if (!jme.getPath().isEmpty()) {
                // Tomamos el primer campo donde falló la conversión
                String campo = jme.getPath().get(0).getFieldName();
                mensaje = "Se esperaba un valor válido para el campo '" + campo + "'";
            } else {
                mensaje = "JSON mal formado";
            }
        } else {
            mensaje = "JSON mal formado";
        }

        error.put("detalle", mensaje);
        return ResponseEntity.badRequest().body(error);
    }
}
