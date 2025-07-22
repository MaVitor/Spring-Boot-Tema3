package com.clinicavet.petcare.service;

import com.clinicavet.petcare.graph.model.*;
import com.clinicavet.petcare.graph.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BackupService {

    // Serviços JPA
    private final PetService petService;
    private final TutorService tutorService;
    private final VeterinarioService veterinarioService;
    private final AtendimentoService atendimentoService;
    private final MedicamentoService medicamentoService;
    private final CirurgiaService cirurgiaService;

    // Repositórios Neo4j
    private final PetGraphRepository petGraphRepository;
    private final TutorGraphRepository tutorGraphRepository;
    private final VeterinarioGraphRepository veterinarioGraphRepository;
    private final AtendimentoGraphRepository atendimentoGraphRepository;
    private final MedicamentoGraphRepository medicamentoGraphRepository;
    private final CirurgiaGraphRepository cirurgiaGraphRepository;

    public BackupService(PetService petService, TutorService tutorService, VeterinarioService veterinarioService, AtendimentoService atendimentoService, MedicamentoService medicamentoService, CirurgiaService cirurgiaService, PetGraphRepository petGraphRepository, TutorGraphRepository tutorGraphRepository, VeterinarioGraphRepository veterinarioGraphRepository, AtendimentoGraphRepository atendimentoGraphRepository, MedicamentoGraphRepository medicamentoGraphRepository, CirurgiaGraphRepository cirurgiaGraphRepository) {
        this.petService = petService;
        this.tutorService = tutorService;
        this.veterinarioService = veterinarioService;
        this.atendimentoService = atendimentoService;
        this.medicamentoService = medicamentoService;
        this.cirurgiaService = cirurgiaService;
        this.petGraphRepository = petGraphRepository;
        this.tutorGraphRepository = tutorGraphRepository;
        this.veterinarioGraphRepository = veterinarioGraphRepository;
        this.atendimentoGraphRepository = atendimentoGraphRepository;
        this.medicamentoGraphRepository = medicamentoGraphRepository;
        this.cirurgiaGraphRepository = cirurgiaGraphRepository;
    }

    @Transactional("neo4jTransactionManager")
    public void backupAllData() {
        System.out.println("Iniciando backup simplificado para Neo4j...");
        
        clearBackupData();

        Map<Long, TutorNode> tutoresMap = backupTutores();
        Map<Long, VeterinarioNode> veterinariosMap = backupVeterinarios();
        Map<Long, PetNode> petsMap = backupPets();
        Map<Long, MedicamentoNode> medicamentosMap = backupMedicamentos();
        Map<Long, CirurgiaNode> cirurgiasMap = backupCirurgias();
        Map<Long, AtendimentoNode> atendimentosMap = backupAtendimentos();
        
        linkAtendimentos(atendimentosMap, petsMap, veterinariosMap);
        linkMedicamentos(medicamentosMap, petsMap);
        linkCirurgias(cirurgiasMap, petsMap);
        // Falta linkar Tutor e Pet, vamos adicionar
        linkTutoresEPets(tutoresMap, petsMap);
        
        System.out.println("Backup finalizado com sucesso!");
    }
    
    @Transactional("neo4jTransactionManager")
    public void clearBackupData() {
        // A ordem de deleção é importante se houver constraints, mas DETACH DELETE resolve isso.
        // Usar repositórios é mais limpo.
        atendimentoGraphRepository.deleteAll();
        cirurgiaGraphRepository.deleteAll();
        medicamentoGraphRepository.deleteAll();
        petGraphRepository.deleteAll();
        tutorGraphRepository.deleteAll();
        veterinarioGraphRepository.deleteAll();
        System.out.println("Dados anteriores do Neo4j limpos.");
    }
    
    // Métodos de backup de nós
    private Map<Long, TutorNode> backupTutores() {
        List<TutorNode> nodes = tutorService.listarTodos().stream()
                .map(t -> new TutorNode(t.getId(), t.getNome(), t.getContato()))
                .collect(Collectors.toList());
        tutorGraphRepository.saveAll(nodes);
        System.out.println("✓ Backup de " + nodes.size() + " tutores concluído.");
        return nodes.stream().collect(Collectors.toMap(TutorNode::getIdOriginal, Function.identity()));
    }

    private Map<Long, VeterinarioNode> backupVeterinarios() {
        List<VeterinarioNode> nodes = veterinarioService.listarTodos().stream()
                .map(v -> new VeterinarioNode(v.getId(), v.getNome(), v.getEspecialidade()))
                .collect(Collectors.toList());
        veterinarioGraphRepository.saveAll(nodes);
        System.out.println("✓ Backup de " + nodes.size() + " veterinários concluído.");
        return nodes.stream().collect(Collectors.toMap(VeterinarioNode::getIdOriginal, Function.identity()));
    }

    private Map<Long, PetNode> backupPets() {
        List<PetNode> nodes = petService.listarTodos().stream()
                .map(p -> new PetNode(p.getId(), p.getNome(), p.getIdade(), p.getTipo(), p.getRaca()))
                .collect(Collectors.toList());
        petGraphRepository.saveAll(nodes);
        System.out.println("✓ Backup de " + nodes.size() + " pets concluído.");
        return nodes.stream().collect(Collectors.toMap(PetNode::getIdOriginal, Function.identity()));
    }
    
    private Map<Long, MedicamentoNode> backupMedicamentos() {
        List<MedicamentoNode> nodes = medicamentoService.listarTodos().stream()
                .map(m -> new MedicamentoNode(m.getId(), m.getNome(), m.getDosagem()))
                .collect(Collectors.toList());
        medicamentoGraphRepository.saveAll(nodes);
        System.out.println("✓ Backup de " + nodes.size() + " medicamentos concluído.");
        return nodes.stream().collect(Collectors.toMap(MedicamentoNode::getIdOriginal, Function.identity()));
    }

    private Map<Long, CirurgiaNode> backupCirurgias() {
        List<CirurgiaNode> nodes = cirurgiaService.listarTodos().stream()
                .map(c -> new CirurgiaNode(c.getId(), c.getNome(), c.getData()))
                .collect(Collectors.toList());
        cirurgiaGraphRepository.saveAll(nodes);
        System.out.println("✓ Backup de " + nodes.size() + " cirurgias concluído.");
        return nodes.stream().collect(Collectors.toMap(CirurgiaNode::getIdOriginal, Function.identity()));
    }

    private Map<Long, AtendimentoNode> backupAtendimentos() {
        List<AtendimentoNode> nodes = atendimentoService.listarTodos().stream()
                .map(a -> new AtendimentoNode(a.getId(), a.getData(), a.getDescricao()))
                .collect(Collectors.toList());
        atendimentoGraphRepository.saveAll(nodes);
        System.out.println("✓ Backup de " + nodes.size() + " atendimentos concluído.");
        return nodes.stream().collect(Collectors.toMap(AtendimentoNode::getIdOriginal, Function.identity()));
    }

    // Métodos para criar os relacionamentos
    private void linkAtendimentos(Map<Long, AtendimentoNode> atendimentosMap, Map<Long, PetNode> petsMap, Map<Long, VeterinarioNode> veterinariosMap) {
        atendimentoService.listarTodos().forEach(original -> {
            AtendimentoNode atendimentoNode = atendimentosMap.get(original.getId());
            if (atendimentoNode != null && original.getPet() != null) {
                PetNode petNode = petsMap.get(original.getPet().getId());
                if (petNode != null) {
                    petNode.getAtendimentos().add(atendimentoNode);
                    if (original.getVeterinario() != null) {
                        atendimentoNode.setVeterinario(veterinariosMap.get(original.getVeterinario().getId()));
                    }
                    atendimentoGraphRepository.save(atendimentoNode);
                    petGraphRepository.save(petNode);
                }
            }
        });
        System.out.println("✓ Relacionamentos de atendimentos criados.");
    }
    
    private void linkMedicamentos(Map<Long, MedicamentoNode> medicamentosMap, Map<Long, PetNode> petsMap) {
        medicamentoService.listarTodos().forEach(original -> {
            if (original.getPet() != null) {
                PetNode petNode = petsMap.get(original.getPet().getId());
                MedicamentoNode medicamentoNode = medicamentosMap.get(original.getId());
                if (petNode != null && medicamentoNode != null) {
                    petNode.getMedicamentos().add(medicamentoNode);
                    petGraphRepository.save(petNode);
                }
            }
        });
        System.out.println("✓ Relacionamentos de medicamentos criados.");
    }
    
    private void linkCirurgias(Map<Long, CirurgiaNode> cirurgiasMap, Map<Long, PetNode> petsMap) {
        cirurgiaService.listarTodos().forEach(original -> {
            if (original.getPet() != null) {
                PetNode petNode = petsMap.get(original.getPet().getId());
                CirurgiaNode cirurgiaNode = cirurgiasMap.get(original.getId());
                if (petNode != null && cirurgiaNode != null) {
                    petNode.getCirurgias().add(cirurgiaNode);
                    petGraphRepository.save(petNode);
                }
            }
        });
        System.out.println("✓ Relacionamentos de cirurgias criados.");
    }

    // Adicionando o método para ligar tutores e pets
    private void linkTutoresEPets(Map<Long, TutorNode> tutoresMap, Map<Long, PetNode> petsMap) {
        petService.listarTodos().forEach(pet -> {
        });
        System.out.println("-> Atenção: Relacionamento Tutor-Pet não implementado no modelo JPA.");
    }

    public long getBackupCount() {
        return atendimentoGraphRepository.count() +
               cirurgiaGraphRepository.count() +
               medicamentoGraphRepository.count() +
               petGraphRepository.count() +
               tutorGraphRepository.count() +
               veterinarioGraphRepository.count();
    }
}