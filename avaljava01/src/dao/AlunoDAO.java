package dao;

import model.Aluno;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// DAO - operações no banco
public class AlunoDAO {

    public void insert(Aluno aluno) {
        String sql = "INSERT INTO alunos (id, nome, turma, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, aluno.getId());
            pstmt.setString(2, aluno.getNome());
            pstmt.setString(3, aluno.getTurma());
            pstmt.setString(4, aluno.getEmail());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Aluno aluno) {
        String sql = "UPDATE alunos SET nome = ?, turma = ?, email = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, aluno.getNome());
            pstmt.setString(2, aluno.getTurma());
            pstmt.setString(3, aluno.getEmail());
            pstmt.setInt(4, aluno.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM alunos WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // select all ordenado por nome
    public List<Aluno> findAll() {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT * FROM alunos ORDER BY nome COLLATE NOCASE";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Aluno a = new Aluno(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("turma"),
                        rs.getString("email")
                );
                alunos.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alunos;
    }

    // check se ID existe
    public boolean idExists(int id) {
        String sql = "SELECT 1 FROM alunos WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
