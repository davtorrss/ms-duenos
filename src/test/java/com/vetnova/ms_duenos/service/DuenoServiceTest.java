package com.vetnova.ms_duenos.service;

import com.vetnova.ms_duenos.dto.DuenoRequestDTO;
import com.vetnova.ms_duenos.dto.DuenoResponseDTO;
import com.vetnova.ms_duenos.exception.DuenoNoEncontradoException;
import com.vetnova.ms_duenos.exception.RutDuplicadoException;
import com.vetnova.ms_duenos.model.Dueno;
import com.vetnova.ms_duenos.repository.DuenoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DuenoServiceTest {

    @Mock
    private DuenoRepository duenoRepository;

    @InjectMocks
    private DuenoService duenoService;

    private DuenoRequestDTO requestDTO;
    private Dueno duenoSimulado;

    @BeforeEach
    void setUp() {
        requestDTO = new DuenoRequestDTO(
                "12345678-K", 
                "Carlos Fuentes", 
                "+56987654321", 
                "Aníbal Pinto 450, Concepción"
        );

        duenoSimulado = new Dueno(
                1L, 
                "12345678-K", 
                "Carlos Fuentes", 
                "+56987654321", 
                "Aníbal Pinto 450, Concepción"
        );
    }

    @Test
    @DisplayName("Debe registrar un dueño correctamente cuando el RUT no existe")
    void registrar_CaminoFeliz_RetornaDuenoResponseDTO() {
        when(duenoRepository.existsByRut(requestDTO.getRut())).thenReturn(false);
        when(duenoRepository.save(any(Dueno.class))).thenReturn(duenoSimulado);
        
        DuenoResponseDTO resultado = duenoService.registrar(requestDTO);
        
        assertNotNull(resultado);
        assertEquals("12345678-K", resultado.getRut());
        assertEquals("Carlos Fuentes", resultado.getNombre());
        verify(duenoRepository, times(1)).existsByRut(requestDTO.getRut());
        verify(duenoRepository, times(1)).save(any(Dueno.class));
    }

    @Test
    @DisplayName("Debe lanzar RutDuplicadoException cuando el RUT ya existe al registrar")
    void registrar_RutYaExiste_LanzaRutDuplicadoException() {
        when(duenoRepository.existsByRut(requestDTO.getRut())).thenReturn(true);
        
        assertThrows(RutDuplicadoException.class, () -> {
            duenoService.registrar(requestDTO);
        });
        
        verify(duenoRepository, times(1)).existsByRut(requestDTO.getRut());
        verify(duenoRepository, never()).save(any(Dueno.class));
    }

    @Test
    @DisplayName("Debe retornar la lista completa de dueños")
    void listarTodos_RetornaLista() {
        when(duenoRepository.findAll()).thenReturn(List.of(duenoSimulado));
        
        List<DuenoResponseDTO> resultado = duenoService.listarTodos();
        
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("12345678-K", resultado.get(0).getRut());
        verify(duenoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe encontrar un dueño por su ID")
    void buscarPorId_Existe_RetornaDuenoResponseDTO() {
        when(duenoRepository.findById(1L)).thenReturn(Optional.of(duenoSimulado));
        
        DuenoResponseDTO resultado = duenoService.buscarPorId(1L);
        
        assertNotNull(resultado);
        assertEquals("Carlos Fuentes", resultado.getNombre());
        verify(duenoRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debe lanzar DuenoNoEncontradoException cuando el ID no existe al buscar")
    void buscarPorId_NoExiste_LanzaException() {
        when(duenoRepository.findById(99L)).thenReturn(Optional.empty());
        
        assertThrows(DuenoNoEncontradoException.class, () -> {
            duenoService.buscarPorId(99L);
        });
        
        verify(duenoRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Debe actualizar un dueño correctamente")
    void actualizar_CaminoFeliz_RetornaDuenoResponseDTO() {
        when(duenoRepository.findById(1L)).thenReturn(Optional.of(duenoSimulado));
        when(duenoRepository.save(any(Dueno.class))).thenReturn(duenoSimulado);

        DuenoRequestDTO updateRequest = new DuenoRequestDTO(
                "12345678-K", "Carlos Actualizado", "+56911111111", "Nueva Dirección"
        );

        DuenoResponseDTO resultado = duenoService.actualizar(1L, updateRequest);

        assertNotNull(resultado);
        assertEquals("Carlos Actualizado", resultado.getNombre());
        verify(duenoRepository, times(1)).findById(1L);
        verify(duenoRepository, times(1)).save(any(Dueno.class));
    }

    @Test
    @DisplayName("Debe eliminar un dueño si el ID existe")
    void eliminar_Existe_EliminaCorrectamente() {
        when(duenoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(duenoRepository).deleteById(1L);

        duenoService.eliminar(1L);

        verify(duenoRepository, times(1)).existsById(1L);
        verify(duenoRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Debe lanzar RutDuplicadoException al actualizar con un RUT que ya pertenece a otro dueño")
    void actualizar_RutDuplicado_LanzaException() {
        when(duenoRepository.findById(1L)).thenReturn(Optional.of(duenoSimulado));
        
        DuenoRequestDTO updateRequest = new DuenoRequestDTO(
                "99888777-6", "Carlos Modificado", "+56911111111", "Nueva Dirección"
        );
        
        when(duenoRepository.existsByRut(updateRequest.getRut())).thenReturn(true);

        assertThrows(RutDuplicadoException.class, () -> {
            duenoService.actualizar(1L, updateRequest);
        });

        verify(duenoRepository, never()).save(any(Dueno.class));
    }

    @Test
    @DisplayName("Debe lanzar DuenoNoEncontradoException al intentar eliminar un ID que no existe")
    void eliminar_NoExiste_LanzaException() {
        when(duenoRepository.existsById(99L)).thenReturn(false);

        assertThrows(DuenoNoEncontradoException.class, () -> {
            duenoService.eliminar(99L);
        });

        verify(duenoRepository, times(1)).existsById(99L);
        verify(duenoRepository, never()).deleteById(99L);
    }

    @Test
    @DisplayName("Debe lanzar DuenoNoEncontradoException al intentar actualizar un ID que no existe")
    void actualizar_NoExiste_LanzaException() {
        when(duenoRepository.findById(99L)).thenReturn(Optional.empty());
        
        DuenoRequestDTO updateRequest = new DuenoRequestDTO(
                "12345678-K", "Carlos Actualizado", "+56911111111", "Nueva Dirección"
        );

        assertThrows(DuenoNoEncontradoException.class, () -> {
            duenoService.actualizar(99L, updateRequest);
        });

        verify(duenoRepository, times(1)).findById(99L);
        verify(duenoRepository, never()).save(any(Dueno.class));
    }

    @Test
    @DisplayName("Debe actualizar correctamente cuando el dueño cambia su RUT por uno nuevo y disponible")
    void actualizar_CambioRutValido_RetornaDuenoResponseDTO() {
        when(duenoRepository.findById(1L)).thenReturn(Optional.of(duenoSimulado));
        
        DuenoRequestDTO updateRequest = new DuenoRequestDTO(
                "99888777-6", "Carlos Actualizado", "+56911111111", "Nueva Dirección"
        );
        
        when(duenoRepository.existsByRut(updateRequest.getRut())).thenReturn(false);
        when(duenoRepository.save(any(Dueno.class))).thenReturn(duenoSimulado);

        DuenoResponseDTO resultado = duenoService.actualizar(1L, updateRequest);

        assertNotNull(resultado);
        verify(duenoRepository, times(1)).findById(1L);
        verify(duenoRepository, times(1)).existsByRut(updateRequest.getRut());
        verify(duenoRepository, times(1)).save(any(Dueno.class));
    }
}