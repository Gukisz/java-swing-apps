# Login System - Java Swing + SQLite

Sistema de login e cadastro com interface gráfica em Java Swing e banco de dados SQLite embutido.

---

## Estrutura do Projeto

```
login-system/
├── MainApp.java              # JFrame principal com CardLayout
├── LoginPanel.java           # Tela de login
├── RegisterPanel.java        # Tela de cadastro
├── UIUtils.java              # Estilização de componentes visuais
├── ValidationUtils.java      # Regras de validação de formulário
├── GhostText.java            # Placeholder/Hint text para campos
├── DatabaseConnection.java   # Conexão com SQLite
├── DatabaseSetup.java        # Criação automática da tabela
├── User.java                 # Modelo de usuário (POJO)
├── UserDAO.java              # Operações no banco (inserir, buscar)
├── sqlite-jdbc.jar           # Driver JDBC do SQLite (já incluído)
├── slf4j-api.jar             # Dependência de logging (já incluído)
├── slf4j-simple.jar          # Implementação simples de logging (já incluído)
├── login_system.db           # Banco de dados gerado automaticamente
└── run.sh                    # Script para compilar e executar
```

---

## Funcionalidades

- **Cadastro de Usuários**: Valida nome, e-mail e senha; salva no banco SQLite
- **Login**: Autentica usuário consultando o banco de dados
- **Validações**:
  - E-mail deve conter `@` e domínio válido
  - Senha: mínimo 8 caracteres e 1 caractere especial
  - Nome: mínimo 3 caracteres
  - Confirmação de senha deve ser igual à senha
- **Tema Escuro**: Fundo preto, campos cinza escuro, texto branco
- **Ghost Text**: Placeholders que desaparecem ao digitar
- **Foto Circular**: Avatar no topo da tela de login (com fallback desenhado)

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
- A tabela `users` é criada automaticamente ao iniciar o app (`DatabaseSetup.init()`)

### Estrutura da Tabela

```sql
CREATE TABLE users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL
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

Dentro do prompt do SQLite, use estes comandos para verificar o banco:

```sql
-- Ver todos os usuarios cadastrados
SELECT * FROM users;

-- Ver apenas nome e email
SELECT name, email FROM users;

-- Contar quantos usuarios existem
SELECT COUNT(*) FROM users;

-- Buscar um usuario especifico pelo email
SELECT * FROM users WHERE email = 'teste@email.com';

-- Ver a estrutura da tabela
.schema users

-- Listar todas as tabelas do banco
.tables

-- Ver informacoes da tabela (indices, etc.)
PRAGMA table_info(users);

-- Inserir um usuario manualmente (para testes)
INSERT INTO users (name, email, password) VALUES ('Joao Silva', 'joao@teste.com', 'Senha@123');

-- Apagar todos os usuarios (cuidado!)
DELETE FROM users;

-- Sair do sqlite3
.quit
```

### Comando direto (sem entrar no prompt)

```bash
# Ver todos os usuarios
sqlite3 /caminho/para/login-system/login_system.db "SELECT * FROM users;"

# Ver contagem
sqlite3 /caminho/para/login-system/login_system.db "SELECT COUNT(*) FROM users;"

# Ver estrutura da tabela
sqlite3 /caminho/para/login-system/login_system.db ".schema users"
```

### Interface Gráfica (DB Browser for SQLite)

```bash
# Instalar (Linux)
sudo apt install sqlitebrowser

# Abrir o banco
QT_QPA_PLATFORM=xcb sqlitebrowser /caminho/para/login-system/login_system.db
```

> Se der erro de Wayland, force o backend X11 com `QT_QPA_PLATFORM=xcb` antes do comando.

### Exemplo de saida esperada

```
sqlite> SELECT * FROM users;
1|Joao Silva|joao@teste.com|Senha@123
2|Maria Souza|maria@teste.com|Minha@Senha1
```

---

## Arquitetura

```
MainApp (JFrame)
├── CardLayout
│   ├── LoginPanel  → UserDAO → SQLite (login_system.db)
│   └── RegisterPanel → UserDAO → SQLite (login_system.db)
```

- **UserDAO**: Camada de acesso a dados com PreparedStatement
- **DatabaseConnection**: Factory de conexões JDBC
- **DatabaseSetup**: Cria a tabela automaticamente se não existir

---

## Observações

- A senha é salva em texto plano por simplicidade (recomenda-se usar hashing como BCrypt em produção)
- Para usar no NetBeans: crie um novo projeto Java, copie os arquivos `.java` para `src/`, e adicione `sqlite-jdbc.jar`, `slf4j-api.jar` e `slf4j-simple.jar` em **Libraries** (clique direito em Libraries → Add JAR/Folder)
- Para limpar os dados, basta apagar o arquivo `login_system.db`

---

## Licença

Projeto livre para estudo e modificação.
