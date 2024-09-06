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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(TurnoService.class);

    public TurnoService(ITurnoRepository turnoRepository, PacienteService pacienteService, @Lazy OdontologoService odontologoService) {
        this.turnoRepository = turnoRepository;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
    }

    @Override
    public TurnoResponseDTO guardar(TurnoRequestDTO nuevoTurno) {
        logger.info("Guardando nuevo turno: {}", nuevoTurno);

        PacienteResponseDTO paciente = pacienteService.buscarPorId(nuevoTurno.paciente_id());
        if (paciente == null) {
            logger.error("Paciente no encontrado con ID: {}", nuevoTurno.paciente_id());
            throw new EntityNotFoundException("Paciente no encontrado");
        }

        OdontologoResponseDTO odontologo = odontologoService.buscarPorId(nuevoTurno.odontologo_id());
        if (odontologo == null) {
            logger.error("Odontólogo no encontrado con ID: {}", nuevoTurno.odontologo_id());
            throw new EntityNotFoundException("Odontólogo no encontrado");
        }

        Turno turno = new Turno();
        turno.setPaciente(paciente.toEntity());
        turno.setOdontologo(odontologo.toEntity());
        turno.setFecha(nuevoTurno.fecha());

        TurnoResponseDTO response = new TurnoResponseDTO(turnoRepository.save(turno));
        logger.info("Turno guardado exitosamente: {}", response);

        return response;
    }

    @Override
    public TurnoResponseDTO buscarPorId(Integer id) {
        logger.info("Buscando turno con ID: {}", id);

        TurnoResponseDTO response = turnoRepository.findById(id)
                .map(TurnoResponseDTO::new)
                .orElseThrow(() -> {
                    logger.error("Turno no encontrado con ID: {}", id);
                    return new EntityNotFoundException("Turno no encontrado");
                });

        logger.info("Turno encontrado: {}", response);
        return response;
    }

    @Override
    public List<TurnoResponseDTO> buscarTodos() {
        logger.info("Buscando todos los turnos");

        List<TurnoResponseDTO> response = turnoRepository.findAll().stream()
                .map(TurnoResponseDTO::new)
                .collect(Collectors.toList());

        logger.info("Número de turnos encontrados: {}", response.size());
        return response;
    }

    @Override
    public void modificar(TurnoModificarRequestDTO turnoModificar) {
        logger.info("Modificando turno con ID: {}", turnoModificar.id());

        Turno turno = turnoRepository.findById(turnoModificar.id())
                .orElseThrow(() -> {
                    logger.error("Turno no encontrado con ID: {}", turnoModificar.id());
                    return new EntityNotFoundException("Turno no encontrado");
                });

        PacienteResponseDTO paciente = pacienteService.buscarPorId(turnoModificar.paciente_id());
        if (paciente == null) {
            logger.error("Paciente no encontrado con ID: {}", turnoModificar.paciente_id());
            throw new EntityNotFoundException("Paciente no encontrado");
        }

        OdontologoResponseDTO odontologo = odontologoService.buscarPorId(turnoModificar.odontologo_id());
        if (odontologo == null) {
            logger.error("Odontólogo no encontrado con ID: {}", turnoModificar.odontologo_id());
            throw new EntityNotFoundException("Odontólogo no encontrado");
        }

        turno.setPaciente(paciente.toEntity());
        turno.setOdontologo(odontologo.toEntity());
        turno.setFecha(turnoModificar.fecha());

        turnoRepository.save(turno);
        logger.info("Turno modificado exitosamente: {}", turnoModificar);
    }

    @Override
    public void eliminar(Integer id) {
        logger.info("Eliminando turno con ID: {}", id);

        if (turnoRepository.existsById(id)) {
            turnoRepository.deleteById(id);
            logger.info("Turno eliminado exitosamente con ID: {}", id);
        } else {
            logger.error("Turno no encontrado con ID: {}", id);
            throw new EntityNotFoundException("Turno no encontrado");
        }
    }

    @Override
    public Set<TurnoResponseDTO> buscarTurnoOdontologo(Integer idOdontologo) {
        logger.info("Buscando turnos para odontólogo con ID: {}", idOdontologo);

        Set<TurnoResponseDTO> response = turnoRepository.buscarTurnoPorIdOdontologo(idOdontologo).stream()
                .map(TurnoResponseDTO::new)
                .collect(Collectors.toSet());

        logger.info("Número de turnos encontrados para odontólogo con ID {}: {}", idOdontologo, response.size());
        return response;
    }
}
