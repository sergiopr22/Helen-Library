package com.sergio.springboot.biblioteca2.springboot_biblioteca2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sergio.springboot.biblioteca2.springboot_biblioteca2.entities.Libro;
import com.sergio.springboot.biblioteca2.springboot_biblioteca2.services.LibroService;

@SpringBootApplication
public class SpringbootBiblioteca2Application implements CommandLineRunner{

	private final LibroService libroService;

	public SpringbootBiblioteca2Application(LibroService libroService) {
		this.libroService = libroService;
	}


	public static void main(String[] args) {
		SpringApplication.run(SpringbootBiblioteca2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("📚 Listado de libros en la base de datos:");
		for (Libro libro : libroService.listarLibros()) {
            System.out.println(libro);
        }
	}

}
