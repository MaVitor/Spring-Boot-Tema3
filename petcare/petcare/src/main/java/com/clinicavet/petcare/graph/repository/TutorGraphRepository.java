package com.clinicavet.petcare.graph.repository;

import com.clinicavet.petcare.graph.model.TutorNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface TutorGraphRepository extends Neo4jRepository<TutorNode, Long> {
}