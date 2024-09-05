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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OdontologoService implements IOdontologoService {

    private final IOdontologoRepository odontologoRepository;
    private final TurnoService turnoService;

    @Autowired
    public OdontologoService(IOdontologoRepository odontologoRepository, TurnoService turnoService) {
        this.odontologoRepository = odontologoRepository;
        this.turnoService = turnoService;
    }

    @Override
    public OdontologoResponseDTO guardar(OdontologoRequestDTO nuevoOdontologo) {
        Odontologo odontologo = new Odontologo();
        odontologo.setNombre(nuevoOdontologo.nombre());
        odontologo.setApellido(nuevoOdontologo.apellido());
        odontologo.setMatricula(nuevoOdontologo.matricula());

        Odontologo odontologoGuardado = odontologoRepository.save(odontologo);

        return OdontologoResponseDTO.fromEntity(odontologoGuardado);
    }

    @Override
    public OdontologoResponseDTO buscarPorId(Integer id) {
        return odontologoRepository.findById(id)
                .map(OdontologoResponseDTO::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Odontólogo no encontrado"));
    }

    @Override
    public List<OdontologoResponseDTO> buscarTodos() {
        return odontologoRepository.findAll().stream()
                .map(OdontologoResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void modificar(OdontologoModificarRequestDTO odontologoModificado) {
        Odontologo odontologoEcontrado = odontologoRepository.findById(odontologoModificado.id())
                .orElseThrow(() -> new EntityNotFoundException("Odontólogo no encontrado"));

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
    }

    @Override
    public void eliminar(Integer id) {
        if (odontologoRepository.existsById(id)) odontologoRepository.deleteById(id);
        else throw new EntityNotFoundException("Odontólogo no encontrado");

    }
}