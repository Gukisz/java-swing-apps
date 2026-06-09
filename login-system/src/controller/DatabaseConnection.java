package controller;
import view.*;
import model.*;
import dao.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// conexao com o banco sqlite, bem simples e facil de mexer
public class DatabaseConnection {
    private static final String URL;

    static {
        // usa o diretório de trabalho atual (onde o programa foi executado)
        // garante que o banco fique sempre na mesma pasta do projeto
        String dbPath = System.getProperty("user.dir") + java.io.File.separator + "login_system.db";
        URL = "jdbc:sqlite:" + dbPath;
    }

    // carrega o driver uma vez so
    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver SQLite nao encontrado. Baixe o sqlite-jdbc.jar");
        }
    }

    // devolve uma conexao nova toda vez que chamar
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
