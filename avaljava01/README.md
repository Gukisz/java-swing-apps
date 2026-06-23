# Avaliação Java Swing 01

Sistema de Cadastro de Alunos desenvolvido em **Java Swing** com tema escuro e persistência em **SQLite**.

## Estrutura do Projeto

```
avaljava01/
├── lib/
│   ├── sqlite-jdbc.jar          # Driver JDBC do SQLite
│   ├── slf4j-api.jar            # Dependência de logging
│   └── slf4j-simple.jar         # Implementação de logging
├── src/
│   ├── controller/
│   │   └── TelaPrincipal.java   # JFrame principal com JDesktopPane MDI
│   ├── dao/
│   │   ├── DatabaseConnection.java  # Conexão com SQLite
│   │   └── AlunoDAO.java        # Operações CRUD no banco
│   ├── model/
│   │   └── Aluno.java           # Classe modelo (POJO)
│   └── view/
│       ├── NovoAlunoFrame.java  # Tela de cadastro (apenas adicionar)
│       ├── EditarAlunoFrame.java # Tela de edição (editar/excluir)
│       └── DarkDialog.java      # Dialogs customizados com tema escuro
├── run.sh                       # Script de compilação/execução (Linux)
└── README.md
```

## Funcionalidades

### Tela de Cadastro (Arquivo > Novo)
- **Formulário**: ID, Nome, Turma, E-mail
- **Botão Adicionar**: Cadastra aluno no banco SQLite
- **Botão Limpar**: Limpa os campos do formulário
- **Sem tabela** e **sem botão de editar** — apenas cadastro

### Tela de Edição (Editar > Editar Aluno)
- **Tabela**: Lista todos os alunos cadastrados no banco
- **Duplo clique** ou botão **Carregar**: Carrega dados do aluno selecionado nos campos
- **Botão Salvar**: Atualiza o aluno no banco
- **Botão Excluir**: Remove o aluno com confirmação
- **Botão Cancelar**: Limpa os campos e sai do modo de edição
- **Sem botão de adicionar** — apenas edição e exclusão

### Persistência
- **SQLite embutido**: arquivo `avaljava01.db` gerado automaticamente
- Os dados não somem ao fechar o programa

### Tema Escuro
- Fundo preto, campos cinzas, texto branco
- Dialogs customizados (`DarkDialog`)

## Como Executar

### Linux / Mac

Usando o script (recomendado):

```bash
cd avaljava01
./run.sh
```

Ou manualmente:

```bash
cd avaljava01

# Compilar
javac -cp "lib/*:src/controller:src/model:src/view:src/dao" src/controller/*.java src/model/*.java src/view/*.java src/dao/*.java

# Executar (com flags de compatibilidade Wayland)
export _JAVA_AWT_WM_NONREPARENTING=1
export NO_AT_BRIDGE=1
java --enable-native-access=ALL-UNNAMED -Dsun.java2d.opengl=false -Dsun.java2d.xrender=false -cp "lib/*:src" controller.TelaPrincipal
```

### Windows

```cmd
cd avaljava01

:: Compilar
javac -cp "lib\*;src\controller;src\model;src\view;src\dao" src\controller\*.java src\model\*.java src\view\*.java src\dao\*.java

:: Executar
java --enable-native-access=ALL-UNNAMED -cp "lib\*;src" controller.TelaPrincipal
```

## Tecnologias

- Java Swing
- SQLite + JDBC
- JDesktopPane + JInternalFrame (MDI)
- JMenuBar, JTable, JFrame
- Tema escuro customizado
