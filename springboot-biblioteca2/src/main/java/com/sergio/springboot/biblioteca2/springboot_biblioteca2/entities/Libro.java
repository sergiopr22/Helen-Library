package com.sergio.springboot.biblioteca2.springboot_biblioteca2.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import jakarta.validation.constraints.*;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título no puede estar vacío")
    private String titulo;

    @NotBlank(message = "El autor no puede estar vacío")
    private String autor;

    @NotBlank(message = "El género no puede estar vacío")
    private String genero;

    @Min(value = 1, message = "El número de páginas debe ser al menos 1")
    private int paginas;

    private boolean leido;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "valoracion_id")
    private Valoracion valoracion;

    public Libro() {

    }

    public Libro(String titulo, String autor, String genero, int paginas, boolean leido) {
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.paginas = paginas;
        this.leido = leido;
    }

    public Long getId() {
         return id; 
    }

    public void setId(Long id) {
         this.id = id; 
    }

    public String getTitulo() {
         return titulo; 
    }

    public void setTitulo(String titulo) {
         this.titulo = titulo; 
    }

    public String getAutor() {
         return autor; 
    }

    public void setAutor(String autor) {
         this.autor = autor; 
    }

    public String getGenero() {
         return genero; 
    }

    public void setGenero(String genero) {
         this.genero = genero; 
    }

    public int getPaginas() {
         return paginas; 
    }

    public void setPaginas(int paginas) {
         this.paginas = paginas; 
    }

    public boolean isLeido() {
         return leido; 
    }
    
    public void setLeido(boolean leido) {
         this.leido = leido; 
    }

    public Valoracion getValoracion() {
        return valoracion;
    }

    public void setValoracion(Valoracion valoracion) {
        this.valoracion = valoracion;
    }

    @Override
    public String toString() {
        return "Libro {id=" + id + ", titulo=" + titulo + ", autor=" + autor + ", genero=" + genero + ", paginas=" + paginas + ", leido=" + leido + "}";
    }

    
}
