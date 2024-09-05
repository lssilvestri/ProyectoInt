package com.dh.clinica.controller;

import com.dh.clinica.dto.MessageResponseDTO;
import com.dh.clinica.dto.odontologo.OdontologoModificarRequestDTO;
import com.dh.clinica.dto.odontologo.OdontologoRequestDTO;
import com.dh.clinica.dto.odontologo.OdontologoResponseDTO;
import com.dh.clinica.service.odontologo.OdontologoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/odontologo")
public class OdontologoController {

    private final OdontologoService odontologoService;

    public OdontologoController(OdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    /**
     * Guarda un nuevo Odontologo.
     *
     * @param nuevoOdontologo Datos del odontólogo a guardar.
     * @return Odontólogo guardado con status 201.
     */
    @PostMapping("/guardar")
    public ResponseEntity<OdontologoResponseDTO> guardar(@RequestBody OdontologoRequestDTO nuevoOdontologo) {
        try {
            OdontologoResponseDTO odontologo = odontologoService.guardar(nuevoOdontologo);
            return ResponseEntity.status(HttpStatus.CREATED).body(odontologo);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Busca un Odontologo por su id.
     *
     * @param id Identificador del odontólogo.
     * @return Odontólogo encontrado o status 404 si no existe.
     */
    @GetMapping("/buscar/{id}")
    public ResponseEntity<OdontologoResponseDTO> buscarPorId(@PathVariable Integer id) {
        try {
            OdontologoResponseDTO odontologo = odontologoService.buscarPorId(id);
            return ResponseEntity.status(HttpStatus.OK).body(odontologo);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Lista todos los Odontologos.
     *
     * @return Lista de odontólogos.
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<OdontologoResponseDTO>> buscarTodos() {
        try {
            List<OdontologoResponseDTO> odontologos = odontologoService.buscarTodos();
            return ResponseEntity.status(HttpStatus.OK).body(odontologos);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Modifica un Odontologo existente.
     *
     * @param odontologoModificado Datos modificados del odontólogo.
     * @return Mensaje de éxito o error.
     */
    @PutMapping("/modificar")
    public ResponseEntity<MessageResponseDTO> modificar(@RequestBody OdontologoModificarRequestDTO odontologoModificado) {
        try {
            odontologoService.modificar(odontologoModificado);
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponseDTO("El odontólogo fue modificado"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponseDTO(e.getMessage()));
        }
    }

    /**
     * Elimina un Odontologo por su id.
     *
     * @param id Identificador del odontólogo.
     * @return Mensaje de éxito o error.
     */
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<MessageResponseDTO> eliminar(@PathVariable Integer id) {
        try {
            odontologoService.eliminar(id);
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponseDTO("El odontólogo fue eliminado"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponseDTO(e.getMessage()));
        }
    }
}
