package com.dh.clinica.controller;

import com.dh.clinica.dto.MessageResponseDTO;
import com.dh.clinica.dto.turno.TurnoModificarRequestDTO;
import com.dh.clinica.dto.turno.TurnoRequestDTO;
import com.dh.clinica.dto.turno.TurnoResponseDTO;
import com.dh.clinica.service.turno.TurnoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turno")
public class TurnoController {
    private final TurnoService turnoService;

    public TurnoController(TurnoService turnoService) {
        this.turnoService = turnoService;
    }

    /**
     * Guarda un nuevo Turno.
     *
     * @param nuevoTurno Datos del turno a guardar.
     * @return Turno guardado con status 201.
     */
    @PostMapping("/guardar")
    public ResponseEntity<TurnoResponseDTO> guardar(@RequestBody TurnoRequestDTO nuevoTurno) {
        try {
            TurnoResponseDTO turnoGuardado = turnoService.guardar(nuevoTurno);
            return ResponseEntity.status(HttpStatus.CREATED).body(turnoGuardado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Busca un turno por su id.
     *
     * @param id Id del turno a buscar.
     * @return Turno encontrado con status 200, o status 404 si no se encontró.
     */
    @GetMapping("/buscar/{id}")
    public ResponseEntity<TurnoResponseDTO> buscarPorId(@PathVariable Integer id) {
        try {
            TurnoResponseDTO turnoEncontrado = turnoService.buscarPorId(id);
            return ResponseEntity.status(HttpStatus.OK).body(turnoEncontrado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Busca todos los turnos.
     *
     * @return Lista de turnos con status 200, o status 404 si no se encontró.
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<TurnoResponseDTO>> buscarTodos() {
        try {
            List<TurnoResponseDTO> turnos = turnoService.buscarTodos();
            return ResponseEntity.status(HttpStatus.OK).body(turnos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Modifica un turno.
     *
     * @param turnoModificar Datos del turno a modificar.
     * @return Mensaje de éxito o error.
     */
    @PutMapping("/modificar")
    public ResponseEntity<MessageResponseDTO> modificar(@RequestBody TurnoModificarRequestDTO turnoModificar) {
        try {
            turnoService.modificar(turnoModificar);
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponseDTO("El turno fue modificado"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponseDTO(e.getMessage()));
        }
    }

    /**
     * Elimina un turno.
     *
     * @param id Id del turno a eliminar.
     * @return Mensaje de éxito o error.
     */
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<MessageResponseDTO> eliminar(@PathVariable Integer id) {
        try {
            turnoService.eliminar(id);
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponseDTO("El turno fue eliminado"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponseDTO(e.getMessage()));
        }
    }
}