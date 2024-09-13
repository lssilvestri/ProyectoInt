package com.dh.clinica.controller;

import com.dh.clinica.dto.MessageResponseDTO;
import com.dh.clinica.dto.turno.TurnoModificarRequestDTO;
import com.dh.clinica.dto.turno.TurnoRequestDTO;
import com.dh.clinica.dto.turno.TurnoResponseDTO;
import com.dh.clinica.service.turno.TurnoService;
import jakarta.validation.Valid;
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
    public ResponseEntity<TurnoResponseDTO> guardar(@Valid @RequestBody TurnoRequestDTO nuevoTurno) {
        TurnoResponseDTO turnoGuardado = turnoService.guardar(nuevoTurno);
        return ResponseEntity.status(HttpStatus.CREATED).body(turnoGuardado);
    }

    /**
     * Busca un turno por su id.
     *
     * @param id Id del turno a buscar.
     * @return Turno encontrado con status 200, o status 404 si no se encontró.
     */
    @GetMapping("/buscar/{id}")
    public ResponseEntity<TurnoResponseDTO> buscarPorId(@PathVariable Integer id) {
        TurnoResponseDTO turnoEncontrado = turnoService.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(turnoEncontrado);
    }

    /**
     * Busca todos los turnos.
     *
     * @return Lista de turnos con status 200, o status 404 si no se encontró.
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<TurnoResponseDTO>> buscarTodos() {
        List<TurnoResponseDTO> turnos = turnoService.buscarTodos();
        return ResponseEntity.status(HttpStatus.OK).body(turnos);
    }

    /**
     * Modifica un turno.
     *
     * @param turnoModificar Datos del turno a modificar.
     * @return Mensaje de éxito o error.
     */
    @PutMapping("/modificar")
    public ResponseEntity<MessageResponseDTO> modificar(@Valid @RequestBody TurnoModificarRequestDTO turnoModificar) {
        turnoService.modificar(turnoModificar);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponseDTO("El turno fue modificado"));
    }

    /**
     * Elimina un turno.
     *
     * @param id Id del turno a eliminar.
     * @return Mensaje de éxito o error.
     */
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<MessageResponseDTO> eliminar(@PathVariable Integer id) {
        turnoService.eliminar(id);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponseDTO("El turno fue eliminado"));
    }
}