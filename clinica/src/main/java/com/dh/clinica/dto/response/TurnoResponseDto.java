package com.dh.clinica.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TurnoResponseDto {
    private Integer id;
    private OdontologoResponseDto odontologoResponseDto;
    private PacienteResponseDto pacienteResponseDto;
    private String fecha;
}

