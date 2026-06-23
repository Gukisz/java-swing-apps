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

Repositório de estudos com aplicações desktop desenvolvidas em **Java Swing**, criadas com foco em aprendizado de interfaces gráficas, componentes visuais customizados e integração com banco de dados. Cada projeto demonstra conceitos diferentes: desde uma calculadora visual com tema colorido até um sistema completo de gestão de serviços com persistência em SQLite.

---

## 📁 Projetos

| Projeto | Pasta | Descrição | Destaques |
|---------|-------|-----------|-----------|
| **Service Management System** | [`login-system/`](login-system/) | Sistema de Gestão de Serviços com SQLite | Login, desktop MDI, CRUD de clientes e ordens de serviço, tema escuro |
| **Avaliação Java Swing** | [`avaljava01/`](avaljava01/) | Sistema de Cadastro de Alunos com SQLite | MenuBar, MDI, CRUD separado (cadastro vs edição), SQLite, tema escuro |
| **Calculator** | [`calculator/`](calculator/) | Calculadora visual estilizada | Design rosa pastel, operações básicas, display customizado |
| **Dialogs** | [`dialogs/`](dialogs/) | Demonstração de diálogos Swing | Erro, aviso e confirmação, tema dark |

---

## ✨ Funcionalidades por Projeto

### Avaliação Java Swing 01

#### MenuBar Completa
- **Arquivo**: Novo (abre tela de cadastro)
- **Editar**: Editar Aluno (abre tela de edição/exclusão)
- **Exibir**: Zoom, separador, Régua
- **Ajuda**: Sobre o Sistema (mensagem com versão)

#### Tela Cadastrar Aluno (apenas cadastro)
- **Formulário**: ID, Nome, Turma, E-mail
- **Botão Adicionar**: Salva no banco SQLite
- **Botão Limpar**: Limpa os campos
- **Sem tabela** e **sem botão de editar**

#### Tela Editar Alunos (apenas edição/exclusão)
- **Tabela**: Lista todos os alunos do banco SQLite
- **Botão Carregar** ou **duplo clique**: Carrega dados nos campos
- **Botão Salvar**: Atualiza o aluno no banco
- **Botão Excluir**: Remove com confirmação
- **Sem botão de adicionar**

#### Banco de Dados
- **SQLite embutido**: arquivo `avaljava01.db` gerado automaticamente
- Dados persistem entre sessões

#### Tema Escuro
- Fundo preto, campos cinzas, texto branco
- Dialogs customizados (`DarkDialog`)

### Service Management System

#### Autenticação e Cadastro
- **Tela de Login**: Valida e-mail e senha consultando o banco SQLite
- **Tela de Cadastro**: Cadastra novos usuários com validação completa de campos
- **Validações**: e-mail com `@` e domínio, senha com mínimo 8 caracteres e 1 especial, nome com mínimo 3 caracteres
- **E-mail único**: impede cadastro duplicado no banco
- **Navegação**: troca entre login e cadastro via `CardLayout`

#### Desktop Principal (MDI)
Após o login, o sistema abre um desktop maximizado com:
- **Barra de Menu**: Cadastro, Movimento, Relatório, Utilitário, Sobre, Ajuda
- **Toolbar**: Atalhos rápidos para Clientes, Serviços e Ordem de Serviço com ícones
- **JDesktopPane**: Área de trabalho para janelas internas (MDI)
- **Menu de Contexto (Popup)**: Acesso rápido no desktop com botão direito

#### Cadastro de Clientes
- **Formulário completo**: Nome, Telefone, E-mail, Endereço
- **Tabela escura** listando todos os clientes cadastrados
- **Busca em tempo real** por nome
- **CRUD completo**: Novo, Salvar, Atualizar, Excluir (com confirmação)

#### Ordem de Serviço (OS)
- **Vinculação com cliente** via combo box
- **Campos**: Equipamento, Defeito, Serviço, Técnico, Valor (R$), Status
- **Status**: Aberta, Em andamento, Finalizada, Cancelada
- **Tabela escura** com todas as ordens de serviço
- **CRUD completo**: Novo, Salvar, Atualizar, Excluir

