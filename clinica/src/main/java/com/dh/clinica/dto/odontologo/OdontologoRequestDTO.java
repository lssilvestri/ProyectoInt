package com.dh.clinica.dto.odontologo;

import jakarta.validation.constraints.NotNull;

public record OdontologoRequestDTO(
        @NotNull
        String nombre,
        @NotNull
        String apellido,
        @NotNull
        String matricula) {
}
