package com.dh.clinica.service.impl;

import com.dh.clinica.dto.request.OdontologoModifyDto;
import com.dh.clinica.dto.request.OdontologoRequestDto;
import com.dh.clinica.dto.response.OdontologoResponseDto;
import com.dh.clinica.entity.Odontologo;
import com.dh.clinica.entity.Turno;
import com.dh.clinica.repository.IOdontologoRepository;
import com.dh.clinica.service.IOdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OdontologoService implements IOdontologoService {

    private final IOdontologoRepository iOdontologoRepository;
    private final TurnoService turnoService;

    @Autowired
    public OdontologoService(IOdontologoRepository iOdontologoRepository, TurnoService turnoService) {
        this.iOdontologoRepository = iOdontologoRepository;
        this.turnoService = turnoService;
    }


    @Override
    public OdontologoResponseDto guardarOdontologo(OdontologoRequestDto odontologoRequestDto) {
        Odontologo odontologo = new Odontologo();
        odontologo.setNombre(odontologoRequestDto.getNombre());
        odontologo.setApellido(odontologoRequestDto.getApellido());
        odontologo.setNroMatricula(odontologoRequestDto.getMatricula());
        Odontologo odontologoDesdeDb = iOdontologoRepository.save(odontologo);
        return convertirAOdontologoResponse(odontologoDesdeDb);
    }

    @Override
    public Optional<OdontologoResponseDto> buscarPorId(Integer id) {
        return iOdontologoRepository.findById(id)
                .map(this::convertirAOdontologoResponse);
    }

    @Override
    public List<OdontologoResponseDto> buscarTodos() {
        return iOdontologoRepository.findAll().stream()
                .map(this::convertirAOdontologoResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void modificarOdontologo(OdontologoModifyDto odontologoModifyDto) {
        Set<Turno> turnos = turnoService.buscarTurnoOdontologo(odontologoModifyDto.getId());
        Odontologo odontologo = new Odontologo(
                odontologoModifyDto.getId(),
                odontologoModifyDto.getNombre(),
                odontologoModifyDto.getApellido(),
                odontologoModifyDto.getMatricula(),
                turnos
        );
        iOdontologoRepository.save(odontologo);
    }

    @Override
    public void eliminarOdontologo(Integer id) {
        iOdontologoRepository.deleteById(id);
    }

    private OdontologoResponseDto convertirAOdontologoResponse(Odontologo odontologo) {
        return new OdontologoResponseDto(
                odontologo.getId(),
                odontologo.getNombre(),
                odontologo.getApellido(),
                odontologo.getNroMatricula()
        );
    }
}