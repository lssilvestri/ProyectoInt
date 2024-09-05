package com.dh.clinica.dto.paciente;

import java.time.LocalDate;

public record PacienteModificarRequestDTO(
        Integer id,
        String nombre,
        String apellido,
        String dni,
        LocalDate fechaIngreso,
        DomicilioDTO domicilio) {
}
