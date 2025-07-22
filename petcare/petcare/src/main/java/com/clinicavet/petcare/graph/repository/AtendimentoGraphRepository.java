package com.clinicavet.petcare.graph.repository;

import com.clinicavet.petcare.graph.model.AtendimentoNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface AtendimentoGraphRepository extends Neo4jRepository<AtendimentoNode, Long> {
}