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
        // Datos de prueba iniciales
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
        // Arrange
        when(duenoRepository.existsByRut(requestDTO.getRut())).thenReturn(false);
        when(duenoRepository.save(any(Dueno.class))).thenReturn(duenoSimulado);

        // Act
        DuenoResponseDTO resultado = duenoService.registrar(requestDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals("12345678-K", resultado.getRut());
        assertEquals("Carlos Fuentes", resultado.getNombre());
        verify(duenoRepository, times(1)).existsByRut(requestDTO.getRut());
        verify(duenoRepository, times(1)).save(any(Dueno.class));
    }

    @Test
    @DisplayName("Debe lanzar RutDuplicadoException cuando el RUT ya existe al registrar")
    void registrar_RutYaExiste_LanzaRutDuplicadoException() {
        // Arrange
        when(duenoRepository.existsByRut(requestDTO.getRut())).thenReturn(true);

        // Act & Assert
        assertThrows(RutDuplicadoException.class, () -> {
            duenoService.registrar(requestDTO);
        });
        verify(duenoRepository, times(1)).existsByRut(requestDTO.getRut());
        verify(duenoRepository, never()).save(any(Dueno.class));
    }

    @Test
    @DisplayName("Debe retornar la lista completa de dueños")
    void listarTodos_RetornaLista() {
        // Arrange
        when(duenoRepository.findAll()).thenReturn(List.of(duenoSimulado));

        // Act
        List<DuenoResponseDTO> resultado = duenoService.listarTodos();

        // Assert
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("12345678-K", resultado.get(0).getRut());
        verify(duenoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe encontrar un dueño por su ID")
    void buscarPorId_Existe_RetornaDuenoResponseDTO() {
        // Arrange
        when(duenoRepository.findById(1L)).thenReturn(Optional.of(duenoSimulado));

        // Act
        DuenoResponseDTO resultado = duenoService.buscarPorId(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals("Carlos Fuentes", resultado.getNombre());
        verify(duenoRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debe lanzar DuenoNoEncontradoException cuando el ID no existe al buscar")
    void buscarPorId_NoExiste_LanzaException() {
        // Arrange
        when(duenoRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DuenoNoEncontradoException.class, () -> {
            duenoService.buscarPorId(99L);
        });
        verify(duenoRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Debe actualizar un dueño correctamente")
    void actualizar_CaminoFeliz_RetornaDuenoResponseDTO() {
        // Arrange
        when(duenoRepository.findById(1L)).thenReturn(Optional.of(duenoSimulado));
        when(duenoRepository.save(any(Dueno.class))).thenReturn(duenoSimulado);

        DuenoRequestDTO updateRequest = new DuenoRequestDTO(
                "12345678-K", "Carlos Actualizado", "+56911111111", "Nueva Dirección"
        );

        // Act
        DuenoResponseDTO resultado = duenoService.actualizar(1L, updateRequest);

        // Assert
        assertNotNull(resultado);
        assertEquals("Carlos Actualizado", resultado.getNombre());
        verify(duenoRepository, times(1)).findById(1L);
        verify(duenoRepository, times(1)).save(any(Dueno.class));
    }

    @Test
    @DisplayName("Debe eliminar un dueño si el ID existe")
    void eliminar_Existe_EliminaCorrectamente() {
        // Arrange
        when(duenoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(duenoRepository).deleteById(1L);

        // Act
        duenoService.eliminar(1L);

        // Assert
        verify(duenoRepository, times(1)).existsById(1L);
        verify(duenoRepository, times(1)).deleteById(1L);
    }
}