#### Utilitários
- **Calculadora**: Abre a calculadora do sistema operacional
- **Bloco de Notas**: Abre o editor de texto do sistema operacional

#### Design e UX
- **Tema escuro**: fundo preto (`#000000`), campos cinza escuro (`#1E1E1E`), texto branco
- **Dialogs Customizados**: Popups modernos escuros (`DarkDialog`) substituindo o `JOptionPane` padrão
- **Ghost Text**: placeholders que aparecem e somem automaticamente nos campos
- **Foto circular**: avatar no topo da tela de login (com fallback desenhado se a imagem não existir)
- **Botões com hover**: fundo clareia ao passar o mouse
- **Limpeza automática**: campos são resetados ao trocar de tela

#### Banco de Dados
- **SQLite embutido**: sem servidor, arquivo `.db` gerado automaticamente na pasta do projeto
- **Tabelas**: `users`, `clients`, `service_orders` (com chave estrangeira)
- **DAOs**: operações de CRUD com `PreparedStatement`
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
│   ├── Icones/                # Ícones para o sistema de gestão
│   └── usericon.png           # Ícone de usuário para o login
├── avaljava01/
│   ├── lib/
│   │   ├── sqlite-jdbc.jar          # Driver JDBC do SQLite
│   │   ├── slf4j-api.jar            # Dependência de logging
│   │   └── slf4j-simple.jar         # Implementação de logging
│   ├── src/
│   │   ├── controller/
│   │   │   └── TelaPrincipal.java   # JFrame principal com JDesktopPane MDI
│   │   ├── dao/
│   │   │   ├── DatabaseConnection.java  # Conexão com SQLite
│   │   │   └── AlunoDAO.java        # Operações CRUD no banco
│   │   ├── model/
│   │   │   └── Aluno.java           # Classe modelo (POJO)
│   │   └── view/
│   │       ├── NovoAlunoFrame.java  # Tela de cadastro (apenas adicionar)
│   │       ├── EditarAlunoFrame.java # Tela de edição (editar/excluir)
│   │       └── DarkDialog.java      # Dialogs customizados
│   ├── run.sh                       # Script para compilar e executar
│   └── README.md
├── calculator/
│   ├── Main.java              # Calculadora com design rosa pastel
│   └── README.md              # Documentação da calculadora
├── dialogs/
│   └── DialogApp.java         # Exemplos de JOptionPane com tema dark
├── login-system/
│   ├── lib/
│   │   ├── sqlite-jdbc.jar    # Driver JDBC do SQLite
│   │   ├── slf4j-api.jar     # Dependência de logging
│   │   └── slf4j-simple.jar  # Implementação de logging
│   ├── src/
│   │   ├── controller/
│   │   │   ├── MainApp.java            # JFrame principal com CardLayout
│   │   │   ├── DatabaseConnection.java # Conexão com SQLite
│   │   │   ├── DatabaseSetup.java      # Criação automática das tabelas
│   │   │   └── ValidationUtils.java    # Regras de validação
│   │   ├── model/
│   │   │   ├── User.java          # Modelo de usuário
│   │   │   ├── Client.java        # Modelo de cliente
│   │   │   ├── Product.java       # Modelo de produto
│   │   │   ├── Service.java       # Modelo de serviço
│   │   │   ├── ServiceOrder.java  # Modelo de ordem de serviço
│   │   │   └── Supplier.java      # Modelo de fornecedor
│   │   ├── dao/
│   │   │   ├── UserDAO.java         # Operações no banco de usuários
│   │   │   ├── ClientDAO.java       # Operações no banco de clientes
│   │   │   ├── ProductDAO.java      # Operações no banco de produtos
│   │   │   ├── ServiceDAO.java      # Operações no banco de serviços
│   │   │   ├── ServiceOrderDAO.java # Operações no banco de OS
│   │   │   └── SupplierDAO.java     # Operações no banco de fornecedores
│   │   └── view/
│   │       ├── LoginPanel.java         # Tela de login
│   │       ├── RegisterPanel.java      # Tela de cadastro
│   │       ├── ServiceManagementFrame.java        # Desktop principal MDI
│   │       ├── ClientInternalFrame.java           # Cadastro de clientes
│   │       ├── ProductInternalFrame.java          # Cadastro de produtos
│   │       ├── ServiceInternalFrame.java          # Cadastro de serviços
│   │       ├── ServiceOrderInternalFrame.java     # Cadastro de OS
│   │       ├── ConsultaClientesInternalFrame.java  # Consulta de clientes
│   │       ├── ConsultaProdutosInternalFrame.java  # Consulta de produtos
│   │       ├── ConsultaServicosInternalFrame.java  # Consulta de serviços
│   │       ├── RelatorioClientesInternalFrame.java # Relatório de clientes
│   │       ├── RelatorioProdutosInternalFrame.java # Relatório de produtos
│   │       ├── RelatorioServicosInternalFrame.java # Relatório de serviços
│   │       ├── DarkDialog.java       # Dialogs customizados
│   │       ├── IconUtils.java       # Carregador de ícones
│   │       ├── UIUtils.java         # Estilização de componentes
│   │       └── GhostText.java       # Placeholder/Hint text
│   ├── login_system.db        # Banco de dados gerado automaticamente
│   ├── run.sh                 # Script para compilar e executar
│   └── README.md              # Documentação detalhada do sistema
├── README.md                  # Este arquivo
└── .gitignore                 # Ignora .class, .db e .log
```

---

## 🚀 Como Usar

### Requisitos

- **Java JDK 8+** (JDK 17 ou superior recomendado)
- Para o `login-system`, não é necessário instalar banco de dados — o SQLite é embutido

### Service Management System

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
   javac -cp "lib/*:src/controller:src/model:src/dao:src/view" src/controller/*.java src/model/*.java src/dao/*.java src/view/*.java
   java -Dsun.java2d.opengl=false -Dsun.java2d.xrender=false -cp ".:lib/*:src" controller.MainApp
   
   # Windows
   javac -cp "lib/*;src/controller;src/model;src/dao;src/view" src/controller/*.java src/model/*.java src/dao/*.java src/view/*.java
   java -Dsun.java2d.opengl=false -Dsun.java2d.xrender=false -cp ".;lib/*;src" controller.MainApp
   ```

