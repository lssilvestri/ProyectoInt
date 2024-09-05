package com.dh.clinica.dto.odontologo;

import com.dh.clinica.entity.Odontologo;

public record OdontologoResponseDTO(
        Integer id,
        String nombre,
        String apellido,
        String matricula) {

    public OdontologoResponseDTO(Odontologo odontologo) {
        this(
                odontologo.getId(),
                odontologo.getNombre(),
                odontologo.getApellido(),
                odontologo.getMatricula()
        );
    }

    public static OdontologoResponseDTO fromEntity(Odontologo odontologo) {
        return new OdontologoResponseDTO(
                odontologo.getId(),
                odontologo.getNombre(),
                odontologo.getApellido(),
                odontologo.getMatricula()
        );
    }

    public Odontologo toEntity() {
        return new Odontologo(
                this.id(),
                this.nombre(),
                this.apellido(),
                this.matricula()
        );
    }
}
