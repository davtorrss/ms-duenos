package com.vetnova.ms_duenos.exception;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuenoNoEncontradoException.class)
    public ResponseEntity<String> manejarDuenoNoEncontrado(DuenoNoEncontradoException ex) {
        log.warn("Recurso no encontrado: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(RutDuplicadoException.class)
    public ResponseEntity<String> manejarRutDuplicado(RutDuplicadoException ex) {
        log.warn("Conflicto de negocio: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarValidaciones(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errores.put(error.getField(), error.getDefaultMessage());
        });
        log.warn("Error de validación de campos DTO: {}", errores);
        return ResponseEntity.badRequest().body(errores);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> manejarErrorGeneral(Exception ex) {
        log.error("Excepción interna del servidor: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Ocurrió un error interno: " + ex.getMessage());
    }
}