4. Consulte o banco de dados via terminal:
   ```bash
   cd login-system
   sqlite3 login_system.db
   
   # Dentro do sqlite3:
   SELECT * FROM users;
   SELECT * FROM clients;
   SELECT * FROM service_orders;
   .quit
   ```

> Veja a documentação completa em [`login-system/README.md`](login-system/).

### Avaliação Java Swing 01

1. Navegue até a pasta do projeto:
   ```bash
   cd java-swing-apps/avaljava01
   ```

2. Usando o script (Linux/Mac):
   ```bash
   chmod +x run.sh
   ./run.sh
   ```

3. Ou compile e execute manualmente:
   ```bash
   # Linux/Mac (com flags de compatibilidade Wayland)
   javac -cp "lib/*:src/controller:src/model:src/view:src/dao" src/controller/*.java src/model/*.java src/view/*.java src/dao/*.java
   export _JAVA_AWT_WM_NONREPARENTING=1
   export NO_AT_BRIDGE=1
   java --enable-native-access=ALL-UNNAMED -Dsun.java2d.opengl=false -Dsun.java2d.xrender=false -cp "lib/*:src" controller.TelaPrincipal

   # Windows
   javac -cp "lib\*;src\controller;src\model;src\view;src\dao" src\controller\*.java src\model\*.java src\view\*.java src\dao\*.java
   java --enable-native-access=ALL-UNNAMED -cp "lib\*;src" controller.TelaPrincipal
   ```

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
- **JDesktopPane + JInternalFrame** — interface MDI com janelas internas
- **JMenuBar + JToolBar** — menu e barra de ferramentas
- **GridBagLayout** — layout responsivo e alinhado
- **SQLite + JDBC** — persistência local sem servidor
- **SLF4J** — logging simples
- **PreparedStatement** — queries SQL seguras contra injeção
- **Foreign Keys** — relacionamento entre tabelas (clients ↔ service_orders)

---

## 📄 Licença

Projeto livre para estudo e modificação.

---

<div align="center">
  <p>Desenvolvido como projeto de estudo</p>
</div>
