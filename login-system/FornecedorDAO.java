import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FornecedorDAO {

    public boolean insert(Fornecedor f) {
        String sql = "INSERT INTO fornecedores(nome, cnpj, telefone, email, endereco) VALUES(?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, f.getNome());
            pstmt.setString(2, f.getCnpj());
            pstmt.setString(3, f.getTelefone());
            pstmt.setString(4, f.getEmail());
            pstmt.setString(5, f.getEndereco());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar fornecedor: " + e.getMessage());
            return false;
        }
    }

    public List<Fornecedor> findAll() {
        List<Fornecedor> list = new ArrayList<>();
        String sql = "SELECT * FROM fornecedores ORDER BY nome";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Fornecedor f = new Fornecedor();
                f.setId(rs.getInt("id"));
                f.setNome(rs.getString("nome"));
                f.setCnpj(rs.getString("cnpj"));
                f.setTelefone(rs.getString("telefone"));
                f.setEmail(rs.getString("email"));
                f.setEndereco(rs.getString("endereco"));
                list.add(f);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar fornecedores: " + e.getMessage());
        }
        return list;
    }

    public boolean update(Fornecedor f) {
        String sql = "UPDATE fornecedores SET nome=?, cnpj=?, telefone=?, email=?, endereco=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, f.getNome());
            pstmt.setString(2, f.getCnpj());
            pstmt.setString(3, f.getTelefone());
            pstmt.setString(4, f.getEmail());
            pstmt.setString(5, f.getEndereco());
            pstmt.setInt(6, f.getId());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar fornecedor: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM fornecedores WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir fornecedor: " + e.getMessage());
            return false;
        }
    }
}
