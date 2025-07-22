package com.clinicavet.petcare.graph.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

import java.time.LocalDate;

@Node("Cirurgia")
public class CirurgiaNode {

    @Id
    private final Long idOriginal;

    @Property("nome")
    private final String nome;

    @Property("data")
    private final LocalDate data;

    public CirurgiaNode(Long idOriginal, String nome, LocalDate data) {
        this.idOriginal = idOriginal;
        this.nome = nome;
        this.data = data;
    }

    public Long getIdOriginal() { return idOriginal; }
    public String getNome() { return nome; }
    public LocalDate getData() { return data; }
}