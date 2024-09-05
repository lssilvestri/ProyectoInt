package com.dh.clinica.controller;

import com.dh.clinica.dto.paciente.PacienteResponseDTO;
import com.dh.clinica.service.paciente.PacienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VistaController {
    private final PacienteService pacienteService;

    public VistaController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    // localhost:8080/20  -> @PathVariable
    // localhost:8080?id=1  -> @RequestParams
    @GetMapping("/index")
    public String mostrarPacientePorId(Model model, @RequestParam Integer id) {
        PacienteResponseDTO paciente = pacienteService.buscarPorId(id);
        model.addAttribute("nombre", paciente.nombre());
        model.addAttribute("apellido", paciente.apellido());
        return "paciente";
    }

    @GetMapping("/index2/{id}")
    public String mostrarPacientePorId2(Model model, @PathVariable Integer id) {
        PacienteResponseDTO paciente = pacienteService.buscarPorId(id);
        model.addAttribute("nombre", paciente.nombre());
        model.addAttribute("apellido", paciente.apellido());
        return "paciente";
    }
}