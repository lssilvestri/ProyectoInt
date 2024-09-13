package com.dh.clinica.dto.paciente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PacienteRequestDTO(
        @NotBlank
        String apellido,
        @NotBlank
        String nombre,
        @NotBlank
        @Size(min=7, max=15)
        String dni,
        @NotNull
        LocalDate fechaIngreso,
        @NotNull
        DomicilioDTO domicilio) {
}

