package com.dh.clinica.dto.paciente;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PacienteModificarRequestDTO(
        @NotNull
        Integer id,
        @NotNull
        String nombre,
        @NotNull
        String apellido,
        @NotNull
        String dni,
        @NotNull
        LocalDate fechaIngreso,
        @NotNull
        DomicilioDTO domicilio) {
}
