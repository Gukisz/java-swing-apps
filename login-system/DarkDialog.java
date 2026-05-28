import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

// dialogs customizados com tema escuro, substituindo o JOptionPane padrao
public class DarkDialog {

    private static final Color BG = new Color(30, 30, 30);
    private static final Color FG = Color.WHITE;
    private static final Color ACCENT = new Color(100, 100, 100);
    private static final Font TITLE_FONT = new Font("SansSerif", Font.BOLD, 16);
    private static final Font MSG_FONT = new Font("SansSerif", Font.PLAIN, 14);
    private static final Font BTN_FONT = new Font("SansSerif", Font.BOLD, 13);

    public static void showInfo(Component parent, String title, String message) {
        showDialog(parent, title, message, "info");
    }

    public static void showError(Component parent, String title, String message) {
        showDialog(parent, title, message, "error");
    }

    public static void showWarning(Component parent, String title, String message) {
        showDialog(parent, title, message, "warn");
    }

    public static boolean showConfirm(Component parent, String title, String message) {
        return showConfirmDialog(parent, title, message);
    }

    private static void showDialog(Component parent, String title, String message, String type) {
        Window window = parent != null ? SwingUtilities.getWindowAncestor(parent) : null;
        JDialog dialog = new JDialog(window, title, Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setUndecorated(false);
        dialog.getRootPane().setBorder(BorderFactory.createLineBorder(ACCENT, 2));
        dialog.setBackground(BG);

        JPanel content = new JPanel(new BorderLayout(10, 10));
        content.setBackground(BG);
        content.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        // icone
        JLabel iconLabel = new JLabel(getIcon(type), SwingConstants.CENTER);
        content.add(iconLabel, BorderLayout.NORTH);

        // mensagem
        JLabel msgLabel = new JLabel("<html><center>" + escapeHtml(message) + "</center></html>", SwingConstants.CENTER);
        msgLabel.setFont(MSG_FONT);
        msgLabel.setForeground(FG);
        content.add(msgLabel, BorderLayout.CENTER);

        // botao ok
        JButton okBtn = createButton("OK", new Color(60, 60, 60));
        okBtn.addActionListener(e -> dialog.dispose());
        dialog.getRootPane().setDefaultButton(okBtn);

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(BG);
        btnPanel.add(okBtn);
        content.add(btnPanel, BorderLayout.SOUTH);

        dialog.setContentPane(content);
        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }

    private static boolean showConfirmDialog(Component parent, String title, String message) {
        Window window = parent != null ? SwingUtilities.getWindowAncestor(parent) : null;
        JDialog dialog = new JDialog(window, title, Dialog.ModalityType.APPLICATION_MODAL);
        dialog.getRootPane().setBorder(BorderFactory.createLineBorder(ACCENT, 2));
        dialog.setBackground(BG);

        final boolean[] result = {false};

        JPanel content = new JPanel(new BorderLayout(10, 10));
        content.setBackground(BG);
        content.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel iconLabel = new JLabel(getIcon("question"), SwingConstants.CENTER);
        content.add(iconLabel, BorderLayout.NORTH);

        JLabel msgLabel = new JLabel("<html><center>" + escapeHtml(message) + "</center></html>", SwingConstants.CENTER);
        msgLabel.setFont(MSG_FONT);
        msgLabel.setForeground(FG);
        content.add(msgLabel, BorderLayout.CENTER);

        JButton yesBtn = createButton("Sim", new Color(60, 60, 60));
        JButton noBtn = createButton("Não", new Color(60, 60, 60));

        yesBtn.addActionListener(e -> {
            result[0] = true;
            dialog.dispose();
        });
        noBtn.addActionListener(e -> {
            result[0] = false;
            dialog.dispose();
        });
        dialog.getRootPane().setDefaultButton(yesBtn);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        btnPanel.setBackground(BG);
        btnPanel.add(yesBtn);
        btnPanel.add(noBtn);
        content.add(btnPanel, BorderLayout.SOUTH);

        dialog.setContentPane(content);
        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
        return result[0];
    }

    private static JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(BTN_FONT);
        btn.setBackground(bg);
        btn.setForeground(FG);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT),
                BorderFactory.createEmptyBorder(8, 20, 8, 20)));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(80, 80, 80));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(bg);
            }
        });
        return btn;
    }

    private static Icon getIcon(String type) {
        int size = 48;
        ImageIcon icon = null;
        switch (type) {
            case "info":
                icon = IconUtils.loadIcon("protecao(1).png", size, size);
                break;
            case "error":
                icon = IconUtils.loadIcon("erro.png", size, size);
                break;
            case "warn":
                icon = IconUtils.loadIcon("alerta-de-virus.png", size, size);
                break;
            case "question":
                icon = IconUtils.loadIcon("lupa.png", size, size);
                break;
        }
        return icon != null ? icon : UIManager.getIcon("OptionPane.informationIcon");
    }

    private static String escapeHtml(String text) {
        return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
