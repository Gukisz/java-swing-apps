import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;

// cria e migra as tabelas automaticamente
public class DatabaseSetup {

    public static void main(String[] args) {
        init();
    }

    public static void init() {
        String usersSql = "CREATE TABLE IF NOT EXISTS users ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name TEXT NOT NULL,"
                + "email TEXT NOT NULL UNIQUE,"
                + "password TEXT NOT NULL"
                + ");";

        String clientsSql = "CREATE TABLE IF NOT EXISTS clients ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name TEXT NOT NULL,"
                + "phone TEXT,"
                + "email TEXT,"
                + "cpf TEXT UNIQUE"
                + ");";

        String ordersSql = "CREATE TABLE IF NOT EXISTS service_orders ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "client_id INTEGER NOT NULL,"
                + "equipment TEXT NOT NULL,"
                + "defect TEXT,"
                + "service TEXT,"
                + "technician TEXT,"
                + "value REAL,"
                + "status TEXT NOT NULL DEFAULT 'Aberta',"
                + "FOREIGN KEY(client_id) REFERENCES clients(id)"
                + ");";

        String productsSql = "CREATE TABLE IF NOT EXISTS products ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name TEXT NOT NULL,"
                + "description TEXT,"
                + "price REAL,"
                + "stock INTEGER,"
                + "supplier TEXT"
                + ");";

        String suppliersSql = "CREATE TABLE IF NOT EXISTS suppliers ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name TEXT NOT NULL,"
                + "phone TEXT,"
                + "email TEXT,"
                + "address TEXT,"
                + "cnpj TEXT"
                + ");";

        String servicesSql = "CREATE TABLE IF NOT EXISTS services ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name TEXT NOT NULL,"
                + "description TEXT,"
                + "price REAL"
                + ");";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(usersSql);
            stmt.execute(clientsSql);
            stmt.execute(ordersSql);
            stmt.execute(productsSql);
            stmt.execute(suppliersSql);
            stmt.execute(servicesSql);

            // migracao: adiciona cpf se a tabela antiga existir sem ele
            migrateClientsTable(conn, stmt);

            System.out.println("Banco de dados inicializado com sucesso.");
        } catch (Exception e) {
            System.err.println("Erro ao inicializar banco: " + e.getMessage());
        }
    }

    private static void migrateClientsTable(Connection conn, Statement stmt) {
        try {
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet rs = meta.getColumns(null, null, "clients", "cpf");
            if (!rs.next()) {
                // coluna cpf nao existe, adiciona
                stmt.execute("ALTER TABLE clients ADD COLUMN cpf TEXT UNIQUE");
                System.out.println("Migracao: coluna cpf adicionada a tabela clients.");
            }
            rs.close();
        } catch (Exception e) {
            System.err.println("Erro na migracao: " + e.getMessage());
        }
    }
}
