package com.vetnova.ms_duenos.dto;

public class DuenoResponseDTO {
    private Long id;
    private String rut;
    private String nombre;
    private String telefono;
    private String direccion;

    
    public void setId(Long id) { this.id = id; }
    public void setRut(String rut) { this.rut = rut; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    
    
}