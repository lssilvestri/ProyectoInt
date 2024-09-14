package com.dh.clinica.controller;

import com.dh.clinica.dto.MessageResponseDTO;
import com.dh.clinica.dto.paciente.PacienteModificarRequestDTO;
import com.dh.clinica.dto.paciente.PacienteRequestDTO;
import com.dh.clinica.dto.paciente.PacienteResponseDTO;
import com.dh.clinica.exception.ResourceNotFoundException;
import com.dh.clinica.service.paciente.PacienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paciente")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    /**
     * Guarda un nuevo Paciente.
     *
     * @param nuevoPaciente Datos del paciente a guardar.
     * @return Paciente guardado con status 201.
     */
    @PostMapping("/guardar")
    public ResponseEntity<PacienteResponseDTO> guardar(@Valid @RequestBody PacienteRequestDTO nuevoPaciente) {
        PacienteResponseDTO pacienteGuardado = pacienteService.guardar(nuevoPaciente);
        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteGuardado);
    }

    /**
     * Busca un Paciente por su id.
     *
     * @param id Identificador del paciente.
     * @return Paciente encontrado o status 404 si no existe.
     */
    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        PacienteResponseDTO paciente = pacienteService.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(paciente);
    }

    /**
     * Busca un Paciente por su dni.
     *
     * @param dni Dni del paciente.
     * @return Paciente encontrado o status 404 si no existe.
     */

    @GetMapping("/buscar/dni/{dni}")
    public ResponseEntity<?> buscarPorDni(@PathVariable String dni) {
        try {
            PacienteResponseDTO pacienteEncontrado = pacienteService.buscarPorDni(dni);
            return ResponseEntity.status(HttpStatus.OK).body(pacienteEncontrado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Lista todos los Pacientes.
     *
     * @return Lista de pacientes.
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<PacienteResponseDTO>> buscarTodos() {
        List<PacienteResponseDTO> pacientes = pacienteService.buscarTodos();
        return ResponseEntity.status(HttpStatus.OK).body(pacientes);
    }

    /**
     * Modifica un Paciente existente.
     *
     * @param pacienteModificar Datos modificados del paciente.
     * @return Mensaje de éxito o error.
     */
    @PutMapping("/modificar")
    public ResponseEntity<MessageResponseDTO> modificar(@Valid @RequestBody PacienteModificarRequestDTO pacienteModificar) {
        pacienteService.modificar(pacienteModificar);
        return ResponseEntity.ok(new MessageResponseDTO("El paciente fue modificado"));
    }

    /**
     * Elimina un Paciente por su id.
     *
     * @param id Identificador del paciente.
     * @return Mensaje de éxito o error.
     */
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<MessageResponseDTO> eliminar(@PathVariable Integer id) {
        pacienteService.eliminar(id);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponseDTO("El paciente fue eliminado"));
    }
}
