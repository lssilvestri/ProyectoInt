package com.dh.clinica.dto.paciente;

import java.time.LocalDate;

public record PacienteRequestDTO(
        String apellido,
        String nombre,
        String dni,
        LocalDate fechaIngreso,
        DomicilioDTO domicilio) {
}

