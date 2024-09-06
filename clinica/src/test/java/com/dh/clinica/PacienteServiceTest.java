package com.dh.clinica;

import com.dh.clinica.dto.paciente.DomicilioDTO;
import com.dh.clinica.dto.paciente.PacienteModificarRequestDTO;
import com.dh.clinica.dto.paciente.PacienteRequestDTO;
import com.dh.clinica.dto.paciente.PacienteResponseDTO;
import com.dh.clinica.entity.Domicilio;
import com.dh.clinica.entity.Paciente;
import com.dh.clinica.repository.IPacienteRepository;
import com.dh.clinica.service.paciente.PacienteService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PacienteServiceTest {
    @Autowired
    private IPacienteRepository pacienteRepository;
    @Autowired
    private PacienteService pacienteService;

    PacienteResponseDTO pacienteDesdeDb;
    DomicilioDTO domicilio = new DomicilioDTO(null,"Falsa", 456, "Cipolleti", "Rio Negro");
    PacienteRequestDTO paciente =  new PacienteRequestDTO("Romero", "Luciana", "566570", LocalDate.now(), domicilio);

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
        assertThrows(EntityNotFoundException.class, () -> pacienteService.buscarPorId(999));
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
}
