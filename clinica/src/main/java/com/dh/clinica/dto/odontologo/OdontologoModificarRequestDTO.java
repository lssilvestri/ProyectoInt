package com.dh.clinica.dto.odontologo;

import jakarta.validation.constraints.NotNull;

public record OdontologoModificarRequestDTO(
        @NotNull
        Integer id,
        @NotNull
        String nombre,
        @NotNull
        String apellido,
        @NotNull
        Integer matricula
) {
}
