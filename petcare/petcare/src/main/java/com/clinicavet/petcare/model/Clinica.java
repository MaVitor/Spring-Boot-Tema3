package com.clinicavet.petcare.model;

import jakarta.persistence.*;

@Entity
public class Clinica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    

    private String nome;
    private String endereco;
    private String telefone;

    @ManyToOne
    @JoinColumn(name = "pet_id", referencedColumnName = "id")
    private Pet pet;

    public Clinica() {}

    public Clinica(String nome, String endereco, String telefone, Pet pet) {
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.pet = pet;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public Pet getPet() { return pet; }
    public void setPet(Pet pet) { this.pet = pet; }
} 
