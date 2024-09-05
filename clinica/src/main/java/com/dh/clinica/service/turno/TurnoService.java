package com.dh.clinica.service.turno;

import com.dh.clinica.dto.odontologo.OdontologoResponseDTO;
import com.dh.clinica.dto.paciente.PacienteResponseDTO;
import com.dh.clinica.dto.turno.TurnoModificarRequestDTO;
import com.dh.clinica.dto.turno.TurnoRequestDTO;
import com.dh.clinica.dto.turno.TurnoResponseDTO;
import com.dh.clinica.entity.Turno;
import com.dh.clinica.repository.ITurnoRepository;
import com.dh.clinica.service.odontologo.OdontologoService;
import com.dh.clinica.service.paciente.PacienteService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TurnoService implements ITurnoService {

    private final ITurnoRepository turnoRepository;
    private final PacienteService pacienteService;
    private final OdontologoService odontologoService;

    public TurnoService(ITurnoRepository turnoRepository, PacienteService pacienteService, @Lazy OdontologoService odontologoService) {
        this.turnoRepository = turnoRepository;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
    }

    @Override
    public TurnoResponseDTO guardar(TurnoRequestDTO nuevoTurno) {
        PacienteResponseDTO paciente = pacienteService.buscarPorId(nuevoTurno.paciente_id());
        if (paciente == null) throw new EntityNotFoundException("Paciente no encontrado");

        OdontologoResponseDTO odontologo = odontologoService.buscarPorId(nuevoTurno.odontologo_id());
        if (odontologo == null) throw new EntityNotFoundException("Odontólogo no encontrado");

        Turno turno = new Turno();
        turno.setPaciente(paciente.toEntity());
        turno.setOdontologo(odontologo.toEntity());
        turno.setFecha(nuevoTurno.fecha());

        return new TurnoResponseDTO(turnoRepository.save(turno));
    }


    @Override
    public TurnoResponseDTO buscarPorId(Integer id) {
        return turnoRepository.findById(id)
                .map(TurnoResponseDTO::new)
                .orElseThrow(() -> new EntityNotFoundException("Turno no encontrado"));
    }

    @Override
    public List<TurnoResponseDTO> buscarTodos() {
        return turnoRepository.findAll().stream()
                .map(TurnoResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public void modificar(TurnoModificarRequestDTO turnoModificar) {

        Turno turno = turnoRepository.findById(turnoModificar.id())
                .orElseThrow(() -> new EntityNotFoundException("Turno no encontrado"));

        PacienteResponseDTO paciente = pacienteService.buscarPorId(turnoModificar.paciente_id());
        if (paciente == null) throw new EntityNotFoundException("Paciente no encontrado");

        OdontologoResponseDTO odontologo = odontologoService.buscarPorId(turnoModificar.odontologo_id());
        if (odontologo == null) throw new EntityNotFoundException("Odontólogo no encontrado");

        turno.setPaciente(paciente.toEntity());
        turno.setOdontologo(odontologo.toEntity());
        turno.setFecha(turnoModificar.fecha());

        turnoRepository.save(turno);
    }

    @Override
    public void eliminar(Integer id) {
        if (turnoRepository.existsById(id)) turnoRepository.deleteById(id);
        else throw new EntityNotFoundException("Turno no encontrado");
    }

    @Override
    public Set<TurnoResponseDTO> buscarTurnoOdontologo(Integer idOdontologo) {
        return turnoRepository.buscarTurnoPorIdOdontologo(idOdontologo).stream()
                .map(TurnoResponseDTO::new)
                .collect(Collectors.toSet());
    }
}
