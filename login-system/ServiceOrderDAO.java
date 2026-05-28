import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceOrderDAO {

    public boolean insert(ServiceOrder so) {
        String sql = "INSERT INTO service_orders(client_id, equipment, defect, service, technician, value, status) VALUES(?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, so.getClientId());
            pstmt.setString(2, so.getEquipment());
            pstmt.setString(3, so.getDefect());
            pstmt.setString(4, so.getService());
            pstmt.setString(5, so.getTechnician());
            pstmt.setDouble(6, so.getValue());
            pstmt.setString(7, so.getStatus());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar OS: " + e.getMessage());
            return false;
        }
    }

    public List<ServiceOrder> findAll() {
        List<ServiceOrder> list = new ArrayList<>();
        String sql = "SELECT so.*, c.name as client_name FROM service_orders so " +
                     "JOIN clients c ON so.client_id = c.id ORDER BY so.id DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar OS: " + e.getMessage());
        }
        return list;
    }

    public boolean update(ServiceOrder so) {
        String sql = "UPDATE service_orders SET client_id=?, equipment=?, defect=?, service=?, technician=?, value=?, status=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, so.getClientId());
            pstmt.setString(2, so.getEquipment());
            pstmt.setString(3, so.getDefect());
            pstmt.setString(4, so.getService());
            pstmt.setString(5, so.getTechnician());
            pstmt.setDouble(6, so.getValue());
            pstmt.setString(7, so.getStatus());
            pstmt.setInt(8, so.getId());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar OS: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM service_orders WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir OS: " + e.getMessage());
            return false;
        }
    }

    private ServiceOrder mapResultSet(ResultSet rs) throws SQLException {
        ServiceOrder so = new ServiceOrder();
        so.setId(rs.getInt("id"));
        so.setClientId(rs.getInt("client_id"));
        so.setClientName(rs.getString("client_name"));
        so.setEquipment(rs.getString("equipment"));
        so.setDefect(rs.getString("defect"));
        so.setService(rs.getString("service"));
        so.setTechnician(rs.getString("technician"));
        so.setValue(rs.getDouble("value"));
        so.setStatus(rs.getString("status"));
        return so;
    }
}
