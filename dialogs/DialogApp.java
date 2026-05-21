import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DialogApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Diálogos Rápidos");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 200);
            frame.setLayout(new GridBagLayout());
            frame.getContentPane().setBackground(new Color(30, 30, 30));

            Font buttonFont = new Font("SansSerif", Font.BOLD, 12);
            Color buttonBg = new Color(50, 50, 50);
            Color buttonFg = Color.WHITE;
            Color hoverBg = new Color(70, 70, 70);

            JButton errorButton = createStyledButton("Mostrar Erro", buttonFont, buttonBg, buttonFg);
            errorButton.addActionListener(e -> JOptionPane.showMessageDialog(frame,
                    "Ocorreu um erro inesperado.", "Erro", JOptionPane.ERROR_MESSAGE));

            JButton infoButton = createStyledButton("Mostrar Aviso", buttonFont, buttonBg, buttonFg);
            infoButton.addActionListener(e -> JOptionPane.showMessageDialog(frame,
                    "Este é um aviso importante.", "Aviso", JOptionPane.WARNING_MESSAGE));

            JButton questionButton = createStyledButton("Fazer Pergunta", buttonFont, buttonBg, buttonFg);
            questionButton.addActionListener(e -> {
                int response = JOptionPane.showConfirmDialog(frame,
                        "Deseja salvar as alterações?", "Salvar", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.YES_OPTION) {
                    System.out.println("Salvo com sucesso!");
                }
            });

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            gbc.gridx = 0;
            frame.add(errorButton, gbc);

            gbc.gridx = 1;
            frame.add(infoButton, gbc);

            gbc.gridx = 2;
            frame.add(questionButton, gbc);

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private static JButton createStyledButton(String text, Font font, Color bg, Color fg) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 100, 100), 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(70, 70, 70));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bg);
            }
        });

        return button;
    }
}
