package com.dh.clinica.dto.paciente;

import com.dh.clinica.entity.Paciente;

import java.time.LocalDate;

public record PacienteResponseDTO(
        Integer id,
        String nombre,
        String apellido,
        String dni,
        DomicilioDTO domicilio,
        LocalDate fechaIngreso) {


    public PacienteResponseDTO(Paciente save) {
        this(
                save.getId(),
                save.getNombre(),
                save.getApellido(),
                save.getDni(),
                new DomicilioDTO(save.getDomicilio()),
                save.getFechaIngreso());
    }

    public Paciente toEntity() {
        return new Paciente(
                this.id(),
                this.nombre(),
                this.apellido(),
                this.dni(),
                this.domicilio().toEntity(),
                this.fechaIngreso());
    }
}

