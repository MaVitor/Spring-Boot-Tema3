package com.clinicavet.petcare.graph.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDate;

@Node("Atendimento")
public class AtendimentoNode {

    @Id
    private final Long idOriginal;

    private LocalDate data;
    private String descricao;

    @Relationship(type = "REALIZOU_ATENDIMENTO", direction = Relationship.Direction.INCOMING)
    private VeterinarioNode veterinario;

    public AtendimentoNode(Long idOriginal, LocalDate data, String descricao) {
        this.idOriginal = idOriginal;
        this.data = data;
        this.descricao = descricao;
    }

    public void setVeterinario(VeterinarioNode veterinario) {
        this.veterinario = veterinario;
    }

    public Long getIdOriginal() { return idOriginal; }
}