<div align="center">
  <h1>Java Swing Apps</h1>
  <p><strong>Coleção de Aplicações Desktop em Java Swing</strong></p>
  <p>
    <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white" alt="Java">
    <img src="https://img.shields.io/badge/Swing-5382A1?style=for-the-badge&logo=java&logoColor=white" alt="Swing">
    <img src="https://img.shields.io/badge/SQLite-003B57?style=for-the-badge&logo=sqlite&logoColor=white" alt="SQLite">
    <img src="https://img.shields.io/badge/license-MIT-blue?style=for-the-badge" alt="MIT License">
  </p>
</div>

---

## Sobre

Repositório de estudos com aplicações desktop desenvolvidas em **Java Swing**, criadas com foco em aprendizado de interfaces gráficas, componentes visuais customizados e integração com banco de dados. Cada projeto demonstra conceitos diferentes: desde uma calculadora visual com tema colorido até um sistema completo de login e cadastro com persistência em SQLite.

---

## 📁 Projetos

| Projeto | Pasta | Descrição | Destaques |
|---------|-------|-----------|-----------|
| **Login System** | [`login-system/`](login-system/) | Sistema de Login & Cadastro com SQLite | Tema escuro, CardLayout, banco de dados embutido, validações |
| **Calculator** | [`calculator/`](calculator/) | Calculadora visual estilizada | Design rosa pastel, operações básicas, display customizado |
| **Dialogs** | [`dialogs/`](dialogs/) | Demonstração de diálogos Swing | Erro, aviso e confirmação, tema dark |

---

## ✨ Funcionalidades por Projeto

### Login System

#### Autenticação e Cadastro
- **Tela de Login**: Valida e-mail e senha consultando o banco SQLite
- **Tela de Cadastro**: Cadastra novos usuários com validação completa de campos
- **Validações**: e-mail com `@` e domínio, senha com mínimo 8 caracteres e 1 especial, nome com mínimo 3 caracteres
- **E-mail único**: impede cadastro duplicado no banco
- **Navegação**: troca entre login e cadastro via `CardLayout`

#### Design e UX
- **Tema escuro**: fundo preto (`#000000`), campos cinza escuro (`#1E1E1E`), texto branco
- **Ghost Text**: placeholders que aparecem e somem automaticamente nos campos
- **Foto circular**: avatar no topo da tela de login (com fallback desenhado se a imagem não existir)
- **Botões com hover**: fundo clareia ao passar o mouse
- **Limpeza automática**: campos são resetados ao trocar de tela

#### Banco de Dados
- **SQLite embutido**: sem servidor, arquivo `.db` gerado automaticamente na pasta do projeto
- **Tabela `users`**: `id`, `name`, `email` (único), `password`
- **DAO simples**: operações de inserir e buscar por e-mail com `PreparedStatement`
- **Consulta em tempo real**: acesso ao banco via `sqlite3` ou DB Browser

### Calculator

#### Operações
- **Básicas**: adição (`+`), subtração (`-`), multiplicação (`*`), divisão (`/`)
- **Funções auxiliares**: porcentagem (`%`), troca de sinal (`±`), backspace (`←`), clear (`C`)
- **Display customizado**: fonte grande (`Segoe UI`, 32px), alinhado à direita, cor suave
- **Formatação inteligente**: remove zeros desnecessários no resultado

#### Design System
- **Tema rosa pastel**: fundo `#FAEBF3`, display `#FFF5FA`
- **Botão `=`**: destaque em rosa forte `#FF69B4` com texto branco
- **Operadores**: rosa médio `#FF8CB4`
- **Funções**: rosa claro `#FFDCE6`
- **Números**: rosa padrão `#FFBED2`

### Dialogs

#### Tipos de Diálogo
- **Erro**: `JOptionPane.ERROR_MESSAGE` com mensagem de erro inesperado
- **Aviso**: `JOptionPane.WARNING_MESSAGE` com aviso importante
- **Confirmação**: `JOptionPane.YES_NO_OPTION` para salvar alterações

#### Design
- **Tema dark**: fundo `#1E1E1E`, botões `#323232`
- **Hover**: botões escurecem ao passar o mouse
- **Layout centralizado**: `GridBagLayout` com espaçamento uniforme

---

## 🎨 Design System

### Cores (Login System)

| Contexto | Cor | Descrição |
|----------|-----|-----------|
| Background | `#000000` | Preto puro |
| Campos de input | `#1E1E1E` | Cinza escuro |
| Borda dos campos | `#646464` | Cinza médio |
| Texto | `#FFFFFF` | Branco |
| Texto fantasma | `#C8C8C8` | Cinza claro |
| Botão hover | `#E6E6E6` | Cinza muito claro |

### Cores (Calculator)

