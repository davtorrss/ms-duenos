package com.vetnova.ms_duenos.service;

import com.vetnova.ms_duenos.dto.DuenoRequestDTO;
import com.vetnova.ms_duenos.dto.DuenoResponseDTO;
import com.vetnova.ms_duenos.exception.DuenoNoEncontradoException;
import com.vetnova.ms_duenos.exception.RutDuplicadoException;
import com.vetnova.ms_duenos.model.Dueno;
import com.vetnova.ms_duenos.repository.DuenoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j // Anotación clave para los logs estructurados
@Service
public class DuenoService {

    @Autowired
    private DuenoRepository duenoRepository;

    public DuenoResponseDTO registrar(DuenoRequestDTO dto) {
        log.info("Iniciando registro de nuevo dueño con RUT: {}", dto.getRut());
        
        if (duenoRepository.existsByRut(dto.getRut())) {
            log.error("Fallo al registrar: El RUT {} ya existe en la base de datos", dto.getRut());
            throw new RutDuplicadoException("El RUT ingresado ya se encuentra registrado.");
        }

        Dueno dueno = new Dueno();
        dueno.setRut(dto.getRut());
        dueno.setNombre(dto.getNombre());
        dueno.setTelefono(dto.getTelefono());
        dueno.setDireccion(dto.getDireccion());

        Dueno duenoGuardado = duenoRepository.save(dueno);
        log.info("Dueño registrado exitosamente con ID: {}", duenoGuardado.getId());
        
        return mapearADTO(duenoGuardado);
    }

    public List<DuenoResponseDTO> listarTodos() {
        log.info("Consultando listado completo de dueños");
        return duenoRepository.findAll().stream()
                .map(this::mapearADTO)
                .collect(Collectors.toList());
    }

    public DuenoResponseDTO buscarPorId(Long id) {
        log.info("Buscando dueño con ID: {}", id);
        Dueno dueno = duenoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Dueño con ID {} no fue encontrado", id);
                    return new DuenoNoEncontradoException("No se encontró el dueño con ID: " + id);
                });
        return mapearADTO(dueno);
    }

    public DuenoResponseDTO actualizar(Long id, DuenoRequestDTO dto) {
        log.info("Iniciando actualización de dueño con ID: {}", id);
        Dueno dueno = duenoRepository.findById(id)
                .orElseThrow(() -> new DuenoNoEncontradoException("No se encontró el dueño a actualizar"));

        // Validar si quiere cambiar el rut a uno que ya existe en otro registro
        if (!dueno.getRut().equals(dto.getRut()) && duenoRepository.existsByRut(dto.getRut())) {
             log.error("Intento de actualización fallido: RUT {} duplicado", dto.getRut());
             throw new RutDuplicadoException("El nuevo RUT ingresado ya pertenece a otro cliente.");
        }

        dueno.setRut(dto.getRut());
        dueno.setNombre(dto.getNombre());
        dueno.setTelefono(dto.getTelefono());
        dueno.setDireccion(dto.getDireccion());

        Dueno actualizado = duenoRepository.save(dueno);
        log.info("Dueño ID: {} actualizado correctamente", id);
        return mapearADTO(actualizado);
    }

    public void eliminar(Long id) {
        log.info("Iniciando eliminación de dueño con ID: {}", id);
        if (!duenoRepository.existsById(id)) {
            log.error("Error al eliminar: Dueño ID {} no existe", id);
            throw new DuenoNoEncontradoException("No se encontró el dueño a eliminar");
        }
        duenoRepository.deleteById(id);
        log.info("Dueño ID: {} eliminado correctamente", id);
    }

    // Método privado auxiliar para no repetir código
    private DuenoResponseDTO mapearADTO(Dueno dueno) {
        return new DuenoResponseDTO(
                dueno.getId(),
                dueno.getRut(),
                dueno.getNombre(),
                dueno.getTelefono(),
                dueno.getDireccion()
        );
    }
}