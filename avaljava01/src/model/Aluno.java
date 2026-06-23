package model;

// model do aluno
public class Aluno {
    private int id;
    private String nome;
    private String turma;
    private String email;

    public Aluno() {}

    public Aluno(int id, String nome, String turma, String email) {
        this.id = id;
        this.nome = nome;
        this.turma = turma;
        this.email = email;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTurma() { return turma; }
    public void setTurma(String turma) { this.turma = turma; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
