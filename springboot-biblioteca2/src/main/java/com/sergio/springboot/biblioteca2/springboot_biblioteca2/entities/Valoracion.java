package com.sergio.springboot.biblioteca2.springboot_biblioteca2.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "valoraciones")
public class Valoracion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int estrellas;
    private String critica;
    private String cosasADestacar;

    public Valoracion() {

    }

    public Valoracion(int estrellas, String critica, String cosasADestacar) {
        this.estrellas = estrellas;
        this.critica = critica;
        this.cosasADestacar = cosasADestacar;
    }

    

    public Valoracion(Long id, int estrellas, String critica, String cosasADestacar) {
        this.id = id;
        this.estrellas = estrellas;
        this.critica = critica;
        this.cosasADestacar = cosasADestacar;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getEstrellas() {
        return estrellas;
    }

    public void setEstrellas(int estrellas) {
        this.estrellas = estrellas;
    }

    public String getCritica() {
        return critica;
    }

    public void setCritica(String critica) {
        this.critica = critica;
    }

    public String getCosasADestacar() {
        return cosasADestacar;
    }

    public void setCosasADestacar(String cosasADestacar) {
        this.cosasADestacar = cosasADestacar;
    }

    @Override
    public String toString() {
        return "Valoracion [id=" + id + ", estrellas=" + estrellas + ", critica=" + critica + ", cosasADestacar="
                + cosasADestacar + "]";
    }

    

}
