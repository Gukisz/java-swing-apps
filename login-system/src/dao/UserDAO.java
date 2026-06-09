package dao;
import controller.DatabaseConnection;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// dao simples pra fazer as operacoes no banco de usuarios
public class UserDAO {

    // cadastra um usuario novo, retorna true se deu certo
    public boolean insert(User user) {
        String sql = "INSERT INTO users(name, email, password) VALUES(?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            // email duplicado ou outro erro
            System.err.println("Erro ao cadastrar usuario: " + e.getMessage());
            return false;
        }
    }

    // busca um usuario pelo email
    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    return user;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuario: " + e.getMessage());
        }
        return null;
    }

    // verifica se o email ja ta cadastrado
    public boolean emailExists(String email) {
        return findByEmail(email) != null;
    }

    // atualiza a senha de um usuario pelo email
    public boolean updatePassword(String email, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newPassword);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar senha: " + e.getMessage());
            return false;
        }
    }
}
