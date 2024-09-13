package com.dh.clinica.dto.turno;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record TurnoRequestDTO(
        @NotNull
        Integer paciente_id,
        @NotNull
        Integer odontologo_id,
        @NotNull
        LocalDate fecha) {
}