| Contexto | Cor | Descrição |
|----------|-----|-----------|
| Background | `#FAEBF3` | Rosa pastel suave |
| Display | `#FFF5FA` | Rosa quase branco |
| Botão `=` | `#FF69B4` | Rosa forte |
| Operadores | `#FF8CB4` | Rosa médio |
| Funções (`C`, `←`, `%`, `±`) | `#FFDCE6` | Rosa claro |
| Números | `#FFBED2` | Rosa padrão |
| Texto | `#2D2D2D` | Cinza escuro |

### Cores (Dialogs)

| Contexto | Cor | Descrição |
|----------|-----|-----------|
| Background | `#1E1E1E` | Cinza escuro |
| Botões | `#323232` | Cinza médio |
| Botão hover | `#464646` | Cinza claro |
| Texto | `#FFFFFF` | Branco |
| Borda | `#646464` | Cinza médio |

### Tipografia

- **Login System**: `SansSerif` — bold 28px (títulos), plain 16px (campos), bold 16px (botões)
- **Calculator**: `Segoe UI` — plain 32px (display), bold 22px (botões)
- **Dialogs**: `SansSerif` — bold 12px (botões)

---

## 📁 Estrutura do Repositório

```
java-swing-apps/
├── assets/
│   └── usericon.png           # Ícone de usuário para o login system
├── calculator/
│   ├── Main.java              # Calculadora com design rosa pastel
│   └── README.md              # Documentação da calculadora
├── dialogs/
│   └── DialogApp.java         # Exemplos de JOptionPane com tema dark
├── login-system/
│   ├── MainApp.java           # JFrame principal com CardLayout
│   ├── LoginPanel.java        # Tela de login com foto e validações
│   ├── RegisterPanel.java     # Tela de cadastro com validações
│   ├── DatabaseConnection.java # Conexão com SQLite
│   ├── DatabaseSetup.java     # Criação automática da tabela
│   ├── UserDAO.java           # Operações no banco (inserir, buscar)
│   ├── User.java              # Modelo de usuário (POJO)
│   ├── UIUtils.java           # Estilização de componentes visuais
│   ├── ValidationUtils.java   # Regras de validação de formulário
│   ├── GhostText.java         # Placeholder/Hint text para campos
│   ├── run.sh                 # Script para compilar e executar
│   ├── sqlite-jdbc.jar        # Driver JDBC do SQLite
│   ├── slf4j-api.jar          # Dependência de logging
│   ├── slf4j-simple.jar       # Implementação de logging
│   └── README.md              # Documentação detalhada do login system
├── README.md                  # Este arquivo
└── .gitignore                 # Ignora .class, .db e .log
```

---

## 🚀 Como Usar

### Requisitos

- **Java JDK 8+** (JDK 17 ou superior recomendado)
- Para o `login-system`, não é necessário instalar banco de dados — o SQLite é embutido

### Login System

1. Navegue até a pasta do projeto:
   ```bash
   cd java-swing-apps/login-system
   ```

2. Usando o script (Linux/Mac):
   ```bash
   chmod +x run.sh
   ./run.sh
   ```

3. Ou compile e execute manualmente:
   ```bash
   # Linux/Mac
   javac -cp ".:sqlite-jdbc.jar:slf4j-api.jar:slf4j-simple.jar" *.java
   java -cp ".:sqlite-jdbc.jar:slf4j-api.jar:slf4j-simple.jar" MainApp
   
   # Windows
   javac -cp ".;sqlite-jdbc.jar;slf4j-api.jar;slf4j-simple.jar" *.java
   java -cp ".;sqlite-jdbc.jar;slf4j-api.jar;slf4j-simple.jar" MainApp
   ```

4. Consulte o banco de dados via terminal:
   ```bash
   cd login-system
   sqlite3 login_system.db
   
   # Dentro do sqlite3:
   SELECT * FROM users;
   .quit
   ```

> Veja a documentação completa em [`login-system/README.md`](login-system/).

### Calculator

1. Navegue até a pasta do projeto:
   ```bash
   cd java-swing-apps/calculator
   ```

2. Compile e execute:
   ```bash
   javac Main.java
   java Main
   ```

### Dialogs

1. Navegue até a pasta do projeto:
   ```bash
   cd java-swing-apps/dialogs
   ```

2. Compile e execute:
   ```bash
   javac DialogApp.java
   java DialogApp
   ```

---

## 🛠 Tecnologias Utilizadas

- **Java Swing** — GUI desktop multiplataforma
- **CardLayout** — navegação entre telas (login ↔ cadastro)
- **GridBagLayout** — layout responsivo e alinhado
- **SQLite + JDBC** — persistência local sem servidor
- **SLF4J** — logging simples
- **PreparedStatement** — queries SQL seguras contra injeção

---

## 📄 Licença

Projeto livre para estudo e modificação.

---

<div align="center">
  <p>Desenvolvido como projeto de estudo</p>
</div>
