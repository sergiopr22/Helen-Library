package com.sergio.springboot.biblioteca2.springboot_biblioteca2.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sergio.springboot.biblioteca2.springboot_biblioteca2.entities.Libro;
import com.sergio.springboot.biblioteca2.springboot_biblioteca2.entities.Valoracion;
import com.sergio.springboot.biblioteca2.springboot_biblioteca2.services.LibroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.*;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Anotación clave para probar solo la capa web (Controller)
@WebMvcTest(LibroController.class)
public class LibroControllerTest {

    @Autowired
    private MockMvc mockMvc; // Simula peticiones HTTP

    @Autowired
    private ObjectMapper objectMapper; // Para convertir objetos Java a JSON

    @MockBean
    private LibroService libroService; // Mock de la capa de servicio

    private Libro libro1;
    private Libro libro2;
    private Valoracion valoracion1;

    @BeforeEach
    void setup() {
        // Datos de prueba
        valoracion1 = new Valoracion(1L, 5, "Excelente", "Final inesperado");
        
        libro1 = new Libro(1L, "La sombra del viento", "Carlos Ruiz Zafón", "Misterio", 500, true);
        libro1.setValoracion(valoracion1);
        
        libro2 = new Libro(2L, "Cien años de soledad", "Gabriel García Márquez", "Realismo Mágico", 400, false);
    }

    // --- TEST para GET /api/libros ---
    @Test
    void obtenerLibros_DebeRetornarListaDeLibros() throws Exception {
        List<Libro> libros = Arrays.asList(libro1, libro2);

        // 1. Configurar el Mock: Cuando se llame a listarLibros(), devolver nuestra lista.
        when(libroService.listarLibros()).thenReturn(libros);

        // 2. Ejecutar la Petición y Assert
        mockMvc.perform(get("/api/libros"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$", hasSize(2)))
               .andExpect(jsonPath("$[0].titulo", is(libro1.getTitulo())));

        // 3. Verificar que el método del servicio fue llamado
        verify(libroService, times(1)).listarLibros();
    }
    
    // --- TEST para POST /api/libros ---
    @Test
    void agregarLibro_DebeCrearNuevoLibroYRetornar201() throws Exception {
        Libro nuevoLibro = new Libro(null, "Nuevo Título", "Autor Prueba", "Ficción", 300, false);
        Libro libroGuardado = new Libro(3L, "Nuevo Título", "Autor Prueba", "Ficción", 300, false);

        // 1. Configurar el Mock
        when(libroService.guardarLibro(any(Libro.class))).thenReturn(libroGuardado);

        // 2. Ejecutar la Petición
        mockMvc.perform(post("/api/libros")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(nuevoLibro)))
               .andExpect(status().isCreated()) // Espera HttpStatus.CREATED (201)
               .andExpect(jsonPath("$.id", is(3)))
               .andExpect(jsonPath("$.titulo", is("Nuevo Título")));
    }
    
    // NOTA: Para probar la validación (@Valid), necesitarías usar @AutoConfigureMockMvc 
    // y @SpringBootTest o usar un método específico en @WebMvcTest, pero es más simple 
    // probar la lógica de validación unitariamente.

    // --- TEST para PUT /api/libros/{id} ---
    @Test
    void actualizarLibro_DebeActualizarYRetornar200() throws Exception {
        Libro libroActualizadoInput = new Libro(null, "Título Editado", "Autor", "Género", 100, true);
        Libro libroActualizadoOutput = new Libro(1L, "Título Editado", "Autor", "Género", 100, true);

        // 1. Configurar el Mock
        when(libroService.actualizarLibro(eq(1L), any(Libro.class))).thenReturn(libroActualizadoOutput);

        // 2. Ejecutar la Petición
        mockMvc.perform(put("/api/libros/1")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(libroActualizadoInput)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.titulo", is("Título Editado")));
    }
    
    // --- TEST para DELETE /api/libros/{id} ---
    @Test
    void eliminarLibro_DebeEliminarYRetornar200() throws Exception {
        // 1. Configurar el Mock (no hace nada)
        doNothing().when(libroService).eliminarLibro(1L);

        // 2. Ejecutar la Petición
        mockMvc.perform(delete("/api/libros/1"))
               .andExpect(status().isOk()); // Por defecto, void retorna 200 (No Content es común)

        // 3. Verificar la llamada
        verify(libroService, times(1)).eliminarLibro(1L);
    }

    // --- TEST para GET /api/libros/{id}/valoracion ---
    @Test
    void obtenerValoracion_DebeRetornarValoracion() throws Exception {
        // 1. Configurar el Mock
        when(libroService.obtenerPorId(1L)).thenReturn(libro1);

        // 2. Ejecutar la Petición
        mockMvc.perform(get("/api/libros/1/valoracion"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.estrellas", is(5)))
               .andExpect(jsonPath("$.critica", is("Excelente")));
    }

    // --- TEST para Endpoint de búsqueda /api/libros/buscar ---
    @Test
    void buscarLibros_DebeRetornarListaFiltrada() throws Exception {
        List<Libro> librosEncontrados = List.of(libro1);

        // 1. Configurar el Mock (capturando los parámetros)
        when(libroService.buscarLibros(eq("Sombra"), isNull(), isNull())).thenReturn(librosEncontrados);

        // 2. Ejecutar la Petición
        mockMvc.perform(get("/api/libros/buscar")
                       .param("titulo", "Sombra"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(1)))
               .andExpect(jsonPath("$[0].autor", is(libro1.getAutor())));
        
        verify(libroService, times(1)).buscarLibros(eq("Sombra"), isNull(), isNull());
    }

    // --- TEST para Paginación /api/libros/paginado ---
    @Test
    void obtenerLibrosPaginados_DebeRetornarPagina() throws Exception {
        // 1. Configurar el Mock
        PageImpl<Libro> pagina = new PageImpl<>(List.of(libro1));
        when(libroService.listarLibrosPaginados(anyInt(), anyInt(), anyString())).thenReturn(pagina);

        // 2. Ejecutar la Petición con parámetros de paginación
        mockMvc.perform(get("/api/libros/paginado")
                       .param("page", "1")
                       .param("size", "10")
                       .param("sortBy", "titulo"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.content", hasSize(1)))
               .andExpect(jsonPath("$.content[0].titulo", is(libro1.getTitulo())));
    }
}