package com.dh.clinica.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Entity
@Table(name = "odontologos")
@AllArgsConstructor
@NoArgsConstructor
public class Odontologo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    private String nombre;
    @NotBlank
    private String apellido;
    @NotBlank
    private String matricula;


    @OneToMany(mappedBy = "odontologo")
    @JsonIgnore
    private Set<Turno> turnos;

    public Odontologo(Integer id, String nombre, String apellido, String matricula) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.matricula = matricula;
    }
}