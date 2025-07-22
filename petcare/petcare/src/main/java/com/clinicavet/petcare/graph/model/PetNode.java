package com.clinicavet.petcare.graph.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import java.util.ArrayList;
import java.util.List;

@Node("Pet")
public class PetNode {

    @Id
    private final Long idOriginal;

    private String nome;
    private Integer idade;
    private String tipo;
    private String raca;

    @Relationship(type = "TEM_MEDICAMENTO", direction = Relationship.Direction.OUTGOING)
    private List<MedicamentoNode> medicamentos = new ArrayList<>();

    @Relationship(type = "FEZ_CIRURGIA", direction = Relationship.Direction.OUTGOING)
    private List<CirurgiaNode> cirurgias = new ArrayList<>();

    @Relationship(type = "TEVE_ATENDIMENTO", direction = Relationship.Direction.OUTGOING)
    private List<AtendimentoNode> atendimentos = new ArrayList<>();

    public PetNode(Long idOriginal, String nome, Integer idade, String tipo, String raca) {
        this.idOriginal = idOriginal;
        this.nome = nome;
        this.idade = idade;
        this.tipo = tipo;
        this.raca = raca;
    }
    
    public void setMedicamentos(List<MedicamentoNode> medicamentos) { this.medicamentos = medicamentos; }
    public void setCirurgias(List<CirurgiaNode> cirurgias) { this.cirurgias = cirurgias; }
    public void setAtendimentos(List<AtendimentoNode> atendimentos) { this.atendimentos = atendimentos; }

    public Long getIdOriginal() { return idOriginal; }
    public List<AtendimentoNode> getAtendimentos() { return atendimentos; }
    public List<CirurgiaNode> getCirurgias() { return cirurgias; }
    public List<MedicamentoNode> getMedicamentos() { return medicamentos; }
}