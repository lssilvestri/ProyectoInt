package com.dh.clinica.controller;
import com.dh.clinica.dto.request.OdontologoModifyDto;
import com.dh.clinica.dto.request.OdontologoRequestDto;
import com.dh.clinica.dto.response.OdontologoResponseDto;
import com.dh.clinica.service.impl.OdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologo")
public class OdontologoController {

    @Autowired
    private OdontologoService odontologoService;

    public OdontologoController(OdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    @PostMapping("/guardar")
    public ResponseEntity<OdontologoResponseDto> guardarOdontologo(@RequestBody OdontologoRequestDto odontologoRequestDto) {
        OdontologoResponseDto odontologoGuardado = odontologoService.guardarOdontologo(odontologoRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(odontologoGuardado);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<OdontologoResponseDto> buscarPorId(@PathVariable Integer id) {
        Optional<OdontologoResponseDto> odontologoEncontrado = odontologoService.buscarPorId(id);
        return odontologoEncontrado
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/buscartodos")
    public ResponseEntity<List<OdontologoResponseDto>> buscarTodos() {
        List<OdontologoResponseDto> listaOdontologos = odontologoService.buscarTodos();
        return ResponseEntity.ok(listaOdontologos);
    }

    @PutMapping("/modificar")
    public ResponseEntity<String> modificarOdontologo(@RequestBody OdontologoModifyDto odontologoModifyDto) {
        Optional<OdontologoResponseDto> odontologoEncontrado = odontologoService.buscarPorId(odontologoModifyDto.getId());
        if (odontologoEncontrado.isPresent()) {
            odontologoService.modificarOdontologo(odontologoModifyDto);
            String jsonResponse = "{\"mensaje\": \"El odontólogo fue modificado\"}";
            return ResponseEntity.ok(jsonResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"mensaje\": \"El odontólogo no fue encontrado\"}");
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarOdontologo(@PathVariable Integer id) {
        Optional<OdontologoResponseDto> odontologoEncontrado = odontologoService.buscarPorId(id);
        if (odontologoEncontrado.isPresent()) {
            odontologoService.eliminarOdontologo(id);
            String jsonResponse = "{\"mensaje\": \"El odontólogo fue eliminado\"}";
            return ResponseEntity.ok(jsonResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"mensaje\": \"El odontólogo no fue encontrado\"}");
        }
    }
}
