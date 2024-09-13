package com.dh.clinica.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@Table(name = "pacientes")
@AllArgsConstructor
@NoArgsConstructor
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    private String apellido;
    @NotBlank
    private String nombre;
    @NotBlank
    @Size(min=7, max=15)
    private String dni;
    @NotNull
    private LocalDate fechaIngreso;

    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_domicilio")
    private Domicilio domicilio;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<Turno> turnoSet;

    public Paciente(Integer id, String nombre, String apellido, String dni, Domicilio domicilio, LocalDate fechaIngreso) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.domicilio = domicilio;
        this.fechaIngreso = fechaIngreso;
    }

}