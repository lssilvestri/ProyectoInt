package com.dh.clinica.repository;

import com.dh.clinica.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPacienteRepository extends JpaRepository<Paciente, Integer> {
    Optional<Paciente> findByDni(String dni);
    boolean existsByDni(String dni);
}