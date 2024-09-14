package com.dh.clinica;

import com.dh.clinica.dto.odontologo.OdontologoRequestDTO;
import com.dh.clinica.dto.odontologo.OdontologoResponseDTO;
import com.dh.clinica.dto.paciente.DomicilioDTO;
import com.dh.clinica.dto.paciente.PacienteRequestDTO;
import com.dh.clinica.dto.paciente.PacienteResponseDTO;
import com.dh.clinica.dto.turno.TurnoModificarRequestDTO;
import com.dh.clinica.dto.turno.TurnoRequestDTO;
import com.dh.clinica.dto.turno.TurnoResponseDTO;
import com.dh.clinica.repository.ITurnoRepository;
import com.dh.clinica.service.odontologo.OdontologoService;
import com.dh.clinica.service.paciente.PacienteService;
import com.dh.clinica.service.turno.TurnoService;
import jakarta.persistence.EntityNotFoundException;
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
class TurnoServiceTest {
    @Autowired
    private TurnoService turnoService;
    @Autowired
    private OdontologoService odontologoService;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private ITurnoRepository turnoRepository;
    TurnoResponseDTO turnoDesdeDb;

    void GuardarOdontologo(){
        OdontologoRequestDTO odontologo = new OdontologoRequestDTO("Miguel", "Arano", "FJH-876");
        OdontologoResponseDTO odontologoDesdeDb = odontologoService.guardar(odontologo);
    }

    void GuardarPaciente() {
        DomicilioDTO domicilio = new DomicilioDTO(null,"Falsa", 456, "Cipolleti", "Rio Negro");
        PacienteRequestDTO paciente =  new PacienteRequestDTO("Romero", "Luciana", "5665700", LocalDate.now(), domicilio);
        PacienteResponseDTO pacienteDesdeDb = pacienteService.guardar(paciente);
    }

    @BeforeEach
    void GuardarTurno(){
        GuardarOdontologo();
        GuardarPaciente();
        TurnoRequestDTO turno = new TurnoRequestDTO(1, 1, LocalDate.now());
        turnoDesdeDb = turnoService.guardar(turno);
    }


    @Test
    void testBuscarPorIdTurnoExistente() {
        Integer id = turnoDesdeDb.id();
        TurnoResponseDTO turnoEncontrado = turnoService.buscarPorId(id);
        assertEquals(id, turnoEncontrado.id());
    }

    @Test
    void testBuscarPorIdTurnoNoExistente() {
        assertThrows(EntityNotFoundException.class, () -> turnoService.buscarPorId(999));
    }

    @Test
    void testBuscarTodosLosTurnos() {
        List<TurnoResponseDTO> turnosEncontrados = turnoService.buscarTodos();
        assertNotNull(turnosEncontrados);
    }

    @Test
    void testEliminarTurnoExistente() {
        turnoService.eliminar(turnoDesdeDb.id());
        assertFalse(turnoRepository.existsById(turnoDesdeDb.id()));
    }

    @Test
    void testModificarTurno() {
        TurnoModificarRequestDTO turnoModificado = new TurnoModificarRequestDTO(
                turnoDesdeDb.id(),
                turnoDesdeDb.pacienteResponseDTO().id(),
                turnoDesdeDb.odontologoResponseDTO().id(),
                LocalDate.now().plusDays(1)
        );

        turnoService.modificar(turnoModificado);
        TurnoResponseDTO turnoEncontrado = turnoService.buscarPorId(turnoDesdeDb.id());
        assertEquals(turnoModificado.fecha(), turnoEncontrado.fecha());
        assertEquals(turnoModificado.paciente_id(), turnoEncontrado.pacienteResponseDTO().id());
        assertEquals(turnoModificado.odontologo_id(), turnoEncontrado.odontologoResponseDTO().id());
    }
}