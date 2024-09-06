package com.dh.clinica;

import com.dh.clinica.dto.odontologo.OdontologoRequestDTO;
import com.dh.clinica.dto.odontologo.OdontologoResponseDTO;
import com.dh.clinica.dto.paciente.PacienteResponseDTO;
import com.dh.clinica.repository.IOdontologoRepository;
import com.dh.clinica.service.odontologo.OdontologoService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OdontologoServiceTest{
    @Autowired
    private IOdontologoRepository odontologoRepository;
    @Autowired
    private OdontologoService odontologoService;
    OdontologoResponseDTO odontologoDesdeDb;

    @BeforeEach
    void GuardarOdontologo(){
        OdontologoRequestDTO odontologo = new OdontologoRequestDTO("Miguel", "Arano", "FJH-876");
        odontologoDesdeDb = odontologoService.guardar(odontologo);
    }

    @Test
    void testBuscarPorIdOdontologoExistente(){
        Integer id = odontologoDesdeDb.id();
        OdontologoResponseDTO odontologoEncontrado = odontologoService.buscarPorId(id);
        assertEquals(id, odontologoEncontrado.id());
    }
    @Test
    void testBuscarPorIdPacienteNoExistente() {
        assertThrows(EntityNotFoundException.class, () -> odontologoService.buscarPorId(999));
    }
    @Test
    void testBuscarTodosLosPacientes() {
        List<OdontologoResponseDTO> odontologosEncontrados = odontologoService.buscarTodos();
        assertNotNull(odontologosEncontrados);
    }
    @Test
    void testEliminarPacienteExistente() {
        odontologoService.eliminar(odontologoDesdeDb.id());
        assertFalse(odontologoRepository.existsById(odontologoDesdeDb.id()));
    }
}