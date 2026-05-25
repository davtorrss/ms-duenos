package com.vetnova.ms_duenos.dto;
import lombok.Data;

@Data
public class DuenoRequestDTO { 
    private String rut;
    private String nombre;
    private String telefono;
    private String direccion;
}