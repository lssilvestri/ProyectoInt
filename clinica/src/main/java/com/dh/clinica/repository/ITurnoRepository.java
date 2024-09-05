package com.dh.clinica.repository;

import com.dh.clinica.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ITurnoRepository extends JpaRepository<Turno, Integer> {
    @Query("Select t from Turno t join t.odontologo o where o.id = :idOdontologo")
    Set<Turno> buscarTurnoPorIdOdontologo(Integer idOdontologo);
}