package com.dh.clinica.service.odontologo;

import com.dh.clinica.dto.odontologo.OdontologoModificarRequestDTO;
import com.dh.clinica.dto.odontologo.OdontologoRequestDTO;
import com.dh.clinica.dto.odontologo.OdontologoResponseDTO;

import java.util.List;

public interface IOdontologoService {
    OdontologoResponseDTO guardar(OdontologoRequestDTO nuevoOdontologo);

    OdontologoResponseDTO buscarPorId(Integer id);

    OdontologoResponseDTO buscarPorMatricula(String matricula);

    List<OdontologoResponseDTO> buscarTodos();

    void modificar(OdontologoModificarRequestDTO odontologoModificado);

    void eliminar(Integer id);
}