package com.sergio.springboot.biblioteca2.springboot_biblioteca2.controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sergio.springboot.biblioteca2.springboot_biblioteca2.entities.Libro;
import com.sergio.springboot.biblioteca2.springboot_biblioteca2.entities.Valoracion;
import com.sergio.springboot.biblioteca2.springboot_biblioteca2.services.LibroService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @GetMapping
    public List<Libro> obtenerLibros() {
        return libroService.listarLibros();
    }

    @PostMapping
    public ResponseEntity<Libro> agregarLibro(@Valid @RequestBody Libro libro) {
        Libro nuevo = libroService.guardarLibro(libro);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarLibro(@PathVariable Long id, @Valid @RequestBody Libro libro) {
        return ResponseEntity.ok(libroService.actualizarLibro(id, libro));
    }

    @DeleteMapping("/{id}")
    public void eliminarLibro(@PathVariable Long id) {
        libroService.eliminarLibro(id);
    }

    @GetMapping("/{id}/valoracion")
    public Valoracion obtenerValoracion(@PathVariable Long id) {
        Libro libro = libroService.obtenerPorId(id);
        return libro.getValoracion();
    }

    @PutMapping("/{id}/valoracion")
    public Libro actualizarValoracion(@PathVariable Long id, @RequestBody Valoracion valoracion) {
        Libro libro = libroService.obtenerPorId(id);
        libro.setValoracion(valoracion);
        return libroService.guardarLibro(libro);
    }

    @DeleteMapping("/{id}/valoracion")
    public Libro eliminarValoracion(@PathVariable Long id) {
        return libroService.eliminarValoracionDeLibro(id);
    }

    // Endpoint de búsqueda
    @GetMapping("/buscar")
    public List<Libro> buscarLibros(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String autor,
            @RequestParam(required = false) String genero) {
        return libroService.buscarLibros(titulo, autor, genero);
    }

    @GetMapping("/filtrar")
    public List<Libro> filtrarLibros(@RequestParam boolean leido) {
        return libroService.filtrarPorLeido(leido);
    }

    @GetMapping("/paginado")
    public Page<Libro> obtenerLibrosPaginados(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        @RequestParam(defaultValue = "titulo") String sortBy) {
        return libroService.listarLibrosPaginados(page, size, sortBy);
    }
}
