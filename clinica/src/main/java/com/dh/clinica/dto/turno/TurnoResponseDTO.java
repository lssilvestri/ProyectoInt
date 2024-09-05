package com.dh.clinica.dto.turno;

import com.dh.clinica.dto.odontologo.OdontologoResponseDTO;
import com.dh.clinica.dto.paciente.PacienteResponseDTO;
import com.dh.clinica.entity.Turno;

import java.time.LocalDate;

public record TurnoResponseDTO(
        Integer id,
        OdontologoResponseDTO odontologoResponseDTO,
        PacienteResponseDTO pacienteResponseDTO,
        LocalDate fecha) {
    public TurnoResponseDTO(Turno save) {
        this(
                save.getId(),
                new OdontologoResponseDTO(save.getOdontologo()),
                new PacienteResponseDTO(save.getPaciente()),
                save.getFecha());
    }

    public Object paciente() {
        return null;
    }

    public Object odontologo() {
        return null;
    }
}
