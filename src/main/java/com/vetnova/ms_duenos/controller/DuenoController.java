package com.vetnova.ms_duenos.controller;

import com.vetnova.ms_duenos.dto.DuenoRequestDTO;
import com.vetnova.ms_duenos.dto.DuenoResponseDTO;
import com.vetnova.ms_duenos.service.DuenoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/duenos")
public class DuenoController {

    @Autowired
    private DuenoService duenoService;

    @PostMapping
    public ResponseEntity<DuenoResponseDTO> crear(@Valid @RequestBody DuenoRequestDTO dto) {
        log.info("Petición REST recibida para crear dueño");
        return new ResponseEntity<>(duenoService.registrar(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DuenoResponseDTO>> listarTodos() {
        log.info("Petición REST recibida para listar dueños");
        return ResponseEntity.ok(duenoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DuenoResponseDTO> buscarPorId(@PathVariable Long id) {
        log.info("Petición REST recibida para buscar dueño ID: {}", id);
        return ResponseEntity.ok(duenoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DuenoResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody DuenoRequestDTO dto) {
        log.info("Petición REST recibida para actualizar dueño ID: {}", id);
        return ResponseEntity.ok(duenoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        log.info("Petición REST recibida para eliminar dueño ID: {}", id);
        duenoService.eliminar(id);
        return ResponseEntity.ok("Dueño eliminado correctamente");
    }
}