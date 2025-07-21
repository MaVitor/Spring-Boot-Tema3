package com.clinicavet.petcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.clinicavet.petcare.model.Agendamento;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
}
