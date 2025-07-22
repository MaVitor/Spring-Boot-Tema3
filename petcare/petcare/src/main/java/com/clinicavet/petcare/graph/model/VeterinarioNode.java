package com.clinicavet.petcare.graph.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("Veterinario")
public class VeterinarioNode {

    @Id
    private final Long idOriginal;

    private String nome;
    private String especialidade;

    public VeterinarioNode(Long idOriginal, String nome, String especialidade) {
        this.idOriginal = idOriginal;
        this.nome = nome;
        this.especialidade = especialidade;
    }

    public Long getIdOriginal() { return idOriginal; }
    public String getNome() { return nome; }
    public String getEspecialidade() { return especialidade; }
}