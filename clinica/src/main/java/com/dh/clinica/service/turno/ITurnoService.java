package com.dh.clinica.service.turno;


import com.dh.clinica.dto.turno.TurnoModificarRequestDTO;
import com.dh.clinica.dto.turno.TurnoRequestDTO;
import com.dh.clinica.dto.turno.TurnoResponseDTO;

import java.util.List;
import java.util.Set;

public interface ITurnoService {
    TurnoResponseDTO guardar(TurnoRequestDTO nuevoTurno);

    TurnoResponseDTO buscarPorId(Integer id);

    List<TurnoResponseDTO> buscarTodos();

    void modificar(TurnoModificarRequestDTO turnoModificar);

    void eliminar(Integer id);

    Set<TurnoResponseDTO> buscarTurnoOdontologo(Integer idOdontologo);
}