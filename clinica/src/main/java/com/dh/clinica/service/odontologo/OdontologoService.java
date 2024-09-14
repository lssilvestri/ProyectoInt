package com.dh.clinica.service.odontologo;

import com.dh.clinica.dto.odontologo.OdontologoModificarRequestDTO;
import com.dh.clinica.dto.odontologo.OdontologoRequestDTO;
import com.dh.clinica.dto.odontologo.OdontologoResponseDTO;
import com.dh.clinica.dto.turno.TurnoResponseDTO;
import com.dh.clinica.entity.Odontologo;
import com.dh.clinica.entity.Turno;
import com.dh.clinica.repository.IOdontologoRepository;
import com.dh.clinica.service.turno.TurnoService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OdontologoService implements IOdontologoService {

    private static final Logger logger = LoggerFactory.getLogger(OdontologoService.class);
    private final IOdontologoRepository odontologoRepository;
    private final TurnoService turnoService;

    @Autowired
    public OdontologoService(IOdontologoRepository odontologoRepository, TurnoService turnoService) {
        this.odontologoRepository = odontologoRepository;
        this.turnoService = turnoService;
    }

    @Override
    public OdontologoResponseDTO guardar(OdontologoRequestDTO nuevoOdontologo) {
        logger.info("Guardando un nuevo odontólogo: {}", nuevoOdontologo);
        Odontologo odontologo = new Odontologo();
        odontologo.setNombre(nuevoOdontologo.nombre());
        odontologo.setApellido(nuevoOdontologo.apellido());
        odontologo.setMatricula(nuevoOdontologo.matricula());

        Odontologo odontologoGuardado = odontologoRepository.save(odontologo);

        logger.info("Odontólogo guardado exitosamente con ID: {}", odontologoGuardado.getId());
        return OdontologoResponseDTO.fromEntity(odontologoGuardado);
    }

    @Override
    public OdontologoResponseDTO buscarPorId(Integer id) {
        logger.info("Buscando odontólogo con ID: {}", id);
        return odontologoRepository.findById(id)
                .map(odontologo -> {
                    logger.info("Odontólogo encontrado: {}", odontologo);
                    return OdontologoResponseDTO.fromEntity(odontologo);
                })
                .orElseThrow(() -> {
                    logger.error("Odontólogo no encontrado con ID: {}", id);
                    throw new EntityNotFoundException("Odontólogo no encontrado");
                });
    }

    @Override
    public OdontologoResponseDTO buscarPorMatricula(String matricula) {
        logger.info("Buscando odontólogo con matrícula: {}", matricula);
        return odontologoRepository.findByMatricula(matricula)
                .map(odontologo -> {
                    logger.info("Odontólogo encontrado: {}", odontologo);
                    return OdontologoResponseDTO.fromEntity(odontologo);
                })
                .orElseThrow(() -> {
                    logger.error("Odontólogo no encontrado con matrícula: {}", matricula);
                    throw new EntityNotFoundException("Odontólogo no encontrado");
                });
    }

    @Override
    public List<OdontologoResponseDTO> buscarTodos() {
        logger.info("Buscando todos los odontólogos");
        List<OdontologoResponseDTO> odontologos = odontologoRepository.findAll().stream()
                .map(OdontologoResponseDTO::fromEntity)
                .collect(Collectors.toList());

        logger.info("Se encontraron {} odontólogos", odontologos.size());
        return odontologos;
    }

    @Override
    public void modificar(OdontologoModificarRequestDTO odontologoModificado) {
        logger.info("Modificando el odontólogo con ID: {}", odontologoModificado.id());
        Odontologo odontologoEcontrado = odontologoRepository.findById(odontologoModificado.id())
                .orElseThrow(() -> {
                    logger.error("Odontólogo no encontrado con ID: {}", odontologoModificado.id());
                    throw new EntityNotFoundException("Odontólogo no encontrado");
                });

        Set<TurnoResponseDTO> turnosExistentes = turnoService.buscarTurnoOdontologo(odontologoModificado.id());

        Set<Turno> turnos = turnosExistentes.stream()
                .map(turno ->
                        new Turno(
                                turno.id(),
                                turno.fecha(),
                                turno.pacienteResponseDTO().toEntity(),
                                turno.odontologoResponseDTO().toEntity()
                        ))
                .collect(Collectors.toSet());

        odontologoEcontrado.setNombre(odontologoModificado.nombre());
        odontologoEcontrado.setApellido(odontologoModificado.apellido());
        odontologoEcontrado.setMatricula(String.valueOf(odontologoModificado.matricula()));
        odontologoEcontrado.setTurnos(turnos);

        odontologoRepository.save(odontologoEcontrado);
        logger.info("Odontólogo con ID: {} modificado exitosamente", odontologoEcontrado.getId());
    }

    @Override
    public void eliminar(Integer id) {
        logger.info("Eliminando odontólogo con ID: {}", id);
        if (odontologoRepository.existsById(id)) {
            odontologoRepository.deleteById(id);
            logger.info("Odontólogo con ID: {} eliminado exitosamente", id);
        } else {
            logger.error("Odontólogo no encontrado con ID: {}", id);
            throw new EntityNotFoundException("Odontólogo no encontrado");
        }
    }
}
