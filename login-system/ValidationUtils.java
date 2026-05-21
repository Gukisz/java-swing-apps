import java.awt.Component;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

// funcoes auxiliares pra validar os campos do formulario
public class ValidationUtils {

    // ve se o campo ta vazio ou ainda ta mostrando o ghost text
    public static boolean isGhostOrEmpty(JTextField field, String ghostText) {
        String text = field.getText();
        return text == null || text.isEmpty() || text.equals(ghostText);
    }

    // mesma coisa pro campo de senha
    public static boolean isGhostOrEmpty(JPasswordField field, String ghostText) {
        String text = new String(field.getPassword());
        return text == null || text.isEmpty() || text.equals(ghostText);
    }

    // validacao basica de email, ve se tem @ e um dominio valido
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) return false;
        int atIndex = email.indexOf('@');
        if (atIndex < 1) return false;
        String domain = email.substring(atIndex + 1);
        return domain.contains(".") && !domain.startsWith(".") && !domain.endsWith(".");
    }

    // senha precisa ter pelo menos 8 caracteres e um caractere especial
    public static boolean isValidPassword(String password) {
        return password.length() >= 8 && password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");
    }

    // mostra um popup de erro se a condicao for verdadeira, retorna true/false
    public static boolean showErrorIf(Component parent, boolean condition, String message) {
        if (condition) {
            JOptionPane.showMessageDialog(parent, message, "Erro", JOptionPane.ERROR_MESSAGE);
            return true;
        }
        return false;
    }
}
