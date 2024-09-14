package com.dh.clinica.dto.paciente;

import com.dh.clinica.entity.Domicilio;

public record DomicilioDTO(
        Long id,
        String calle,
        String numero,
        String localidad,
        String provincia) {

    public DomicilioDTO(Domicilio domicilio) {
        this(
                domicilio.getId(),
                domicilio.getCalle(),
                domicilio.getNumero(),
                domicilio.getLocalidad(),
                domicilio.getProvincia());
    }

    public Domicilio toEntity() {
        Domicilio domicilio = new Domicilio();
        domicilio.setId(this.id());
        domicilio.setCalle(this.calle());
        domicilio.setNumero(this.numero());
        domicilio.setLocalidad(this.localidad());
        domicilio.setProvincia(this.provincia());
        return domicilio;
    }
}
