package com.vetnova.ms_duenos.service;

import com.vetnova.ms_duenos.dto.DuenoRequestDTO;
import com.vetnova.ms_duenos.dto.DuenoResponseDTO;
import com.vetnova.ms_duenos.exception.DuenoNoEncontradoException;
import com.vetnova.ms_duenos.exception.RutDuplicadoException;
import com.vetnova.ms_duenos.model.Dueno;
import com.vetnova.ms_duenos.repository.DuenoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DuenoService {

    private static final Logger logger = LoggerFactory.getLogger(DuenoService.class);

    @Autowired
    private DuenoRepository repository;

    public DuenoResponseDTO registrar(DuenoRequestDTO dto) {

        logger.info("Registrando dueño con RUT: " + dto.getRut());

        if (repository.existsByRut(dto.getRut())) {
            logger.error("Ya existe un dueño con RUT: " + dto.getRut());
            throw new RutDuplicadoException(
                    "Ya existe un dueño registrado con ese RUT");
        }

        Dueno dueno = new Dueno();

        dueno.setRut(dto.getRut());
        dueno.setNombre(dto.getNombre());
        dueno.setTelefono(dto.getTelefono());
        dueno.setDireccion(dto.getDireccion());

        return convertirAResponse(repository.save(dueno));
    }

    public List<DuenoResponseDTO> listarTodos() {

        logger.info("Listando dueños");

        return repository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    public DuenoResponseDTO buscarPorId(Long id) {

        logger.info("Buscando dueño con ID: " + id);

        Dueno dueno = repository.findById(id)
                .orElseThrow(() -> {

                    logger.error("Dueño no encontrado con ID: " + id);

                    return new DuenoNoEncontradoException(
                            "Dueño no encontrado");
                });

        return convertirAResponse(dueno);
    }

    public DuenoResponseDTO actualizar(Long id, DuenoRequestDTO dto) {

        logger.info("Actualizando dueño con ID: " + id);

        Dueno dueno = repository.findById(id)
                .orElseThrow(() -> {

                    logger.error("Dueño no encontrado con ID: " + id);

                    return new DuenoNoEncontradoException(
                            "Dueño no encontrado");
                });

        dueno.setNombre(dto.getNombre());
        dueno.setTelefono(dto.getTelefono());
        dueno.setDireccion(dto.getDireccion());

        return convertirAResponse(repository.save(dueno));
    }

    public void eliminar(Long id) {

        logger.info("Eliminando dueño con ID: " + id);

        Dueno dueno = repository.findById(id)
                .orElseThrow(() -> {

                    logger.error("Dueño no encontrado con ID: " + id);

                    return new DuenoNoEncontradoException(
                            "Dueño no encontrado");
                });

        repository.delete(dueno);
    }

    private DuenoResponseDTO convertirAResponse(Dueno dueno) {

        DuenoResponseDTO dto = new DuenoResponseDTO();

        dto.setId(dueno.getId());
        dto.setRut(dueno.getRut());
        dto.setNombre(dueno.getNombre());
        dto.setTelefono(dueno.getTelefono());
        dto.setDireccion(dueno.getDireccion());

        return dto;
    }
}