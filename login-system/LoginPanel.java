import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class LoginPanel extends JPanel {
    private MainApp mainApp;
    private UserDAO userDAO;
    private JTextField emailField;
    private JPasswordField passwordField;
    private GhostText emailGhost, passwordGhost;

    public LoginPanel(MainApp mainApp) {
        this.mainApp = mainApp;
        this.userDAO = new UserDAO();
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        // foto do usuario no topo, centralizada
        JPanel photoPanel = createPhotoPanel();
        add(photoPanel, BorderLayout.NORTH);

        // coluna do meio centralizada com espacadores dos lados
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.BLACK);
        add(formPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 0;

        // coluna 0 e 2 sao espacadores, coluna 1 é o conteudo centralizado
        gbc.gridx = 0;
        gbc.weightx = 0.3;
        formPanel.add(Box.createHorizontalGlue(), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(8, 0, 8, 0);

        // titulo da tela
        JLabel titleLabel = new JLabel("Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        formPanel.add(titleLabel, gbc);

        gbc.gridy = 1;
        emailField = UIUtils.createStyledTextField(20);
        emailGhost = new GhostText(emailField, "E-mail");
        formPanel.add(emailField, gbc);

        gbc.gridy = 2;
        passwordField = UIUtils.createStyledPasswordField(20);
        passwordGhost = new GhostText(passwordField, "Senha");
        formPanel.add(passwordField, gbc);

        gbc.gridy = 3;
        JButton loginButton = UIUtils.createStyledButton("ENTRAR", Color.WHITE, Color.BLACK);
        formPanel.add(loginButton, gbc);

        gbc.gridy = 4;
        JButton signupButton = UIUtils.createStyledButton("CRIAR CONTA", Color.WHITE, Color.BLACK);
        formPanel.add(signupButton, gbc);

        gbc.gridy = 5;
        gbc.insets = new Insets(4, 0, 8, 0);
        JButton forgotBtn = UIUtils.createLinkButton("Esqueci a senha");
        formPanel.add(forgotBtn, gbc);

        // coluna espacadora da direita
        gbc.gridx = 2;
        gbc.weightx = 0.3;
        gbc.insets = new Insets(0, 0, 0, 0);
        formPanel.add(Box.createHorizontalGlue(), gbc);

        // acoes dos botoes
        loginButton.addActionListener(e -> handleLogin());
        signupButton.addActionListener(e -> mainApp.showRegister());
        forgotBtn.addActionListener(e -> showForgotPasswordDialog());

        // quando a tela aparece, limpa os campos
        addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent e) {
                clearFields();
                SwingUtilities.invokeLater(() -> LoginPanel.this.requestFocusInWindow());
            }
        });
    }

    // carrega a foto uma vez e desenha num painel do tamanho dela
    private JPanel createPhotoPanel() {
        BufferedImage photo = null;
        try {
            File imgFile = new File("assets/usericon.png");
            if (!imgFile.exists()) {
                // Tenta no diretório pai caso esteja rodando de dentro de login-system
                imgFile = new File("../assets/usericon.png");
            }
            if (imgFile.exists()) {
                photo = ImageIO.read(imgFile);
            }
        } catch (Exception e) {
            // foto nao carregou, vai mostrar o placeholder
        }

        final BufferedImage finalPhoto = photo;
        JPanel panel = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                Container parent = getParent();
                int displaySize = 150;
                if (parent != null) {
                    displaySize = Math.min(150, Math.max(80, parent.getWidth() / 3));
                }
                return new Dimension(displaySize + 20, displaySize + 20);
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

                int center = Math.min(getWidth(), getHeight()) - 10;
                if (center <= 0) {
                    g2d.dispose();
                    return;
                }
                int x = (getWidth() - center) / 2;
                int y = (getHeight() - center) / 2;

                if (finalPhoto != null) {
                    g2d.setClip(new Ellipse2D.Double(x, y, center, center));
                    g2d.drawImage(finalPhoto, x, y, center, center, this);
                } else {
                    g2d.setColor(new Color(60, 60, 60));
                    g2d.fill(new Ellipse2D.Double(x, y, center, center));

                    g2d.setColor(Color.WHITE);
                    int headSize = center / 3;
                    g2d.fill(new Ellipse2D.Double(x + (center - headSize) / 2, y + center / 6, headSize, headSize));
                    g2d.fillArc(x + center / 8, y + center / 2 - 5, center * 3 / 4, center / 2, 0, 180);
                }

                g2d.dispose();
            }
        };
        panel.setBackground(Color.BLACK);
        return panel;
    }

    // valida os dados e autentica no banco
    private void handleLogin() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        if (emailGhost.isShowingGhost() || passwordGhost.isShowingGhost()) {
            DarkDialog.showError(this, "Erro", "Por favor, preencha todos os campos.");
        } else if (!ValidationUtils.isValidEmail(email)) {
            DarkDialog.showError(this, "Erro", "E-mail inválido. Certifique-se de usar '@' e um domínio.");
        } else {
            User user = userDAO.findByEmail(email);
            if (user == null) {
                DarkDialog.showError(this, "Erro", "E-mail não encontrado. Crie uma conta primeiro.");
            } else if (!user.getPassword().equals(password)) {
                DarkDialog.showError(this, "Erro", "Senha incorreta.");
            } else {
                DarkDialog.showInfo(this, "Sucesso", "Bem-vindo, " + user.getName() + "!");
                clearFields();

                // abre a tela principal do sistema de gestao
                SwingUtilities.invokeLater(() -> {
                    new ServiceManagementFrame().setVisible(true);
                });

                // fecha a janela de login
                Window loginWindow = SwingUtilities.getWindowAncestor(this);
                if (loginWindow != null) {
                    loginWindow.dispose();
                }
            }
        }
    }

    // reseta os campos e tira o foco pra ghost text aparecer
    public void clearFields() {
        emailField.setText("");
        passwordField.setText("");
        requestFocusInWindow();
    }

    // dialog de recuperacao de senha com tema escuro
    private void showForgotPasswordDialog() {
        Window parent = SwingUtilities.getWindowAncestor(this);
        JDialog dialog = new JDialog(parent, "Recuperar Senha", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setUndecorated(false);
        dialog.getRootPane().setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 2));
        dialog.setBackground(new Color(30, 30, 30));

        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(Color.BLACK);
        content.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 0, 8, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel title = new JLabel("Recuperar Senha", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        content.add(title, gbc);

        gbc.gridy = 1;
        JLabel info = new JLabel("<html><center>Digite seu e-mail e uma nova senha.</center></html>", SwingConstants.CENTER);
        info.setFont(new Font("SansSerif", Font.PLAIN, 12));
        info.setForeground(Color.LIGHT_GRAY);
        content.add(info, gbc);

        gbc.gridy = 2;
        JTextField emailField = UIUtils.createStyledTextField(20);
        GhostText emailGhost = new GhostText(emailField, "E-mail");
        content.add(emailField, gbc);

        gbc.gridy = 3;
        JPasswordField passField = UIUtils.createStyledPasswordField(20);
        GhostText passGhost = new GhostText(passField, "Nova Senha");
        content.add(passField, gbc);

        gbc.gridy = 4;
        JPasswordField confirmField = UIUtils.createStyledPasswordField(20);
        GhostText confirmGhost = new GhostText(confirmField, "Confirmar Nova Senha");
        content.add(confirmField, gbc);

        gbc.gridy = 5;
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnPanel.setBackground(Color.BLACK);

        JButton recoverBtn = UIUtils.createStyledButton("RECUPERAR", Color.WHITE, Color.BLACK);
        JButton cancelBtn = UIUtils.createStyledButton("CANCELAR", Color.WHITE, Color.BLACK);

        recoverBtn.addActionListener(e -> {
            String email = emailField.getText();
            String pass = new String(passField.getPassword());
            String confirm = new String(confirmField.getPassword());

            if (emailGhost.isShowingGhost() || passGhost.isShowingGhost() || confirmGhost.isShowingGhost()) {
                DarkDialog.showError(content, "Erro", "Preencha todos os campos.");
            } else if (!ValidationUtils.isValidEmail(email)) {
                DarkDialog.showError(content, "Erro", "E-mail invalido.");
            } else if (!userDAO.emailExists(email)) {
                DarkDialog.showError(content, "Erro", "E-mail nao encontrado.");
            } else if (!ValidationUtils.isValidPassword(pass)) {
                DarkDialog.showError(content, "Erro", "Senha deve ter pelo menos 8 caracteres e um especial.");
            } else if (!pass.equals(confirm)) {
                DarkDialog.showError(content, "Erro", "As senhas nao coincidem.");
            } else {
                if (userDAO.updatePassword(email, pass)) {
                    DarkDialog.showInfo(content, "Sucesso", "Senha atualizada com sucesso!");
                    dialog.dispose();
                } else {
                    DarkDialog.showError(content, "Erro", "Erro ao atualizar senha.");
                }
            }
        });

        cancelBtn.addActionListener(e -> dialog.dispose());

        btnPanel.add(recoverBtn);
        btnPanel.add(cancelBtn);
        content.add(btnPanel, gbc);

        dialog.setContentPane(content);
        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }
}
