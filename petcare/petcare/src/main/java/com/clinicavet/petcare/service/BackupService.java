package com.clinicavet.petcare.service;

import java.util.List;
import java.util.Map;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.springframework.stereotype.Service;

import com.clinicavet.petcare.model.Atendimento;
import com.clinicavet.petcare.model.Cirurgia;
import com.clinicavet.petcare.model.Medicamento;
import com.clinicavet.petcare.model.Pet;
import com.clinicavet.petcare.model.Tutor;
import com.clinicavet.petcare.model.Veterinario;

@Service
public class BackupService {

    private final PetService petService;
    private final TutorService tutorService;
    private final VeterinarioService veterinarioService;
    private final AtendimentoService atendimentoService;
    private final MedicamentoService medicamentoService;
    private final CirurgiaService cirurgiaService;
    private final Driver neo4jDriver;

    public BackupService(PetService petService, TutorService tutorService,
                        VeterinarioService veterinarioService, AtendimentoService atendimentoService,
                        MedicamentoService medicamentoService, CirurgiaService cirurgiaService,
                        Driver neo4jDriver) {
        this.petService = petService;
        this.tutorService = tutorService;
        this.veterinarioService = veterinarioService;
        this.atendimentoService = atendimentoService;
        this.medicamentoService = medicamentoService;
        this.cirurgiaService = cirurgiaService;
        this.neo4jDriver = neo4jDriver;
    }

    public void backupAllData() {
        System.out.println("Iniciando backup dos dados para Neo4j...");

        try (Session session = neo4jDriver.session()) {
            // Limpar dados existentes
            limparDadosAnteriores(session);

            // Fazer backup de cada entidade
            fazerBackupTutores(session);
            fazerBackupVeterinarios(session);
            fazerBackupPets(session);
            fazerBackupMedicamentos(session);
            fazerBackupCirurgias(session);
            fazerBackupAtendimentos(session);

            System.out.println("Backup finalizado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro durante o backup: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Falha no backup", e);
        }
    }

    private void limparDadosAnteriores(Session session) {
        try (Transaction tx = session.beginTransaction()) {
            tx.run("MATCH (n) DETACH DELETE n");
            tx.commit();
            System.out.println("Dados anteriores limpos.");
        }
    }

    private void fazerBackupTutores(Session session) {
        List<Tutor> tutores = tutorService.listarTodos();

        try (Transaction tx = session.beginTransaction()) {
            for (Tutor tutor : tutores) {
                tx.run("CREATE (t:Tutor {idOriginal: $idOriginal, nome: $nome, contato: $contato})",
                    Map.of(
                        "idOriginal", tutor.getId(),
                        "nome", tutor.getNome(),
                        "contato", tutor.getContato()
                    ));
                System.out.println("✓ Tutor salvo: " + tutor.getNome());
            }
            tx.commit();
            System.out.println("Backup de " + tutores.size() + " tutores concluído.");
        }
    }

    private void fazerBackupVeterinarios(Session session) {
        List<Veterinario> veterinarios = veterinarioService.listarTodos();

        try (Transaction tx = session.beginTransaction()) {
            for (Veterinario veterinario : veterinarios) {
                tx.run("CREATE (v:Veterinario {idOriginal: $idOriginal, nome: $nome, especialidade: $especialidade})",
                    Map.of(
                        "idOriginal", veterinario.getId(),
                        "nome", veterinario.getNome(),
                        "especialidade", veterinario.getEspecialidade()
                    ));
                System.out.println("✓ Veterinário salvo: " + veterinario.getNome());
            }
            tx.commit();
            System.out.println("Backup de " + veterinarios.size() + " veterinários concluído.");
        }
    }

    private void fazerBackupPets(Session session) {
        List<Pet> pets = petService.listarTodos();

        try (Transaction tx = session.beginTransaction()) {
            for (Pet pet : pets) {
                tx.run("CREATE (p:Pet {idOriginal: $idOriginal, nome: $nome, idade: $idade, tipo: $tipo, raca: $raca})",
                    Map.of(
                        "idOriginal", pet.getId(),
                        "nome", pet.getNome(),
                        "idade", pet.getIdade(),
                        "tipo", pet.getTipo(),
                        "raca", pet.getRaca()
                    ));
                System.out.println("✓ Pet salvo: " + pet.getNome());
            }
            tx.commit();
            System.out.println("Backup de " + pets.size() + " pets concluído.");
        }
    }

