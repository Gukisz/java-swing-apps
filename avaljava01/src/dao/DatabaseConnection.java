package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

// sqlite conn + cria tabela se precisar
public class DatabaseConnection {
    private static final String DB_URL = "jdbc:sqlite:avaljava01.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void init() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS alunos (" +
                    "id INTEGER PRIMARY KEY, " +
                    "nome TEXT NOT NULL, " +
                    "turma TEXT NOT NULL, " +
                    "email TEXT NOT NULL" +
                    ")";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
