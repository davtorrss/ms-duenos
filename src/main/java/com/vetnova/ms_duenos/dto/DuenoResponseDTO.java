package com.vetnova.ms_duenos.dto;


public class DuenoResponseDTO {
    private Long id;
    private String rut;
    private String nombre;
    private String telefono;
    private String direccion;

    public DuenoResponseDTO() {}


    public DuenoResponseDTO(Long id, String rut, String nombre, String telefono, String direccion) {
        this.id = id;
        this.rut = rut;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    
    public Long getId() { return id; }
    public String getRut() { return rut; }
    public String getNombre() { return nombre; }
    public String getTelefono() { return telefono; }
    public String getDireccion() { return direccion; }

    
    public void setId(Long id) { this.id = id; }
    public void setRut(String rut) { this.rut = rut; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
}