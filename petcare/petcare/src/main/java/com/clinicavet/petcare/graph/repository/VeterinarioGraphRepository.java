package com.clinicavet.petcare.graph.repository;

import com.clinicavet.petcare.graph.model.VeterinarioNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface VeterinarioGraphRepository extends Neo4jRepository<VeterinarioNode, Long> {
}