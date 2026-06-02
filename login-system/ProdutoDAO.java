import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    public boolean insert(Produto p) {
        String sql = "INSERT INTO produtos(nome, descricao, preco, quantidade) VALUES(?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, p.getNome());
            pstmt.setString(2, p.getDescricao());
            pstmt.setDouble(3, p.getPreco());
            pstmt.setInt(4, p.getQuantidade());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar produto: " + e.getMessage());
            return false;
        }
    }

    public List<Produto> findAll() {
        List<Produto> list = new ArrayList<>();
        String sql = "SELECT * FROM produtos ORDER BY nome";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setDescricao(rs.getString("descricao"));
                p.setPreco(rs.getDouble("preco"));
                p.setQuantidade(rs.getInt("quantidade"));
                list.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar produtos: " + e.getMessage());
        }
        return list;
    }

    public boolean update(Produto p) {
        String sql = "UPDATE produtos SET nome=?, descricao=?, preco=?, quantidade=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, p.getNome());
            pstmt.setString(2, p.getDescricao());
            pstmt.setDouble(3, p.getPreco());
            pstmt.setInt(4, p.getQuantidade());
            pstmt.setInt(5, p.getId());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar produto: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM produtos WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir produto: " + e.getMessage());
            return false;
        }
    }
}
