package com.vetnova.ms_duenos.controller;

import com.vetnova.ms_duenos.dto.DuenoRequestDTO;
import com.vetnova.ms_duenos.dto.DuenoResponseDTO;
import com.vetnova.ms_duenos.service.DuenoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/duenos")
@Tag(name = "Gestión de Dueños", description = "Endpoints para la administración de datos de clientes/dueños en VetNova")
public class DuenoController {

    @Autowired
    private DuenoService duenoService;

    @PostMapping
    @Operation(summary = "Registrar un nuevo dueño", description = "Permite ingresar un nuevo cliente validando RUT, email y formato de campos.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Dueño creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o RUT duplicado")
    })
    public ResponseEntity<DuenoResponseDTO> crear(@Valid @RequestBody DuenoRequestDTO dto) {
        log.info("Petición REST para registrar un dueño con RUT: {}", dto.getRut());
        return new ResponseEntity<>(duenoService.registrar(dto), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar todos los dueños", description = "Retorna una lista completa de los dueños registrados.")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    public ResponseEntity<List<DuenoResponseDTO>> listarTodos() {
        log.info("Petición REST para listar todos los dueños");
        return ResponseEntity.ok(duenoService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar dueño por ID", description = "Obtiene los detalles de un dueño específico mediante su identificador único.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Dueño encontrado"),
        @ApiResponse(responseCode = "404", description = "Dueño no encontrado")
    })
    public ResponseEntity<DuenoResponseDTO> buscarPorId(@PathVariable Long id) {
        log.info("Petición REST para buscar dueño con ID: {}", id);
        return ResponseEntity.ok(duenoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un dueño existente", description = "Modifica los datos de contacto o dirección de un dueño.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Dueño actualizado con éxito"),
        @ApiResponse(responseCode = "404", description = "Dueño no encontrado para actualizar")
    })
    public ResponseEntity<DuenoResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody DuenoRequestDTO dto) {
        log.info("Petición REST para actualizar dueño con ID: {}", id);
        return ResponseEntity.ok(duenoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un dueño", description = "Remueve lógicamente o físicamente un dueño del sistema de registros.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Dueño eliminado con éxito"),
        @ApiResponse(responseCode = "404", description = "Dueño no encontrado")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("Petición REST para eliminar dueño con ID: {}", id);
        duenoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}