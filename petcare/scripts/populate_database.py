import requests
import json
import random
from datetime import datetime, timedelta

# Configuração
BASE_URL = "http://localhost:8080/api"
headers = {'Content-Type': 'application/json'}

def create_tutores():
    """Cria 3 tutores para demo"""
    tutores_data = [
        {"nome": "João Silva", "contato": "(11) 99999-1111"},
        {"nome": "Maria Santos", "contato": "(11) 99999-2222"},
        {"nome": "Pedro Costa", "contato": "(11) 99999-3333"}
    ]
    
    tutores = []
    print("📋 Criando tutores...")
    
    for tutor_data in tutores_data:
        try:
            response = requests.post(f"{BASE_URL}/tutores", json=tutor_data, headers=headers)
            if response.status_code == 200:
                tutor = response.json()
                tutores.append(tutor)
                print(f"✓ {tutor['nome']}")
        except Exception as e:
            print(f"✗ Erro: {e}")
    
    return tutores

def create_veterinarios():
    """Cria 2 veterinários para demo"""
    vets_data = [
        {"nome": "Dr. Ana Oliveira", "especialidade": "Clínica Geral"},
        {"nome": "Dr. Carlos Lima", "especialidade": "Cirurgia"}
    ]
    
    veterinarios = []
    print("👨‍⚕️ Criando veterinários...")
    
    for vet_data in vets_data:
        try:
            response = requests.post(f"{BASE_URL}/veterinarios", json=vet_data, headers=headers)
            if response.status_code == 200:
                veterinario = response.json()
                veterinarios.append(veterinario)
                print(f"✓ {veterinario['nome']}")
        except Exception as e:
            print(f"✗ Erro: {e}")
    
    return veterinarios

def create_pets():
    """Cria 5 pets para demo"""
    pets_data = [
        {"nome": "Rex", "idade": 3, "tipo": "Cão", "raca": "Labrador"},
        {"nome": "Mimi", "idade": 2, "tipo": "Gato", "raca": "Persa"},
        {"nome": "Bob", "idade": 5, "tipo": "Cão", "raca": "Bulldog"},
        {"nome": "Luna", "idade": 1, "tipo": "Gato", "raca": "SRD"},
        {"nome": "Max", "idade": 4, "tipo": "Cão", "raca": "Golden Retriever"}
    ]
    
    pets = []
    print("🐕 Criando pets...")
    
    for pet_data in pets_data:
        try:
            response = requests.post(f"{BASE_URL}/pets", json=pet_data, headers=headers)
            if response.status_code == 200:
                pet = response.json()
                pets.append(pet)
                print(f"✓ {pet['nome']} ({pet['tipo']})")
        except Exception as e:
            print(f"✗ Erro: {e}")
    
    return pets

def create_medicamentos(pets):
    """Cria alguns medicamentos"""
    medicamentos_data = [
        {"nome": "Antibiótico", "dosagem": "2x ao dia"},
        {"nome": "Vermífugo", "dosagem": "Dose única"},
        {"nome": "Anti-inflamatório", "dosagem": "1x ao dia"},
        {"nome": "Vitamina", "dosagem": "1x por semana"}
    ]
    
    print("💊 Criando medicamentos...")
    
    for i, med_data in enumerate(medicamentos_data):
        if i < len(pets):
            med_data["pet"] = {"id": pets[i]['id']}
            try:
                response = requests.post(f"{BASE_URL}/medicamentos", json=med_data, headers=headers)
                if response.status_code == 200:
                    print(f"✓ {med_data['nome']} para {pets[i]['nome']}")
            except Exception as e:
                print(f"✗ Erro: {e}")

def create_atendimentos(pets, veterinarios):
    """Cria alguns atendimentos"""
    atendimentos_data = [
        {"data": "2024-01-15", "descricao": "Consulta de rotina"},
        {"data": "2024-02-10", "descricao": "Vacinação"},
        {"data": "2024-03-05", "descricao": "Exame clínico"}
    ]
    
    print("🏥 Criando atendimentos...")
    
    for i, atend_data in enumerate(atendimentos_data):
        if i < len(pets) and i < len(veterinarios):
            atend_data["pet"] = {"id": pets[i]['id']}
            atend_data["veterinario"] = {"id": veterinarios[i % len(veterinarios)]['id']}
            try:
                response = requests.post(f"{BASE_URL}/atendimentos", json=atend_data, headers=headers)
                if response.status_code == 200:
                    print(f"✓ {atend_data['descricao']} - {pets[i]['nome']}")
            except Exception as e:
                print(f"✗ Erro: {e}")

def trigger_backup():
    """Executa o backup"""
    print("\n🔄 Executando backup para Neo4j...")
    try:
        response = requests.post(f"{BASE_URL}/backup/full")
        if response.status_code == 200:
            result = response.json()
            print(f"✅ {result['message']}")
        else:
            print(f"❌ Erro no backup: {response.status_code}")
    except Exception as e:
        print(f"❌ Erro: {e}")

def main():
    """Função principal - versão demo"""
    print("🚀 DEMO - Populando banco para apresentação Neo4j")
    print("=" * 50)
    
    # Verificar API
    try:
        requests.get(f"{BASE_URL}/pets")
        print("✓ API conectada")
    except:
        print("❌ API não está rodando na porta 8080")
        return
    
    # Criar dados mínimos para demo
    tutores = create_tutores()
    veterinarios = create_veterinarios()
    pets = create_pets()
    
    if pets:
        create_medicamentos(pets)
        if veterinarios:
            create_atendimentos(pets, veterinarios)
    
    print("\n" + "=" * 50)
    print("📊 RESUMO DA DEMO:")
    print(f"• {len(tutores)} tutores")
    print(f"• {len(veterinarios)} veterinários") 
    print(f"• {len(pets)} pets")
    print("• 4 medicamentos")
    print("• 3 atendimentos")
    
    # Fazer backup automaticamente
    trigger_backup()
    
    print("\n🎯 PRONTO PARA DEMONSTRAÇÃO!")
    print("📍 Acesse Neo4j Browser: http://localhost:7474")
    print("🔍 Execute: MATCH (n) RETURN n")

if __name__ == "__main__":
    main()
