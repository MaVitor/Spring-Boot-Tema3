package com.clinicavet.petcare.graph.repository;

import com.clinicavet.petcare.graph.model.CirurgiaNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface CirurgiaGraphRepository extends Neo4jRepository<CirurgiaNode, Long> {
}