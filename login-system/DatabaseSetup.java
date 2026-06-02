import java.sql.Connection;
import java.sql.Statement;

// cria a tabela de usuarios se ela nao existir ainda
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
                + "address TEXT"
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

        String produtosSql = "CREATE TABLE IF NOT EXISTS produtos ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nome TEXT NOT NULL,"
                + "descricao TEXT,"
                + "preco REAL,"
                + "quantidade INTEGER"
                + ");";

        String fornecedoresSql = "CREATE TABLE IF NOT EXISTS fornecedores ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nome TEXT NOT NULL,"
                + "cnpj TEXT,"
                + "telefone TEXT,"
                + "email TEXT,"
                + "endereco TEXT"
                + ");";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(usersSql);
            stmt.execute(clientsSql);
            stmt.execute(ordersSql);
            stmt.execute(produtosSql);
            stmt.execute(fornecedoresSql);
            System.out.println("Banco de dados inicializado com sucesso.");
        } catch (Exception e) {
            System.err.println("Erro ao inicializar banco: " + e.getMessage());
        }
    }
}
