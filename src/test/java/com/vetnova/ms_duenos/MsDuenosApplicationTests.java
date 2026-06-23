package com.vetnova.ms_duenos;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vetnova.ms_duenos.dto.DuenoRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) 
@Transactional
class MsDuenosApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Integración: Flujo completo de registro de Dueño real en Base de Datos")
    void registrarDueno_FlujoCompleto_Retorna201() throws Exception {
        
        DuenoRequestDTO nuevoDueno = new DuenoRequestDTO(
                "19876543-2",
                "Juan Integración",
                "+56999999999",
                "Calle Verdadera 123"
        );

       
        mockMvc.perform(post("/api/duenos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevoDueno)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.rut").value("19876543-2"))
                .andExpect(jsonPath("$.nombre").value("Juan Integración"))
                .andExpect(jsonPath("$.id").exists()); // Confirmamos que MySQL le asignó un ID real
    }

    @Test
    @DisplayName("Integración: Falla al registrar RUT duplicado en Base de Datos real")
    void registrarDueno_RUTDuplicado_Retorna400() throws Exception {
        // 1. Preparamos los datos
        DuenoRequestDTO dueñoDuplicado = new DuenoRequestDTO(
                "11222333-4",
                "María Copia",
                "+56988888888",
                "Avenida Siempre Viva 742"
        );

       
        mockMvc.perform(post("/api/duenos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dueñoDuplicado)))
                .andExpect(status().isCreated());

       
        mockMvc.perform(post("/api/duenos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dueñoDuplicado)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("El RUT ingresado ya se encuentra registrado."));
    }
}