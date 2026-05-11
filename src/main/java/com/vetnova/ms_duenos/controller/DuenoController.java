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
    public ResponseEntity<Dueno> registrar(@Valid @RequestBody Dueno d) {
        Dueno nuevo = duenoService.guardarDueno(d);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Dueno>> listar() {
        return ResponseEntity.ok(duenoService.obtenerTodos());
    }
}