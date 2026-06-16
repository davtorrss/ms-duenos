package com.vetnova.ms_duenos.repository;

import com.vetnova.ms_duenos.model.Dueno;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DuenoRepository extends JpaRepository<Dueno, Long> {

    boolean existsByRut(String rut);
}