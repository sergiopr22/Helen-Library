package com.sergio.springboot.biblioteca2.springboot_biblioteca2.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sergio.springboot.biblioteca2.springboot_biblioteca2.entities.Libro;

public interface LibroRepository extends CrudRepository<Libro, Long>, PagingAndSortingRepository<Libro, Long> {

    // Buscar por título (contiene, insensible a mayúsculas)
    List<Libro> findByTituloContainingIgnoreCase(String titulo);

    // Buscar por autor (contiene, insensible a mayúsculas)
    List<Libro> findByAutorContainingIgnoreCase(String autor);

    // Buscar por género (contiene, insensible a mayúsculas)
    List<Libro> findByGeneroContainingIgnoreCase(String genero);

    // Buscar combinando título, autor y género
    List<Libro> findByTituloContainingIgnoreCaseAndAutorContainingIgnoreCaseAndGeneroContainingIgnoreCase(
            String titulo, String autor, String genero);

    // Filtra libros por si han sido leídos o no
    List<Libro> findByLeido(boolean leido);
}
