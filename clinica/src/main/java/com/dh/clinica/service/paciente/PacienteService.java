package com.dh.clinica.service.paciente;
import com.dh.clinica.dto.paciente.DomicilioDTO;
import com.dh.clinica.dto.paciente.PacienteModificarRequestDTO;
import com.dh.clinica.dto.paciente.PacienteRequestDTO;
import com.dh.clinica.dto.paciente.PacienteResponseDTO;
import com.dh.clinica.entity.Domicilio;
import com.dh.clinica.entity.Paciente;
import com.dh.clinica.exception.BadRequestException;
import com.dh.clinica.exception.ResourceNotFoundException;
import com.dh.clinica.repository.IPacienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PacienteService implements IPacienteService {
    private final IPacienteRepository pacienteRepository;
    private static final Logger logger = LoggerFactory.getLogger(PacienteService.class);

    public PacienteService(IPacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public PacienteResponseDTO guardar(PacienteRequestDTO nuevoPaciente) {
        logger.info("Guardando nuevo paciente: {}", nuevoPaciente);

        if (pacienteRepository.existsByDni(nuevoPaciente.dni())) {
            logger.error("Ya existe un paciente con el DNI: {}", nuevoPaciente.dni());
            throw new BadRequestException("Ya existe un paciente con el DNI " + nuevoPaciente.dni());
        }

        Domicilio domicilio = new Domicilio();
        domicilio.setCalle(nuevoPaciente.domicilio().calle());
        domicilio.setNumero(nuevoPaciente.domicilio().numero());
        domicilio.setLocalidad(nuevoPaciente.domicilio().localidad());
        domicilio.setProvincia(nuevoPaciente.domicilio().provincia());

        Paciente paciente = new Paciente();
        paciente.setNombre(nuevoPaciente.nombre());
        paciente.setApellido(nuevoPaciente.apellido());
        paciente.setDni(nuevoPaciente.dni());
        paciente.setFechaIngreso(nuevoPaciente.fechaIngreso());
        paciente.setDomicilio(domicilio);

        PacienteResponseDTO response = new PacienteResponseDTO(pacienteRepository.save(paciente));
        logger.info("Paciente guardado exitosamente: {}", response);

        return response;
    }

    @Override
    public PacienteResponseDTO buscarPorId(Integer id) {
        logger.info("Buscando paciente con ID: {}", id);

        PacienteResponseDTO response = pacienteRepository.findById(id)
                .map(PacienteResponseDTO::new)
                .orElseThrow(() -> {
                    logger.error("Paciente no encontrado con ID: {}", id);
                    return new ResourceNotFoundException("Paciente no encontrado");
                });

        logger.info("Paciente encontrado: {}", response);
        return response;
    }

    @Override
    public List<PacienteResponseDTO> buscarTodos() {
        logger.info("Buscando todos los pacientes");

        List<PacienteResponseDTO> response = pacienteRepository.findAll().stream()
                .map(PacienteResponseDTO::new)
                .collect(Collectors.toList());

        logger.info("Número de pacientes encontrados: {}", response.size());
        return response;
    }

    @Override
    public void modificar(PacienteModificarRequestDTO pacienteModificado) {
        logger.info("Modificando paciente con ID: {}", pacienteModificado.id());

        Paciente paciente = pacienteRepository.findById(pacienteModificado.id())
                .orElseThrow(() -> {
                    logger.error("Paciente no encontrado con ID: {}", pacienteModificado.id());
                    return new ResourceNotFoundException("Paciente no encontrado");
                });

        paciente.setNombre(pacienteModificado.nombre());
        paciente.setApellido(pacienteModificado.apellido());
        paciente.setDni(pacienteModificado.dni());
        paciente.setFechaIngreso(pacienteModificado.fechaIngreso());

        Domicilio domicilio = paciente.getDomicilio();
        DomicilioDTO nuevoDomicilio = pacienteModificado.domicilio();

        domicilio.setCalle(nuevoDomicilio.calle());
        domicilio.setNumero(nuevoDomicilio.numero());
        domicilio.setLocalidad(nuevoDomicilio.localidad());
        domicilio.setProvincia(nuevoDomicilio.provincia());

        pacienteRepository.save(paciente);
        logger.info("Paciente modificado exitosamente: {}", pacienteModificado);
    }

    @Override
    public void eliminar(Integer id) {
        logger.info("Eliminando paciente con ID: {}", id);

        if (pacienteRepository.existsById(id)) {
            pacienteRepository.deleteById(id);
            logger.info("Paciente eliminado exitosamente con ID: {}", id);
        } else {
            logger.error("Paciente no encontrado con ID: {}", id);
            throw new ResourceNotFoundException("Paciente no encontrado");
        }
    }
}
