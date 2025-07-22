package com.clinicavet.petcare.graph.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import java.util.ArrayList;
import java.util.List;

@Node("Tutor")
public class TutorNode {

    @Id
    private final Long idOriginal;

    private String nome;
    private String contato;

    @Relationship(type = "TEM_PET", direction = Relationship.Direction.OUTGOING)
    private List<PetNode> pets = new ArrayList<>();

    public TutorNode(Long idOriginal, String nome, String contato) {
        this.idOriginal = idOriginal;
        this.nome = nome;
        this.contato = contato;
    }

    public void setPets(List<PetNode> pets) {
        this.pets = pets;
    }
    
    public Long getIdOriginal() { return idOriginal; }
    public List<PetNode> getPets() { return pets; }
}