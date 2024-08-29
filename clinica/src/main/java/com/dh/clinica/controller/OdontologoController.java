package com.dh.clinica.controller;
import com.dh.clinica.entity.Odontologo;
import com.dh.clinica.service.impl.OdontologoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologo")
public class OdontologoController {
    private OdontologoService odontologoService;

    public OdontologoController(OdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    @PostMapping("/guardar")
    public ResponseEntity<Odontologo> guardarOdontologo(@RequestBody Odontologo odontologo) {
        Odontologo odontologoGuardado = odontologoService.guardarOdontologo(odontologo);
        return ResponseEntity.status(HttpStatus.CREATED).body(odontologoGuardado);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Odontologo> buscarPorId(@PathVariable Integer id) {
        Optional<Odontologo> odontologoEncontrado = odontologoService.buscarPorId(id);
        return odontologoEncontrado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatusCode.valueOf(404)).build());
    }

    @GetMapping("/buscartodos")
    public ResponseEntity<List<Odontologo>> buscarTodos() {
        List<Odontologo> odontologos = odontologoService.buscarTodos();
        return ResponseEntity.ok(odontologos);
    }

    @PutMapping("/modificar")
    public ResponseEntity<String> modificarOdontologo(@RequestBody Odontologo odontologo) {
        Optional<Odontologo> odontologoEncontrado = odontologoService.buscarPorId(odontologo.getId());
        if (odontologoEncontrado.isPresent()){
            odontologoService.modificarOdontologo(odontologoEncontrado.get());
            String jsonResponse = "{\"mensaje\": \"El odontologo fue modificado\"}";
            return ResponseEntity.ok(jsonResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarOdontologo(@PathVariable Integer id) {
        Optional<Odontologo> odontologoEncontrado = odontologoService.buscarPorId(id);
        if(odontologoEncontrado.isPresent()){
            odontologoService.eliminarOdontologo(id);
            String jsonResponse = "{\"mensaje\": \"El odontologo fue eliminado\"}";
            return ResponseEntity.ok(jsonResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
