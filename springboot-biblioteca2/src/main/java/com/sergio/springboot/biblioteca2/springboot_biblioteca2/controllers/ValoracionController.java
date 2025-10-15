package com.sergio.springboot.biblioteca2.springboot_biblioteca2.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sergio.springboot.biblioteca2.springboot_biblioteca2.entities.Valoracion;
import com.sergio.springboot.biblioteca2.springboot_biblioteca2.services.ValoracionService;

@RestController
@RequestMapping("/api/valoraciones")
public class ValoracionController {

    private final ValoracionService valoracionService;

    public ValoracionController(ValoracionService valoracionService) {
        this.valoracionService = valoracionService;
    }

    @GetMapping
    public List<Valoracion> obtenerValoraciones() {
        return valoracionService.listarValoraciones();
    }

    @PostMapping
    public Valoracion agregarValoracion(@RequestBody Valoracion valoracion) {
        return valoracionService.guardarValoracion(valoracion);
    }

    @DeleteMapping("/{id}")
    public void eliminarValoracion(@PathVariable Long id) {
        valoracionService.eliminarValoracion(id);
    }

    @PutMapping("/{id}")
    public Valoracion modificarValoracion(@PathVariable Long id, @RequestBody Valoracion valoracion) {
        return valoracionService.actualizarValoracion(id, valoracion);
    }
}
