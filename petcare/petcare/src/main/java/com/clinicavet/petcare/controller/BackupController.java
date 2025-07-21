package com.clinicavet.petcare.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clinicavet.petcare.service.BackupService;

@RestController
@RequestMapping("/api/backup")
@CrossOrigin(origins = "*")
public class BackupController {

    private final BackupService backupService;

    public BackupController(BackupService backupService) {
        this.backupService = backupService;
    }

    @PostMapping("/full")
    public ResponseEntity<Map<String, String>> realizarBackupCompleto() {
        try {
            backupService.backupAllData();
            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Backup completo realizado com sucesso!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Erro ao realizar backup: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Map<String, String>> limparBackup() {
        try {
            backupService.clearBackupData();
            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Dados de backup limpos com sucesso!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Erro ao limpar backup: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> statusBackup() {
        try {
            long count = backupService.getBackupCount();
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("backupCount", count);
            response.put("message", "Status do backup obtido com sucesso!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Erro ao obter status do backup: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
