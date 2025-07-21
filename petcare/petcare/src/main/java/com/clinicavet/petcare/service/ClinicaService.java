package com.clinicavet.petcare.service;

import com.clinicavet.petcare.model.Clinica;
import com.clinicavet.petcare.repository.ClinicaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClinicaService {

    private final ClinicaRepository clinicaRepository;

    public ClinicaService(ClinicaRepository clinicaRepository) {
        this.clinicaRepository = clinicaRepository;
    }

    public Clinica salvar(Clinica clinica) {
        return clinicaRepository.save(clinica);
    }

    public List<Clinica> listarTodos() {
        return clinicaRepository.findAll();
    }

    public Optional<Clinica> buscarPorId(Long id) {
        return clinicaRepository.findById(id);
    }

    public Clinica atualizar(Long id, Clinica clinica) {
        Clinica existente = clinicaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clínica não encontrada"));
        existente.setNome(clinica.getNome());
        existente.setEndereco(clinica.getEndereco());
        existente.setTelefone(clinica.getTelefone());
        return clinicaRepository.save(existente);
    }

    public void deletar(Long id) {
        clinicaRepository.deleteById(id);
    }
}
