package com.vetnova.ms_duenos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class DuenoRequestDTO {

    @NotBlank(message = "El RUT es obligatorio")
    @Size(min = 8, max = 12, message = "El RUT debe tener entre 8 y 12 caracteres")
    private String rut;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String nombre;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^9\\d{8}$", message = "El teléfono debe empezar con 9 y tener 9 dígitos")
    private String telefono;

    private String direccion;
}