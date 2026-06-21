package com.vetnova.ms_duenos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DuenoResponseDTO {
    private Long id;
    private String rut;
    private String nombre;
    private String telefono;
    private String direccion;
}