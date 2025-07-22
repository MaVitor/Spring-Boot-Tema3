package com.clinicavet.petcare.graph.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Node("Medicamento")
public class MedicamentoNode {

    @Id
    private final Long idOriginal;

    @Property("nome")
    private final String nome;

    @Property("dosagem")
    private final String dosagem;

    public MedicamentoNode(Long idOriginal, String nome, String dosagem) {
        this.idOriginal = idOriginal;
        this.nome = nome;
        this.dosagem = dosagem;
    }

    public Long getIdOriginal() { return idOriginal; }
    public String getNome() { return nome; }
    public String getDosagem() { return dosagem; }
}