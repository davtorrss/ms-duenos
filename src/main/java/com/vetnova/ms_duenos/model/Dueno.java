package com.vetnova.ms_duenos.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Data
@Table(name = "duenos")
public class Dueno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El RUT es obligatorio y no puede estar vacío")
    private String rut;

    @NotBlank(message = "El nombre del dueño es obligatorio")
    private String nombre;

    @NotBlank(message = "El teléfono no puede estar vacío")
    private String telefono;
}