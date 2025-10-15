package com.sergio.springboot.biblioteca2.springboot_biblioteca2.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sergio.springboot.biblioteca2.springboot_biblioteca2.entities.Valoracion;
import com.sergio.springboot.biblioteca2.springboot_biblioteca2.repositories.ValoracionRepository;

@Service
public class ValoracionService {

    private final ValoracionRepository valoracionRepository;

    public ValoracionService(ValoracionRepository valoracionRepository) {
        this.valoracionRepository = valoracionRepository;
    }

    public List<Valoracion> listarValoraciones() {
        return (List<Valoracion>) valoracionRepository.findAll();
    }

    public Valoracion guardarValoracion(Valoracion valoracion) {
        return valoracionRepository.save(valoracion);
    }

    public void eliminarValoracion(Long id) {
        valoracionRepository.deleteById(id);
    }

    public Valoracion actualizarValoracion(Long id, Valoracion valoracionActualizada) {
        return valoracionRepository.findById(id).map(valoracion -> {
            valoracion.setEstrellas(valoracionActualizada.getEstrellas());
            valoracion.setCritica(valoracionActualizada.getCritica());
            valoracion.setCosasADestacar(valoracionActualizada.getCosasADestacar());
            return valoracionRepository.save(valoracion);
    }).orElseThrow(() -> new RuntimeException("Valoración no encontrada con id " + id));
}
}
