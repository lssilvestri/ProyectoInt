package com.dh.clinica.controller;
import com.dh.clinica.dto.request.TurnoModifyDto;
import com.dh.clinica.dto.request.TurnoRequestDto;
import com.dh.clinica.dto.response.TurnoResponseDto;
import com.dh.clinica.entity.Turno;
import com.dh.clinica.service.impl.TurnoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turnos")
public class TurnoController {
    private TurnoService turnoService;

    public TurnoController(TurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> guardarTurno(@RequestBody TurnoRequestDto turnoRequestDto){
        TurnoResponseDto turnoAGuardar = turnoService.guardarTurno(turnoRequestDto);
        if(turnoAGuardar != null){
            return ResponseEntity.ok(turnoAGuardar);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El paciente o el odontologo no fueron encontrados");
        }

    }

    @GetMapping("/buscartodos")
    public ResponseEntity<List<TurnoResponseDto>> buscarTodos(){
        return ResponseEntity.ok(turnoService.buscarTodos());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<TurnoResponseDto> buscarPorId(@PathVariable Integer id){
        Optional<TurnoResponseDto> turno = turnoService.buscarPorId(id);
        return turno.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    @PutMapping("/modificar")
    public ResponseEntity<?> modificarTurno(@RequestBody TurnoModifyDto turnoModifyDto){
        Optional<TurnoResponseDto> turnoEncontrado = turnoService.buscarPorId(turnoModifyDto.getId());
        if(turnoEncontrado.isPresent()){
            turnoService.modificarTurno(turnoModifyDto);
            String jsonResponse = "{\"mensaje\": \"El turno fue modificado\"}";
            return ResponseEntity.ok(jsonResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarTurno(@PathVariable Integer id){
        Optional<TurnoResponseDto> turnoEncontrado = turnoService.buscarPorId(id);
        if(turnoEncontrado.isPresent()){
            turnoService.eliminarTurno(id);
            String jsonResponse = "{\"mensaje\": \"El turno fue eliminado\"}";
            return ResponseEntity.ok(jsonResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}