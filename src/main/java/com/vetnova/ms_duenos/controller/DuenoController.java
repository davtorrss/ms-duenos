package com.vetnova.ms_duenos.controller;

import com.vetnova.ms_duenos.model.Dueno;
import com.vetnova.ms_duenos.service.DuenoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/duenos")
public class DuenoController {

    @Autowired
    private DuenoService duenoService;

    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@Valid @RequestBody Dueno dueno) {
        try {
            Dueno nuevo = duenoService.registrarDueno(dueno);
            return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: El RUT ingresado ya está registrado o el formato es incorrecto.", HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Dueno>> listar() {
        return ResponseEntity.ok(duenoService.listarTodos());
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Dueno> actualizar(@PathVariable Long id, @Valid @RequestBody Dueno dueno) {
        return ResponseEntity.ok(duenoService.actualizar(id, dueno));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        duenoService.eliminar(id);
        return ResponseEntity.ok("Dueño eliminado correctamente.");
    }
}