package com.sergio.springboot.biblioteca2.springboot_biblioteca2.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sergio.springboot.biblioteca2.springboot_biblioteca2.entities.Libro;
import com.sergio.springboot.biblioteca2.springboot_biblioteca2.repositories.LibroRepository;

@Service
public class LibroService {

    private final LibroRepository libroRepository;

    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    public List<Libro> listarLibros() {
        return (List<Libro>) libroRepository.findAll();
    }

    public Libro actualizarLibro(Long id, Libro libroActualizado) {
    return libroRepository.findById(id).map(libro -> {
        libro.setTitulo(libroActualizado.getTitulo());
        libro.setAutor(libroActualizado.getAutor());
        libro.setGenero(libroActualizado.getGenero());
        libro.setPaginas(libroActualizado.getPaginas());
        libro.setLeido(libroActualizado.isLeido());
        return libroRepository.save(libro);
    }).orElseThrow(() -> new RuntimeException("Libro no encontrado con id " + id));
}

    public Libro guardarLibro(Libro libro) {
        return libroRepository.save(libro);
    }

    public void eliminarLibro(Long id) {
        libroRepository.deleteById(id);
    }

    public Libro obtenerPorId(Long id) {
        return libroRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Libro no encontrado con id " + id));
    }

    public Libro eliminarValoracionDeLibro(Long libroId) {
        Libro libro = libroRepository.findById(libroId)
            .orElseThrow(() -> new RuntimeException("Libro no encontrado con id " + libroId));

        libro.setValoracion(null); // elimina la referencia
        return libroRepository.save(libro); // gracias a orphanRemoval, también borra la fila de valoracion
    }
}
