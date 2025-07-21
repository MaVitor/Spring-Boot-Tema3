package com.clinicavet.petcare.service;

import com.clinicavet.petcare.model.Agendamento;
import com.clinicavet.petcare.repository.AgendamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;

    public AgendamentoService(AgendamentoRepository agendamentoRepository) {
        this.agendamentoRepository = agendamentoRepository;
    }

    public Agendamento salvar(Agendamento agendamento) {
        return agendamentoRepository.save(agendamento);
    }

    public List<Agendamento> listarTodos() {
        return agendamentoRepository.findAll();
    }

    public Optional<Agendamento> buscarPorId(Long id) {
        return agendamentoRepository.findById(id);
    }

    public Agendamento atualizar(Long id, Agendamento dadosAtualizados) {
        return agendamentoRepository.findById(id).map(agendamento -> {
            agendamento.setDataAgendada(dadosAtualizados.getDataAgendada());
            agendamento.setPet(dadosAtualizados.getPet());
            agendamento.setVeterinario(dadosAtualizados.getVeterinario());
            agendamento.setAtendimento(dadosAtualizados.getAtendimento());
            agendamento.setClinica(dadosAtualizados.getClinica());
            return agendamentoRepository.save(agendamento);
        }).orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));
    }

    public void deletar(Long id) {
        agendamentoRepository.deleteById(id);
    }
}
