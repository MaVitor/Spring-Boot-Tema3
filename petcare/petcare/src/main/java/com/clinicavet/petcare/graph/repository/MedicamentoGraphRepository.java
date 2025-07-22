package com.clinicavet.petcare.graph.repository;

import com.clinicavet.petcare.graph.model.MedicamentoNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface MedicamentoGraphRepository extends Neo4jRepository<MedicamentoNode, Long> {
}