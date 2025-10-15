package com.sergio.springboot.biblioteca2.springboot_biblioteca2.repositories;

import org.springframework.data.repository.CrudRepository;

import com.sergio.springboot.biblioteca2.springboot_biblioteca2.entities.Libro;

public interface LibroRepository extends CrudRepository<Libro, Long> {

}
