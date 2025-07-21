package com.clinicavet.petcare.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataAgendada;

    @ManyToOne
    @JoinColumn(name = "pet_id", referencedColumnName = "id")
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "veterinario_id", referencedColumnName = "id")
    private Veterinario veterinario;

    @ManyToOne
    @JoinColumn(name = "atendimento_id", referencedColumnName = "id")
    private Atendimento atendimento;

    @ManyToOne
    @JoinColumn(name = "clinica_id", referencedColumnName = "id")
    private Clinica clinica;

    // Construtores
    public Agendamento() {}

    public Agendamento(LocalDate dataAgendada, Pet pet, Veterinario veterinario, Atendimento atendimento, Clinica clinica) {
        this.dataAgendada = dataAgendada;
        this.pet = pet;
        this.veterinario = veterinario;
        this.atendimento = atendimento;
        this.clinica = clinica;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataAgendada() {
        return dataAgendada;
    }

    public void setDataAgendada(LocalDate dataAgendada) {
        this.dataAgendada = dataAgendada;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }

    public Atendimento getAtendimento() {
        return atendimento;
    }

    public void setAtendimento(Atendimento atendimento) {
        this.atendimento = atendimento;
    }

    public Clinica getClinica() {
        return clinica;
    }

    public void setClinica(Clinica clinica) {
        this.clinica = clinica;
    }
}
