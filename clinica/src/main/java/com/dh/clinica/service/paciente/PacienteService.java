package com.dh.clinica.service.paciente;

import com.dh.clinica.dto.paciente.PacienteModificarRequestDTO;
import com.dh.clinica.dto.paciente.PacienteRequestDTO;
import com.dh.clinica.dto.paciente.PacienteResponseDTO;
import com.dh.clinica.entity.Domicilio;
import com.dh.clinica.entity.Paciente;
import com.dh.clinica.repository.IPacienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PacienteService implements IPacienteService {
    private final IPacienteRepository pacienteRepository;

    public PacienteService(IPacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public PacienteResponseDTO guardar(PacienteRequestDTO nuevoPaciente) {

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

        return new PacienteResponseDTO(pacienteRepository.save(paciente));
    }

    @Override
    public PacienteResponseDTO buscarPorId(Integer id) {
        return pacienteRepository.findById(id)
                .map(PacienteResponseDTO::new)
                .orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado"));
    }

    @Override
    public List<PacienteResponseDTO> buscarTodos() {
        return pacienteRepository.findAll().stream()
                .map(PacienteResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public void modificar(PacienteModificarRequestDTO pacienteModificado) {
        Paciente paciente = pacienteRepository.findById(pacienteModificado.id())
                .orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado"));

        // Modificar los campos
        paciente.setNombre(pacienteModificado.nombre());
        paciente.setApellido(pacienteModificado.apellido());
        paciente.setDni(pacienteModificado.dni());
        paciente.setFechaIngreso(pacienteModificado.fechaIngreso());

        pacienteRepository.save(paciente);
    }

    @Override
    public void eliminar(Integer id) {
        if (pacienteRepository.existsById(id)) pacienteRepository.deleteById(id);
        else throw new EntityNotFoundException("Paciente no encontrado");
    }
}