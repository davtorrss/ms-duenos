package com.vetnova.ms_duenos.service;

import com.vetnova.ms_duenos.model.Dueno;
import com.vetnova.ms_duenos.repository.DuenoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DuenoService {

    @Autowired
    private DuenoRepository duenoRepository;

    public Dueno registrarDueno(Dueno dueno) {
        if (duenoRepository.existsByRut(dueno.getRut())) {
            throw new RuntimeException("RUT duplicado");
        }
        return duenoRepository.save(dueno);
    }

    public List<Dueno> listarTodos() {
        return duenoRepository.findAll();
    }

    public Dueno actualizar(Long id, Dueno nuevosDatos) {
        return duenoRepository.findById(id).map(d -> {
            d.setNombre(nuevosDatos.getNombre());
            d.setTelefono(nuevosDatos.getTelefono());
            d.setDireccion(nuevosDatos.getDireccion());
            return duenoRepository.save(d);
        }).orElseThrow(() -> new RuntimeException("Dueño no encontrado"));
    }

    public void eliminar(Long id) {
        if (!duenoRepository.existsById(id)) {
            throw new RuntimeException("ID no existe");
        }
        duenoRepository.deleteById(id);
    }
}