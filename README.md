## 🛠 Pré-requisitos

Antes de começar, certifique-se de ter instalado:

### Software Necessário
- **Java 17+** - [Download](https://adoptium.net/)
- **PostgreSQL 12+** - [Download](https://www.postgresql.org/download/)
- **Neo4j Desktop** - [Download](https://neo4j.com/deployment-center/?gdb-selfmanaged&community)
- **Python 3.8+** - [Download](https://www.python.org/downloads/)
- **Git** - [Download](https://git-scm.com/)

## 🚀 Instalação

### 1. Clone o Repositório

git clone <url-do-repositorio>
cd petcare/petcare

## 🗄️ Configuração do Banco de Dados

### PostgreSQL

#### 1. Criar Banco de Dados

Realize a criação de um banco de dados chamado petcare pelo PGADMIN

### Neo4j

#### 1. Instalar e Configurar

1. Extraia o zip do neo4j
2. Verifique em qual diretório você fez a extração
3. Abra o CMD
4. Entre no diretório no qual você fez a extração do neo4j
5. Entre no neo4j-community-2025.06.2/bin
6. Digite o comando console neo4j.bat
7. Abra o site http://localhost:7474/ em algum navegador

## ▶️ Executando o Projeto

### 1. Configurar application.properties

Verifique se o arquivo src/main/resources/application.properties está correto de acordo com as credenciais que você criou:

# PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/petcare
spring.datasource.username=postgres
spring.datasource.password=postgres

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# Server
server.port=8080

# Neo4j
spring.neo4j.uri=bolt://localhost:7687
spring.neo4j.authentication.username=neo4j
spring.neo4j.authentication.password=neo4j
spring.neo4j.database=neo4j

### 2. Compilar e Executar

#### Windows:

./mvnw.cmd clean compile
./mvnw.cmd spring-boot:run

### 3. Verificar se Subiu
- **API**: http://localhost:8080
- **Swagger**: http://localhost:8080/swagger-ui.html
- **Actuator**: http://localhost:8080/actuator/health

## 📊 Populando o Banco

# Instalar dependências Python
pip install requests faker

# Executar script demo
python scripts/populate_demo.py

**Dados criados:**
- 3 tutores
- 2 veterinários
- 5 pets
- 4 medicamentos
- 3 atendimentos

## 🔄 Sistema de Backup Neo4j

### Como Funciona
O sistema sincroniza automaticamente dados do PostgreSQL para o Neo4j, criando um backup em formato de grafo.

### Executar Backup Manual

#### Via Swagger:
1. Acesse: http://localhost:8080/swagger-ui.html
2. Vá em **backup-controller**
3. Execute **POST** /api/backup/full

## 📡 API Endpoints

### Entidades Principais
- **Pets**: /api/pets
- **Tutores**: /api/tutores
- **Veterinários**: /api/veterinarios
- **Atendimentos**: /api/atendimentos
- **Medicamentos**: /api/medicamentos
- **Cirurgias**: /api/cirurgias
- **Clínicas**: /api/clinicas

### Backup
- **Backup Completo**: POST /api/backup/full
- **Status**: GET /api/backup/status
- **Limpar**: DELETE /api/backup/clear

### Operações CRUD
Todas as entidades suportam:
- **GET** /api/{entidade} - Listar todos
- **GET** /api/{entidade}/{id} - Buscar por ID
- **POST** /api/{entidade} - Criar novo
- **PUT** /api/{entidade}/{id} - Atualizar
- **DELETE** /api/{entidade}/{id} - Deletar

---
