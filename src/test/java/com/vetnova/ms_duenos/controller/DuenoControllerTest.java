package com.vetnova.ms_duenos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vetnova.ms_duenos.dto.DuenoRequestDTO;
import com.vetnova.ms_duenos.dto.DuenoResponseDTO;
import com.vetnova.ms_duenos.security.JwtFilter;
import com.vetnova.ms_duenos.security.JwtUtil;
import com.vetnova.ms_duenos.service.DuenoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = DuenoController.class)
@AutoConfigureMockMvc(addFilters = false)
class DuenoControllerTest {

    @Autowired
    private MockMvc mockMvc; 

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DuenoService duenoService; 

   
    @MockBean
    private JwtUtil jwtUtil;
    @MockBean
    private JwtFilter jwtFilter;

    private DuenoRequestDTO requestDTO;
    private DuenoResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        requestDTO = new DuenoRequestDTO(
                "12345678-K",
                "Carlos Perez",
                "+56912345678",
                "Concepcion"
        );

        responseDTO = new DuenoResponseDTO(
                1L,
                "12345678-K",
                "Carlos Perez",
                "+56912345678",
                "Concepcion"
        );
    }

    @Test
    @DisplayName("POST /api/duenos - Debe crear un dueño y retornar 201 Created")
    void crear_Retorna201() throws Exception {
        when(duenoService.registrar(any(DuenoRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/duenos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.rut").value("12345678-K"))
                .andExpect(jsonPath("$.nombre").value("Carlos Perez"));
    }

    @Test
    @DisplayName("GET /api/duenos - Debe listar dueños y retornar 200 OK")
    void listarTodos_Retorna200() throws Exception {
        when(duenoService.listarTodos()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/api/duenos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].rut").value("12345678-K"));
    }

    @Test
    @DisplayName("GET /api/duenos/{id} - Debe buscar por ID y retornar 200 OK")
    void buscarPorId_Retorna200() throws Exception {
        when(duenoService.buscarPorId(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/duenos/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Carlos Perez"));
    }

    @Test
    @DisplayName("PUT /api/duenos/{id} - Debe actualizar y retornar 200 OK")
    void actualizar_Retorna200() throws Exception {
        when(duenoService.actualizar(eq(1L), any(DuenoRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/api/duenos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.direccion").value("Concepcion"));
    }

    @Test
    @DisplayName("DELETE /api/duenos/{id} - Debe eliminar y retornar 204 No Content")
    void eliminar_Retorna204() throws Exception {
       
        
        mockMvc.perform(delete("/api/duenos/1"))
                .andExpect(status().isNoContent());
    }
}