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
    private JTextField emailField;
    private JPasswordField passwordField;
    private GhostText emailGhost, passwordGhost;

    public LoginPanel(MainApp mainApp) {
        this.mainApp = mainApp;
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
        gbc.weightx = 0;
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
        forgotBtn.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Funcionalidade de recuperação de senha em breve.", "Informação",
                        JOptionPane.INFORMATION_MESSAGE));

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
            if (imgFile.exists()) {
                photo = ImageIO.read(imgFile);
            }
        } catch (Exception e) {
            // foto nao carregou, vai mostrar o placeholder
        }

        final BufferedImage finalPhoto = photo;
        final int displaySize = 150;

        JPanel panel = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(displaySize + 20, displaySize + 20);
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

                int center = Math.min(getWidth(), getHeight()) - 10;
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

    // valida os dados e mostra mensagem de erro ou sucesso
    private void handleLogin() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        if (emailGhost.isShowingGhost() || passwordGhost.isShowingGhost()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Erro",
                    JOptionPane.ERROR_MESSAGE);
        } else if (!ValidationUtils.isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "E-mail inválido. Certifique-se de usar '@' e um domínio.", "Erro",
                    JOptionPane.ERROR_MESSAGE);
        } else if (!ValidationUtils.isValidPassword(password)) {
            JOptionPane.showMessageDialog(this, "A senha deve ter pelo menos 8 caracteres e um caractere especial.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Login realizado como: " + email, "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
            clearFields();
        }
    }

    // reseta os campos e tira o foco pra ghost text aparecer
    public void clearFields() {
        emailField.setText("");
        passwordField.setText("");
        requestFocusInWindow();
    }
}
