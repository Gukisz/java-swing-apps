package dao;
import controller.DatabaseConnection;
import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO {

    public boolean insert(Supplier s) {
        String sql = "INSERT INTO suppliers(name, phone, email, address, cnpj) VALUES(?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, s.getName());
            pstmt.setString(2, s.getPhone());
            pstmt.setString(3, s.getEmail());
            pstmt.setString(4, s.getAddress());
            pstmt.setString(5, s.getCnpj());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar fornecedor: " + e.getMessage());
            return false;
        }
    }

    public List<Supplier> findAll() {
        List<Supplier> list = new ArrayList<>();
        String sql = "SELECT * FROM suppliers ORDER BY name";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Supplier s = new Supplier();
                s.setId(rs.getInt("id"));
                s.setName(rs.getString("name"));
                s.setPhone(rs.getString("phone"));
                s.setEmail(rs.getString("email"));
                s.setAddress(rs.getString("address"));
                s.setCnpj(rs.getString("cnpj"));
                list.add(s);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar fornecedores: " + e.getMessage());
        }
        return list;
    }

    public List<Supplier> searchByName(String query) {
        List<Supplier> list = new ArrayList<>();
        String sql = "SELECT * FROM suppliers WHERE name LIKE ? ORDER BY name";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + query + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Supplier s = new Supplier();
                s.setId(rs.getInt("id"));
                s.setName(rs.getString("name"));
                s.setPhone(rs.getString("phone"));
                s.setEmail(rs.getString("email"));
                s.setAddress(rs.getString("address"));
                s.setCnpj(rs.getString("cnpj"));
                list.add(s);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar fornecedores: " + e.getMessage());
        }
        return list;
    }
}
