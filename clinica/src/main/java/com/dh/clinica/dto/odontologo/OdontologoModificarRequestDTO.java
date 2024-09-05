package com.dh.clinica.dto.odontologo;

public record OdontologoModificarRequestDTO(
        Integer id,
        String nombre,
        String apellido,
        Integer matricula
) {
}
