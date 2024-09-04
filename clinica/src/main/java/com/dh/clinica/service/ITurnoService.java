package com.dh.clinica.service;


import com.dh.clinica.dto.request.TurnoModifyDto;
import com.dh.clinica.dto.request.TurnoRequestDto;
import com.dh.clinica.dto.response.TurnoResponseDto;
import com.dh.clinica.entity.Turno;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ITurnoService {
    TurnoResponseDto guardarTurno(TurnoRequestDto turnoRequestDto);

    Optional<TurnoResponseDto> buscarPorId(Integer id);

    List<TurnoResponseDto> buscarTodos();

    void modificarTurno(TurnoModifyDto turnoModifyDto);
    void eliminarTurno(Integer id);
    Set<Turno> buscarTurnoOdontologo(Integer idOdontologo);
}