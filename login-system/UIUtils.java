import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

// centraliza a criação dos componentes visuais pra não ficar repetindo código
public class UIUtils {
    // cores e fontes padronizadas pra manter consistência
    private static final Color BG_FIELD = new Color(30, 30, 30);
    private static final Color BORDER_COLOR = new Color(100, 100, 100);
    private static final Color WHITE = Color.WHITE;
    private static final Font FIELD_FONT = new Font("SansSerif", Font.PLAIN, 16);
    private static final Font BUTTON_FONT = new Font("SansSerif", Font.BOLD, 16);

    // cria um campo de texto com o visual escuro padrao
    public static JTextField createStyledTextField(int cols) {
        JTextField field = new JTextField(cols);
        field.setBackground(BG_FIELD);
        field.setForeground(WHITE);
        field.setCaretColor(WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        field.setFont(FIELD_FONT);
        return field;
    }

    // mesma coisa do de cima mas pra senha, esconde os caracteres
    public static JPasswordField createStyledPasswordField(int cols) {
        JPasswordField field = new JPasswordField(cols);
        field.setBackground(BG_FIELD);
        field.setForeground(WHITE);
        field.setCaretColor(WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        field.setFont(FIELD_FONT);
        return field;
    }

    // botao padrao com efeito de hover que muda de cor
    public static JButton createStyledButton(String text, Color bg, Color fg) {
        JButton button = new JButton(text);
        button.setBackground(bg);
        button.setForeground(fg);
        button.setOpaque(true);
        button.setFocusPainted(false);
        button.setFont(BUTTON_FONT);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(100, 50));
        button.setBorder(BorderFactory.createLineBorder(WHITE, 1));

        // quando o mouse entra clareia o fundo, quando sai volta ao normal
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(230, 230, 230));
            }

            public void mouseExited(MouseEvent evt) {
                button.setBackground(bg);
            }
        });

        return button;
    }

    // botao de link, sem fundo nem borda, estilo "esqueci a senha"
    public static JButton createLinkButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.PLAIN, 12));
        button.setForeground(Color.LIGHT_GRAY);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }
}
