package com.vetnova.ms_duenos.controller;

import com.vetnova.ms_duenos.dto.*;
import com.vetnova.ms_duenos.service.DuenoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/duenos")
public class DuenoController {
    @Autowired private DuenoService duenoService;

    @PostMapping
    public ResponseEntity<DuenoResponseDTO> crear(@RequestBody DuenoRequestDTO dto) { return new ResponseEntity<>(duenoService.registrar(dto), HttpStatus.CREATED); }

    @GetMapping
    public ResponseEntity<List<DuenoResponseDTO>> listarTodos() { return ResponseEntity.ok(duenoService.listarTodos()); }

    @PutMapping("/{id}")
    public ResponseEntity<DuenoResponseDTO> actualizar(@PathVariable Long id, @RequestBody DuenoRequestDTO dto) { return ResponseEntity.ok(duenoService.actualizar(id, dto)); }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) { duenoService.eliminar(id); return ResponseEntity.ok("Dueño eliminado exitosamente."); }
}