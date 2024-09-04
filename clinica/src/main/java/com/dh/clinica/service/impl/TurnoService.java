package com.dh.clinica.service.impl;

import com.dh.clinica.dto.request.TurnoModifyDto;
import com.dh.clinica.dto.request.TurnoRequestDto;
import com.dh.clinica.dto.response.OdontologoResponseDto;
import com.dh.clinica.dto.response.PacienteResponseDto;
import com.dh.clinica.dto.response.TurnoResponseDto;
import com.dh.clinica.entity.Odontologo;
import com.dh.clinica.entity.Paciente;
import com.dh.clinica.entity.Turno;
import com.dh.clinica.repository.ITurnoRepository;
import com.dh.clinica.service.ITurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TurnoService implements ITurnoService {

    private final ITurnoRepository turnoRepository;
    private final PacienteService pacienteService;
    private final OdontologoService odontologoService;

    @Autowired
    public TurnoService(ITurnoRepository turnoRepository, PacienteService pacienteService, @Lazy OdontologoService odontologoService) {
        this.turnoRepository = turnoRepository;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
    }

    @Override
    public TurnoResponseDto guardarTurno(TurnoRequestDto turnoRequestDto) {
        Optional<Paciente> pacienteOpt = pacienteService.buscarPorId(turnoRequestDto.getPaciente_id());
        Optional<OdontologoResponseDto> odontologoOpt = odontologoService.buscarPorId(turnoRequestDto.getOdontologo_id());

        if (pacienteOpt.isPresent() && odontologoOpt.isPresent()) {
            Turno turno = new Turno();
            turno.setPaciente(pacienteOpt.get());
            turno.setOdontologo(convertirOdontologoResponseDtoAOdontologo(odontologoOpt.get()));
            turno.setFecha(LocalDate.parse(turnoRequestDto.getFecha()));

            Turno turnoGuardado = turnoRepository.save(turno);
            return convertirATurnoResponse(turnoGuardado);
        } else {
            // Si no se encuentran paciente u odontólogo, retorna null o lanza una excepción
            return null;
        }
    }

    @Override
    public Optional<TurnoResponseDto> buscarPorId(Integer id) {
        Optional<Turno> turnoOpt = turnoRepository.findById(id);
        return turnoOpt.map(this::convertirATurnoResponse);
    }

    @Override
    public List<TurnoResponseDto> buscarTodos() {
        List<Turno> turnos = turnoRepository.findAll();
        List<TurnoResponseDto> respuesta = new ArrayList<>();
        for (Turno turno : turnos) {
            respuesta.add(convertirATurnoResponse(turno));
        }
        return respuesta;
    }

    @Override
    public void modificarTurno(TurnoModifyDto turnoModifyDto) {
        Optional<Paciente> pacienteOpt = pacienteService.buscarPorId(turnoModifyDto.getPaciente_id());
        Optional<OdontologoResponseDto> odontologoOpt = odontologoService.buscarPorId(turnoModifyDto.getOdontologo_id());

        if (pacienteOpt.isPresent() && odontologoOpt.isPresent()) {
            Turno turno = new Turno(
                    turnoModifyDto.getId(),
                    pacienteOpt.get(),
                    convertirOdontologoResponseDtoAOdontologo(odontologoOpt.get()),
                    LocalDate.parse(turnoModifyDto.getFecha())
            );
            turnoRepository.save(turno);
        }
    }

    @Override
    public void eliminarTurno(Integer id) {
        turnoRepository.deleteById(id);
    }

    @Override
    public Set<Turno> buscarTurnoOdontologo(Integer idOdontologo) {
        return turnoRepository.buscarTurnoPorIdOdontologo(idOdontologo);
    }

    private TurnoResponseDto convertirATurnoResponse(Turno turno) {
        OdontologoResponseDto odontologoResponseDto = turno.getOdontologo().convertirAOdontologoResponseDto();
        PacienteResponseDto pacienteResponseDto = new PacienteResponseDto(
                turno.getPaciente().getId(),
                turno.getPaciente().getNombre(),
                turno.getPaciente().getApellido(),
                turno.getPaciente().getDni()
        );
        return new TurnoResponseDto(
                turno.getId(),
                odontologoResponseDto,
                pacienteResponseDto,
                turno.getFecha().toString()
        );
    }

    private Odontologo convertirOdontologoResponseDtoAOdontologo(OdontologoResponseDto odontologoResponseDto) {
        return new Odontologo(
                odontologoResponseDto.getId(),
                odontologoResponseDto.getNombre(),
                odontologoResponseDto.getApellido(),
                odontologoResponseDto.getMatricula(),
                null
        );
    }
}
