package com.vetnova.ms_duenos.service;

import com.vetnova.ms_duenos.model.Dueno;
import com.vetnova.ms_duenos.repository.DuenoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DuenoService {

    @Autowired
    private DuenoRepository duenoRepository;

    public List<Dueno> findAll() {
        return duenoRepository.findAll();
    }

    public Optional<Dueno> buscarPorId(Long id) {
        return duenoRepository.findById(id);
    }
    
    public Dueno guardarDueno(Dueno dueno) {
        return duenoRepository.save(dueno);
    }
}