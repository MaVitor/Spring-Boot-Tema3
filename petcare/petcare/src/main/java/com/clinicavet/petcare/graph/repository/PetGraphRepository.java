package com.clinicavet.petcare.graph.repository;

import com.clinicavet.petcare.graph.model.PetNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface PetGraphRepository extends Neo4jRepository<PetNode, Long> {
}