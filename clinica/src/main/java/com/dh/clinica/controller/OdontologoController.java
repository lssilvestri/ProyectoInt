package com.dh.clinica.controller;

import com.dh.clinica.dto.MessageResponseDTO;
import com.dh.clinica.dto.odontologo.OdontologoModificarRequestDTO;
import com.dh.clinica.dto.odontologo.OdontologoRequestDTO;
import com.dh.clinica.dto.odontologo.OdontologoResponseDTO;
import com.dh.clinica.service.odontologo.OdontologoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
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
    public ResponseEntity<OdontologoResponseDTO> guardar(@Valid @RequestBody OdontologoRequestDTO nuevoOdontologo) {
        OdontologoResponseDTO odontologo = odontologoService.guardar(nuevoOdontologo);
        return ResponseEntity.status(HttpStatus.CREATED).body(odontologo);
    }

    /**
     * Busca un Odontologo por su id.
     *
     * @param id Identificador del odontólogo.
     * @return Odontólogo encontrado o status 404 si no existe.
     */
    @GetMapping("/buscar/{id}")
    public ResponseEntity<OdontologoResponseDTO> buscarPorId(@PathVariable Integer id) {
            OdontologoResponseDTO odontologo = odontologoService.buscarPorId(id);
            return ResponseEntity.status(HttpStatus.OK).body(odontologo);
    }

    /**
     * Lista todos los Odontologos.
     *
     * @return Lista de odontólogos.
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<OdontologoResponseDTO>> buscarTodos() {
        List<OdontologoResponseDTO> odontologos = odontologoService.buscarTodos();
        return ResponseEntity.status(HttpStatus.OK).body(odontologos);
    }

    /**
     * Modifica un Odontologo existente.
     *
     * @param odontologoModificado Datos modificados del odontólogo.
     * @return Mensaje de éxito o error.
     */
    @PutMapping("/modificar")
    public ResponseEntity<MessageResponseDTO> modificar(@Valid @RequestBody OdontologoModificarRequestDTO odontologoModificado) {
        odontologoService.modificar(odontologoModificado);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponseDTO("El odontólogo fue modificado"));
    }

    /**
     * Elimina un Odontologo por su id.
     *
     * @param id Identificador del odontólogo.
     * @return Mensaje de éxito o error.
     */
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<MessageResponseDTO> eliminar(@PathVariable Integer id) {
        odontologoService.eliminar(id);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponseDTO("El odontólogo fue eliminado"));
    }
}
