# Sistema de Gestão de Serviços - Java Swing + SQLite

Sistema de gestão de serviços com interface gráfica em Java Swing e banco de dados SQLite embutido. Possui tela de login/cadastro e, após autenticação, um desktop profissional com barra de menu, toolbar e janelas internas (MDI).

---

## Estrutura do Projeto

```
login-system/
├── MainApp.java                    # JFrame principal com CardLayout (login/cadastro)
├── LoginPanel.java                 # Tela de login
├── RegisterPanel.java              # Tela de cadastro
├── ServiceManagementFrame.java     # Desktop principal com menu, toolbar e JDesktopPane
├── ClientInternalFrame.java        # Tela interna de Cadastro de Clientes
├── ServiceOrderInternalFrame.java  # Tela interna de Ordem de Serviço
├── DarkDialog.java                 # Dialogs customizados com tema escuro
├── IconUtils.java                  # Carregador de ícones dos assets
├── UIUtils.java                    # Estilização de componentes visuais
├── ValidationUtils.java            # Regras de validação de formulário
├── GhostText.java                  # Placeholder/Hint text para campos
├── DatabaseConnection.java         # Conexão com SQLite
├── DatabaseSetup.java              # Criação automática das tabelas
├── User.java                       # Modelo de usuário
├── UserDAO.java                    # Operações no banco de usuários
├── Client.java                     # Modelo de cliente
├── ClientDAO.java                  # Operações no banco de clientes
├── ServiceOrder.java               # Modelo de ordem de serviço
├── ServiceOrderDAO.java            # Operações no banco de OS
├── sqlite-jdbc.jar                 # Driver JDBC do SQLite
├── slf4j-api.jar                   # Dependência de logging
├── slf4j-simple.jar                # Implementação simples de logging
├── login_system.db                 # Banco de dados gerado automaticamente
└── run.sh                          # Script para compilar e executar
```

---

## Funcionalidades

### Autenticação
- **Cadastro de Usuários**: Valida nome, e-mail e senha; salva no banco SQLite
- **Login**: Autentica usuário consultando o banco de dados
- **Validações**:
  - E-mail deve conter `@` e domínio válido
  - Senha: mínimo 8 caracteres e 1 caractere especial
  - Nome: mínimo 3 caracteres
  - Confirmação de senha deve ser igual à senha

### Desktop Principal (MDI)
Após o login, o sistema abre um desktop maximizado com:
- **Barra de Menu**: Cadastro, Movimento, Relatório, Utilitário, Sobre, Ajuda
- **Toolbar**: Atalhos rápidos para Clientes, Serviços e Ordem de Serviço
- **JDesktopPane**: Área de trabalho para janelas internas
- **Menu de Contexto (Popup)**: Acesso rápido no desktop

### Cadastro de Clientes
- Formulário completo: Nome, Telefone, E-mail, Endereço
- Tabela com todos os clientes cadastrados
- Busca por nome em tempo real
- Ações: Novo, Salvar, Atualizar, Excluir

### Ordem de Serviço (OS)
- Vinculação com cliente via combo box
- Campos: Equipamento, Defeito, Serviço, Técnico, Valor, Status
- Status disponíveis: Aberta, Em andamento, Finalizada, Cancelada
- Tabela com todas as ordens de serviço
- Ações: Novo, Salvar, Atualizar, Excluir

### Utilitários
- **Calculadora**: Abre a calculadora do sistema operacional
- **Bloco de Notas**: Abre o editor de texto do sistema operacional

### Tema Escuro
- Fundo preto, campos cinza escuro, texto branco em toda a aplicação
- **Dialogs Customizados**: Popups modernos escuros substituindo o JOptionPane padrão
- Ghost Text: Placeholders que desaparecem ao digitar
- Foto Circular: Avatar no topo da tela de login (com fallback desenhado)

---

## Requisitos

- Java JDK 8 ou superior

> O driver JDBC do SQLite (`sqlite-jdbc.jar`) e suas dependências (`slf4j-api.jar`, `slf4j-simple.jar`) já estão incluídos no projeto. Não precisa baixar nada.

