package com.vetnova.ms_duenos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DuenoRequestDTO {

    @NotBlank(message = "El RUT es obligatorio.")
    @Pattern(regexp = "^[0-9]{7,8}-[0-9kK]{1}$", message = "El formato del RUT debe ser 12345678-K")
    private String rut;

    @NotBlank(message = "El nombre no puede estar vacío.")
    private String nombre;

    @NotBlank(message = "El teléfono es obligatorio.")
    @Pattern(regexp = "^\\+?56[9]\\d{8}$", message = "El teléfono debe cumplir con el formato chileno (+56912345678)")
    private String telefono;

    @NotBlank(message = "La dirección es obligatoria.")
    private String direccion;
}