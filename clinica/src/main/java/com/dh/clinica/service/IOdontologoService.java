package com.dh.clinica.service;

import com.dh.clinica.dto.request.OdontologoModifyDto;
import com.dh.clinica.dto.request.OdontologoRequestDto;
import com.dh.clinica.dto.response.OdontologoResponseDto;
import com.dh.clinica.entity.Odontologo;


import java.util.List;
import java.util.Optional;

public interface IOdontologoService {
    OdontologoResponseDto guardarOdontologo(OdontologoRequestDto odontologoRequestDto);
    Optional<OdontologoResponseDto> buscarPorId(Integer id);

    List<OdontologoResponseDto> buscarTodos();

    void modificarOdontologo(OdontologoModifyDto odontologoModifyDto);
    void eliminarOdontologo(Integer id);
}