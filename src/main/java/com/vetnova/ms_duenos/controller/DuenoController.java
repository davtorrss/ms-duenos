package com.vetnova.ms_duenos.controller;

import com.vetnova.ms_duenos.model.Dueno;
import com.vetnova.ms_duenos.service.DuenoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/duenos")
public class DuenoController {

    @Autowired
    private DuenoService duenoService;

    @GetMapping
    public ResponseEntity<List<Dueno>> listarTodos() {
        return ResponseEntity.ok(duenoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dueno> obtenerPorId(@PathVariable Long id) {
        return duenoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build()); 
    }

    @PostMapping
    public ResponseEntity<Dueno> crearDueno(@RequestBody Dueno dueno) {
        return ResponseEntity.ok(duenoService.guardarDueno(dueno));
    }
}