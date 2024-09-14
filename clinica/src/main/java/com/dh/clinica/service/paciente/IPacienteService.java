package com.dh.clinica.service.paciente;

import com.dh.clinica.dto.paciente.PacienteModificarRequestDTO;
import com.dh.clinica.dto.paciente.PacienteRequestDTO;
import com.dh.clinica.dto.paciente.PacienteResponseDTO;

import java.util.List;

public interface IPacienteService {
    PacienteResponseDTO guardar(PacienteRequestDTO nuevoPaciente);

    PacienteResponseDTO buscarPorId(Integer id);

    PacienteResponseDTO buscarPorDni(String dni);

    List<PacienteResponseDTO> buscarTodos();

    void modificar(PacienteModificarRequestDTO nuevoPaciente);

    void eliminar(Integer id);
}