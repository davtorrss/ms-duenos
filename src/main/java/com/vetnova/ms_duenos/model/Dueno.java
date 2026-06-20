package com.vetnova.ms_duenos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "duenos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dueno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 12)
    private String rut;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 9)
    private String telefono;

    private String direccion;
}