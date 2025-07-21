package com.clinicavet.petcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.clinicavet.petcare.model.Clinica;

public interface ClinicaRepository extends JpaRepository<Clinica, Long> {
}