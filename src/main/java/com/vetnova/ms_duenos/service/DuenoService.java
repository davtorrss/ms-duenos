package com.vetnova.ms_duenos.service;

import com.vetnova.ms_duenos.dto.DuenoRequestDTO;
import com.vetnova.ms_duenos.dto.DuenoResponseDTO;
import com.vetnova.ms_duenos.model.Dueno;
import com.vetnova.ms_duenos.repository.DuenoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DuenoService {

    @Autowired
    private DuenoRepository repository;

    public DuenoResponseDTO registrar(DuenoRequestDTO dto) {

        Dueno dueno = new Dueno();

        dueno.setRut(dto.getRut());
        dueno.setNombre(dto.getNombre());
        dueno.setTelefono(dto.getTelefono());
        dueno.setDireccion(dto.getDireccion());

        return convertirAResponse(repository.save(dueno));
    }

    public List<DuenoResponseDTO> listarTodos() {

        return repository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    public DuenoResponseDTO buscarPorId(Long id) {

        Dueno dueno = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Dueño no encontrado"));

        return convertirAResponse(dueno);
    }

    public DuenoResponseDTO actualizar(Long id, DuenoRequestDTO dto) {

        Dueno dueno = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Dueño no encontrado"));

        dueno.setNombre(dto.getNombre());
        dueno.setTelefono(dto.getTelefono());
        dueno.setDireccion(dto.getDireccion());

        return convertirAResponse(repository.save(dueno));
    }

    public void eliminar(Long id) {

        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Dueño no encontrado");
        }

        repository.deleteById(id);
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