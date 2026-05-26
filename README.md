# Java Swing Apps

Repositório de estudos com aplicações desktop em **Java Swing**, criadas com foco em aprendizado de interfaces gráficas, componentes visuais customizados e integração com banco de dados.

---

## Projetos Disponíveis

| Projeto | Descrição | Destaques |
|---------|-----------|-----------|
| [login-system](login-system/) | Sistema de Login & Cadastro com SQLite | Tema escuro, CardLayout, banco de dados embutido, validações |
| [calculator](calculator/) | Calculadora visual estilizada | Design rosa pastel, operações básicas, display customizado |
| [dialogs](dialogs/) | Demonstração de diálogos Swing | Erro, aviso e confirmação, tema dark |

---

## Estrutura do Repositório

```
java-swing-apps/
├── assets/                 # Recursos visuais compartilhados (ícones, imagens)
├── calculator/
│   └── Main.java           # Calculadora com design rosa
├── dialogs/
│   └── DialogApp.java      # Exemplos de JOptionPane
├── login-system/
│   ├── MainApp.java        # JFrame principal
│   ├── LoginPanel.java     # Tela de login
│   ├── RegisterPanel.java  # Tela de cadastro
│   ├── DatabaseConnection.java
│   ├── DatabaseSetup.java
│   ├── UserDAO.java
│   ├── User.java
│   ├── UIUtils.java
│   ├── ValidationUtils.java
│   ├── GhostText.java
│   ├── run.sh              # Script de execução
│   ├── sqlite-jdbc.jar     # Driver SQLite
│   ├── slf4j-api.jar       # Dependência de logging
│   ├── slf4j-simple.jar    # Implementação de logging
│   └── README.md           # Documentação detalhada
└── README.md               # Este arquivo
```

---

## Requisitos Gerais

- **Java JDK 8+** (JDK 17 ou superior recomendado)
- Para o `login-system`, não é necessário instalar banco de dados — o SQLite é embutido.

---

## Como Executar

### Calculadora

```bash
cd calculator
javac Main.java
java Main
```

### Diálogos

```bash
cd dialogs
javac DialogApp.java
java DialogApp
```

### Login System

Veja as instruções completas em [login-system/README.md](login-system/README.md).

Resumido:

```bash
cd login-system
javac -cp ".:sqlite-jdbc.jar:slf4j-api.jar:slf4j-simple.jar" *.java
java -cp ".:sqlite-jdbc.jar:slf4j-api.jar:slf4j-simple.jar" MainApp
```

Ou use o script:

```bash
cd login-system
chmod +x run.sh
./run.sh
```

> **Windows**: troque `:` por `;` nos comandos de classpath.

---

## Tecnologias Utilizadas

- Java Swing (GUI)
- CardLayout (navegação entre telas)
- SQLite + JDBC (persistência)
- SLF4J (logging)

---

## Licença

Projeto livre para estudo e modificação.
