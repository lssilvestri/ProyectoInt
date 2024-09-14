package com.dh.clinica;

import com.dh.clinica.dto.paciente.DomicilioDTO;
import com.dh.clinica.dto.paciente.PacienteModificarRequestDTO;
import com.dh.clinica.dto.paciente.PacienteRequestDTO;
import com.dh.clinica.dto.paciente.PacienteResponseDTO;
import com.dh.clinica.exception.ResourceNotFoundException;
import com.dh.clinica.repository.IPacienteRepository;
import com.dh.clinica.service.paciente.PacienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
class PacienteServiceTest {
    PacienteResponseDTO pacienteDesdeDb;
    DomicilioDTO domicilio = new DomicilioDTO(null, "Falsa", "456", "Cipolleti", "Rio Negro");
    PacienteRequestDTO paciente = new PacienteRequestDTO("Romero", "Luciana", "5665700", LocalDate.now(), domicilio);
    @Autowired
    private IPacienteRepository pacienteRepository;
    @Autowired
    private PacienteService pacienteService;

    @BeforeEach
    void GuardarPaciente() {
        pacienteDesdeDb = pacienteService.guardar(paciente);
    }

    @Test
    void testBuscarPorIdPacienteExistente() {
        Integer id = pacienteDesdeDb.id();
        PacienteResponseDTO pacienteEncontrado = pacienteService.buscarPorId(id);
        assertEquals(id, pacienteEncontrado.id());
    }

    @Test
    void testBuscarPorIdPacienteNoExistente() {
        assertThrows(ResourceNotFoundException.class, () -> pacienteService.buscarPorId(999));
    }

    @Test
    void testBuscarTodosLosPacientes() {
        List<PacienteResponseDTO> pacientesEncontrados = pacienteService.buscarTodos();
        assertNotNull(pacientesEncontrados);
    }

    @Test
    void testEliminarPacienteExistente() {
        pacienteService.eliminar(pacienteDesdeDb.id());
        assertFalse(pacienteRepository.existsById(pacienteDesdeDb.id()));
    }

    @Test
    void testModificarPacienteExistente() {
        DomicilioDTO nuevoDomicilio = new DomicilioDTO(null, "Verdadera", "789", "Neuquén", "Neuquén");
        PacienteModificarRequestDTO pacienteModificado = new PacienteModificarRequestDTO(
                pacienteDesdeDb.id(), "Luciana Modificada", "Romero Modificado", "9876504", LocalDate.of(2022, 12, 31), nuevoDomicilio
        );

        pacienteService.modificar(pacienteModificado);

        PacienteResponseDTO pacienteActualizado = pacienteService.buscarPorId(pacienteDesdeDb.id());

        assertEquals("Luciana Modificada", pacienteActualizado.nombre());
        assertEquals("Romero Modificado", pacienteActualizado.apellido());
        assertEquals("9876504", pacienteActualizado.dni());
        assertEquals(LocalDate.of(2022, 12, 31), pacienteActualizado.fechaIngreso());
        assertEquals("Verdadera", pacienteActualizado.domicilio().calle());
        assertEquals(789, pacienteActualizado.domicilio().numero());
        assertEquals("Neuquén", pacienteActualizado.domicilio().localidad());
        assertEquals("Neuquén", pacienteActualizado.domicilio().provincia());
    }
}
