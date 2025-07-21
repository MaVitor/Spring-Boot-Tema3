package com.clinicavet.petcare.controller;

import com.clinicavet.petcare.model.Clinica;
import com.clinicavet.petcare.service.ClinicaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clinicas")
@CrossOrigin(origins = "*")
public class ClinicaController {

    private final ClinicaService clinicaService;

    public ClinicaController(ClinicaService clinicaService) {
        this.clinicaService = clinicaService;
    }

    @PostMapping
    public ResponseEntity<Clinica> salvar(@RequestBody Clinica clinica) {
        return ResponseEntity.ok(clinicaService.salvar(clinica));
    }

    @GetMapping
    public ResponseEntity<List<Clinica>> listarTodos() {
        return ResponseEntity.ok(clinicaService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Clinica> buscarPorId(@PathVariable Long id) {
        return clinicaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Clinica> atualizar(@PathVariable Long id, @RequestBody Clinica clinica) {
        return ResponseEntity.ok(clinicaService.atualizar(id, clinica));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        clinicaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
} 
    
