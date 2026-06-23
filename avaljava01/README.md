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

```bash
cd avaljava01

# Compilar
javac -cp "src/controller:src/model:src/view" src/controller/*.java src/model/*.java src/view/*.java

# Executar
java -cp ".:src/controller:src/model:src/view" controller.TelaPrincipal
```

## Tecnologias

- Java Swing
- Programação Orientada a Objetos
- JMenuBar, JTable, JFrame
- Tema escuro customizado
