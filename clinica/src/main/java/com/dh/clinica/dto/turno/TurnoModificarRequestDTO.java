package com.dh.clinica.dto.turno;

import java.time.LocalDate;

public record TurnoModificarRequestDTO(
        Integer id,
        Integer paciente_id,
        Integer odontologo_id,
        LocalDate fecha) {
}
