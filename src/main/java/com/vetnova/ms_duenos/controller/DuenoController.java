package com.vetnova.ms_duenos.controller;

import com.vetnova.ms_duenos.dto.DuenoRequestDTO;
import com.vetnova.ms_duenos.dto.DuenoResponseDTO;
import com.vetnova.ms_duenos.service.DuenoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/duenos")
public class DuenoController {

    @Autowired
    private DuenoService duenoService;

    @GetMapping
    public ResponseEntity<List<DuenoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(duenoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DuenoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return duenoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DuenoResponseDTO> crearDueno(@RequestBody DuenoRequestDTO dto) {
        return new ResponseEntity<>(duenoService.registrar(dto), HttpStatus.CREATED);
    }
}