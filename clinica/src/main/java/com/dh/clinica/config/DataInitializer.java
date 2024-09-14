package com.dh.clinica.config;

import com.dh.clinica.entity.Domicilio;
import com.dh.clinica.entity.Odontologo;
import com.dh.clinica.entity.Paciente;
import com.dh.clinica.repository.IOdontologoRepository;
import com.dh.clinica.repository.IPacienteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(IOdontologoRepository odontologoRepository, IPacienteRepository pacienteRepository) {
        return args -> {
            // Inicializar odontólogos
            odontologoRepository.save(new Odontologo(null, "Juan", "Pérez", "12345"));
            odontologoRepository.save(new Odontologo(null, "María", "González", "67890"));
            odontologoRepository.save(new Odontologo(null, "Carlos", "López", "54321"));

            // Inicializar pacientes con domicilios
            Domicilio domicilio1 = new Domicilio(null, "Calle 1", "123", "Localidad A", "Provincia A");
            Domicilio domicilio2 = new Domicilio(null, "Calle 2", "456", "Localidad B", "Provincia B");

            pacienteRepository.save(new Paciente(null, "Ana", "Martínez", "123456789", domicilio1, LocalDate.now()));
            pacienteRepository.save(new Paciente(null, "Luis", "Rodríguez", "22222222", domicilio2, LocalDate.now()));
        };
    }
}