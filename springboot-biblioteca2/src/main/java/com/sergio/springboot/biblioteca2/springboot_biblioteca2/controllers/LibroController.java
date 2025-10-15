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

import com.sergio.springboot.biblioteca2.springboot_biblioteca2.entities.Libro;
import com.sergio.springboot.biblioteca2.springboot_biblioteca2.entities.Valoracion;
import com.sergio.springboot.biblioteca2.springboot_biblioteca2.services.LibroService;

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

    @PutMapping("/{id}")
    public Libro modificarLibro(@PathVariable Long id, @RequestBody Libro libro) {
        return libroService.actualizarLibro(id, libro);
    }

    @PostMapping
    public Libro agregarLibro(@RequestBody Libro libro) {
        return libroService.guardarLibro(libro);
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
}