    private void fazerBackupMedicamentos(Session session) {
        List<Medicamento> medicamentos = medicamentoService.listarTodos();

        try (Transaction tx = session.beginTransaction()) {
            for (Medicamento medicamento : medicamentos) {
                // Criar o nó do medicamento
                tx.run("CREATE (m:Medicamento {idOriginal: $idOriginal, nome: $nome, dosagem: $dosagem, petId: $petId})",
                    Map.of(
                        "idOriginal", medicamento.getId(),
                        "nome", medicamento.getNome(),
                        "dosagem", medicamento.getDosagem(),
                        "petId", medicamento.getPet() != null ? medicamento.getPet().getId() : null
                    ));

                // Criar relacionamento com o pet se existir
                if (medicamento.getPet() != null) {
                    tx.run("MATCH (p:Pet {idOriginal: $petId}), (m:Medicamento {idOriginal: $medicamentoId}) " +
                           "CREATE (p)-[:TEM_MEDICAMENTO]->(m)",
                        Map.of(
                            "petId", medicamento.getPet().getId(),
                            "medicamentoId", medicamento.getId()
                        ));
                }
                System.out.println("✓ Medicamento salvo: " + medicamento.getNome());
            }
            tx.commit();
            System.out.println("Backup de " + medicamentos.size() + " medicamentos concluído.");
        }
    }

    private void fazerBackupCirurgias(Session session) {
        List<Cirurgia> cirurgias = cirurgiaService.listarTodos();

        try (Transaction tx = session.beginTransaction()) {
            for (Cirurgia cirurgia : cirurgias) {
                // Criar o nó da cirurgia
                tx.run("CREATE (c:Cirurgia {idOriginal: $idOriginal, nome: $nome, data: $data, petId: $petId})",
                    Map.of(
                        "idOriginal", cirurgia.getId(),
                        "nome", cirurgia.getNome(),
                        "data", cirurgia.getData().toString(),
                        "petId", cirurgia.getPet() != null ? cirurgia.getPet().getId() : null
                    ));

                // Criar relacionamento com o pet se existir
                if (cirurgia.getPet() != null) {
                    tx.run("MATCH (p:Pet {idOriginal: $petId}), (c:Cirurgia {idOriginal: $cirurgiaId}) " +
                           "CREATE (p)-[:FEZ_CIRURGIA]->(c)",
                        Map.of(
                            "petId", cirurgia.getPet().getId(),
                            "cirurgiaId", cirurgia.getId()
                        ));
                }
                System.out.println("✓ Cirurgia salva: " + cirurgia.getNome());
            }
            tx.commit();
            System.out.println("Backup de " + cirurgias.size() + " cirurgias concluído.");
        }
    }

    private void fazerBackupAtendimentos(Session session) {
        List<Atendimento> atendimentos = atendimentoService.listarTodos();

        try (Transaction tx = session.beginTransaction()) {
            for (Atendimento atendimento : atendimentos) {
                // Criar o nó do atendimento
                tx.run("CREATE (a:Atendimento {idOriginal: $idOriginal, data: $data, descricao: $descricao, petId: $petId, veterinarioId: $veterinarioId})",
                    Map.of(
                        "idOriginal", atendimento.getId(),
                        "data", atendimento.getData().toString(),
                        "descricao", atendimento.getDescricao(),
                        "petId", atendimento.getPet() != null ? atendimento.getPet().getId() : null,
                        "veterinarioId", atendimento.getVeterinario() != null ? atendimento.getVeterinario().getId() : null
                    ));

                // Criar relacionamento com o pet se existir
                if (atendimento.getPet() != null) {
                    tx.run("MATCH (p:Pet {idOriginal: $petId}), (a:Atendimento {idOriginal: $atendimentoId}) " +
                           "CREATE (p)-[:TEVE_ATENDIMENTO]->(a)",
                        Map.of(
                            "petId", atendimento.getPet().getId(),
                            "atendimentoId", atendimento.getId()
                        ));
                }

                // Criar relacionamento com o veterinário se existir
                if (atendimento.getVeterinario() != null) {
                    tx.run("MATCH (v:Veterinario {idOriginal: $veterinarioId}), (a:Atendimento {idOriginal: $atendimentoId}) " +
                           "CREATE (v)-[:REALIZOU_ATENDIMENTO]->(a)",
                        Map.of(
                            "veterinarioId", atendimento.getVeterinario().getId(),
                            "atendimentoId", atendimento.getId()
                        ));
                }
                System.out.println("✓ Atendimento salvo: " + atendimento.getDescricao());
            }
            tx.commit();
            System.out.println("Backup de " + atendimentos.size() + " atendimentos concluído.");
        }
    }

    public void clearBackupData() {
        System.out.println("Limpando dados de backup do Neo4j...");

        try (Session session = neo4jDriver.session()) {
            limparDadosAnteriores(session);
            System.out.println("Dados de backup limpos!");
        } catch (Exception e) {
            System.out.println("Erro ao limpar dados: " + e.getMessage());
            throw new RuntimeException("Falha ao limpar backup", e);
        }
    }

    public long getBackupCount() {
        try (Session session = neo4jDriver.session()) {
            var result = session.run("MATCH (n) RETURN count(n) as total");
            if (result.hasNext()) {
                return result.next().get("total").asLong();
            }
            return 0;
        } catch (Exception e) {
            System.out.println("Erro ao contar registros: " + e.getMessage());
            return 0;
        }
    }
}
