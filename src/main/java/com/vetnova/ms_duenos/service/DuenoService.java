package com.vetnova.ms_duenos.service;

import com.vetnova.ms_duenos.dto.DuenoRequestDTO;
import com.vetnova.ms_duenos.dto.DuenoResponseDTO;
import com.vetnova.ms_duenos.model.Dueno;
import com.vetnova.ms_duenos.repository.DuenoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
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
        return repository.findAll().stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    public Optional<DuenoResponseDTO> buscarPorId(Long id) {
        return repository.findById(id).map(this::convertirAResponse);
    }

    private DuenoResponseDTO convertirAResponse(Dueno d) {
        DuenoResponseDTO dto = new DuenoResponseDTO();
        dto.setId(d.getId());
        dto.setRut(d.getRut());
        dto.setNombre(d.getNombre());
        dto.setTelefono(d.getTelefono());
        dto.setDireccion(d.getDireccion());
        return dto;
    }
}