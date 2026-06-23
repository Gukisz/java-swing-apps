# Avaliação Java Swing 01

Sistema de Cadastro de Alunos desenvolvido em **Java Swing** com tema escuro.

## Estrutura do Projeto

```
avaljava01/
├── src/
│   ├── controller/
│   │   └── TelaPrincipal.java    # JFrame principal com MenuBar
│   ├── model/
│   │   └── Aluno.java            # Classe modelo (POJO)
│   └── view/
│       ├── NovoAlunoFrame.java   # Tela de cadastro com formulário e JTable
│       └── DarkDialog.java       # Dialogs customizados com tema escuro
├── run.sh                        # Script de compilação/execução (Linux)
└── README.md
```

## Funcionalidades

- **MenuBar completa**: Arquivo, Editar, Exibir, Ajuda
- **Tela Novo Aluno**: Formulário com ID, Nome, Turma e E-mail
- **JTable**: Exibe alunos cadastrados com ordenação automática por nome
- **Botão Adicionar**: Cadastra aluno na tabela e limpa os campos
- **Botão Excluir**: Remove aluno selecionado com confirmação
- **Tema escuro**: Fundo preto, campos cinzas, texto branco

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
javac -cp "src/controller:src/model:src/view" src/controller/*.java src/model/*.java src/view/*.java

# Executar (com flags de compatibilidade Wayland)
export _JAVA_AWT_WM_NONREPARENTING=1
export NO_AT_BRIDGE=1
java -Dsun.java2d.opengl=false -Dsun.java2d.xrender=false -cp "src" controller.TelaPrincipal
```

### Windows

```cmd
cd avaljava01

:: Compilar
javac -cp "src\controller;src\model;src\view" src\controller\*.java src\model\*.java src\view\*.java

:: Executar
java -cp "src" controller.TelaPrincipal
```

## Tecnologias

- Java Swing
- Programação Orientada a Objetos
- JMenuBar, JTable, JFrame
- Tema escuro customizado