---

## Como Executar

### Opção 1: Script pronto (recomendado - Linux/Mac)

```bash
cd /caminho/para/login-system
chmod +x run.sh
./run.sh
```

### Opção 2: Linux/Mac (manual)

```bash
cd /caminho/para/login-system

# Compilar incluindo os JARs no classpath
javac -cp ".:sqlite-jdbc.jar:slf4j-api.jar:slf4j-simple.jar" *.java

# Executar incluindo os JARs no classpath
java -cp ".:sqlite-jdbc.jar:slf4j-api.jar:slf4j-simple.jar" MainApp
```

### Opção 3: Windows (manual)

Abra o **Prompt de Comando** (`cmd`) ou **PowerShell** na pasta do projeto:

```cmd
cd C:\caminho\para\login-system

:: Compilar incluindo os JARs no classpath
javac -cp .;sqlite-jdbc.jar;slf4j-api.jar;slf4j-simple.jar *.java

:: Executar incluindo os JARs no classpath
java -cp .;sqlite-jdbc.jar;slf4j-api.jar;slf4j-simple.jar MainApp
```

> **Dica**: No Windows, o separador do classpath é ponto-e-vírgula (`;`), não dois-pontos (`:`).

---

## Banco de Dados

O projeto usa **SQLite embutido**, igual ao Java DB do NetBeans:

- **Sem servidor**, sem instalação
- O arquivo `login_system.db` é criado automaticamente na mesma pasta
- As tabelas `users`, `clients` e `service_orders` são criadas automaticamente ao iniciar o app (`DatabaseSetup.init()`)

### Estrutura das Tabelas

```sql
CREATE TABLE users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL
);

CREATE TABLE clients (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    phone TEXT,
    email TEXT,
    address TEXT
);

CREATE TABLE service_orders (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    client_id INTEGER NOT NULL,
    equipment TEXT NOT NULL,
    defect TEXT,
    service TEXT,
    technician TEXT,
    value REAL,
    status TEXT NOT NULL DEFAULT 'Aberta',
    FOREIGN KEY(client_id) REFERENCES clients(id)
);
```

---

## Como visualizar o banco em tempo real

### Terminal (mais rápido)

Abra um terminal e execute:

```bash
cd /caminho/para/login-system
sqlite3 login_system.db
```

Dentro do prompt do SQLite:

```sql
-- Ver todos os usuarios cadastrados
SELECT * FROM users;

-- Ver todos os clientes
SELECT * FROM clients;

-- Ver todas as ordens de servico
SELECT * FROM service_orders;

-- Ver estrutura de todas as tabelas
.schema

-- Listar todas as tabelas
.tables

-- Sair do sqlite3
.quit
```

---

## Arquitetura

```
MainApp (JFrame)
├── CardLayout
│   ├── LoginPanel  → UserDAO → SQLite
│   └── RegisterPanel → UserDAO → SQLite
│
└── ServiceManagementFrame (JFrame - após login)
    ├── JMenuBar (Cadastro, Movimento, Relatório, Utilitário, Sobre, Ajuda)
    ├── JToolBar (Clientes, Serviços, OS)
    └── JDesktopPane
        ├── ClientInternalFrame → ClientDAO → SQLite
        └── ServiceOrderInternalFrame → ServiceOrderDAO → SQLite
```

- **UserDAO / ClientDAO / ServiceOrderDAO**: Camada de acesso a dados com PreparedStatement
- **DatabaseConnection**: Factory de conexões JDBC
- **DatabaseSetup**: Cria as tabelas automaticamente se não existirem

---

## Observações

- A senha é salva em texto plano por simplicidade (recomenda-se usar hashing como BCrypt em produção)
- Para usar no NetBeans: crie um novo projeto Java, copie os arquivos `.java` para `src/`, e adicione `sqlite-jdbc.jar`, `slf4j-api.jar` e `slf4j-simple.jar` em **Libraries**
- Para limpar os dados, basta apagar o arquivo `login_system.db`

---

## Licença

Projeto livre para estudo e modificação.
