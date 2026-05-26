import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class RegisterPanel extends JPanel {
    private MainApp mainApp;
    private UserDAO userDAO;
    private JTextField nameField, emailField;
    private JPasswordField passwordField, confirmPasswordField;
    private GhostText nameGhost, emailGhost, passwordGhost, confirmGhost;

    public RegisterPanel(MainApp mainApp) {
        this.mainApp = mainApp;
        this.userDAO = new UserDAO();
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);
        setFocusable(true);

        // coluna do meio centralizada com espacadores dos lados
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 0;

        gbc.gridx = 0;
        gbc.weightx = 0.3;
        add(Box.createHorizontalGlue(), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0;
        gbc.insets = new Insets(8, 0, 8, 0);

        // titulo da tela de cadastro
        JLabel titleLabel = new JLabel("Criar Conta", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel, gbc);

        // campos do formulario de cadastro
        gbc.gridy = 1;
        nameField = UIUtils.createStyledTextField(20);
        nameGhost = new GhostText(nameField, "Nome Completo");
        add(nameField, gbc);

        gbc.gridy = 2;
        emailField = UIUtils.createStyledTextField(20);
        emailGhost = new GhostText(emailField, "E-mail");
        add(emailField, gbc);

        gbc.gridy = 3;
        passwordField = UIUtils.createStyledPasswordField(20);
        passwordGhost = new GhostText(passwordField, "Senha");
        add(passwordField, gbc);

        gbc.gridy = 4;
        confirmPasswordField = UIUtils.createStyledPasswordField(20);
        confirmGhost = new GhostText(confirmPasswordField, "Confirmar Senha");
        add(confirmPasswordField, gbc);

        gbc.gridy = 5;
        JButton registerButton = UIUtils.createStyledButton("CRIAR CONTA", Color.WHITE, Color.BLACK);
        add(registerButton, gbc);

        gbc.gridy = 6;
        gbc.insets = new Insets(4, 0, 8, 0);
        JButton backBtn = UIUtils.createLinkButton("Já tem uma conta? Entrar");
        add(backBtn, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.3;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(Box.createHorizontalGlue(), gbc);

        registerButton.addActionListener(e -> handleRegister());
        backBtn.addActionListener(e -> mainApp.showLogin());

        // limpa os campos sempre que essa tela aparecer
        addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent e) {
                clearFields();
                RegisterPanel.this.requestFocusInWindow();
            }
        });
    }

    // validacao completa do formulario de cadastro
    private void handleRegister() {
        String name = nameField.getText();
        String email = emailField.getText();
        String pass = new String(passwordField.getPassword());
        String confirmPass = new String(confirmPasswordField.getPassword());

        // verifica se tem campos vazios antes de qualquer coisa
        if (nameGhost.isShowingGhost() || emailGhost.isShowingGhost()
                || passwordGhost.isShowingGhost() || confirmGhost.isShowingGhost()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios.", "Erro",
                    JOptionPane.ERROR_MESSAGE);
        } else if (ValidationUtils.showErrorIf(this, name.length() < 3,
                "O nome deve ter pelo menos 3 caracteres.")) {
        } else if (ValidationUtils.showErrorIf(this, !ValidationUtils.isValidEmail(email),
                "E-mail inválido. Certifique-se de usar '@' e um domínio.")) {
        } else if (ValidationUtils.showErrorIf(this, !ValidationUtils.isValidPassword(pass),
                "A senha deve ter pelo menos 8 caracteres e um caractere especial.")) {
        } else if (ValidationUtils.showErrorIf(this, !pass.equals(confirmPass),
                "As senhas não coincidem.")) {
        } else if (ValidationUtils.showErrorIf(this, userDAO.emailExists(email),
                "Este e-mail já está cadastrado.")) {
        } else {
            // passou em todas as validacoes, salva no banco
            User newUser = new User(name, email, pass);
            if (userDAO.insert(newUser)) {
                JOptionPane.showMessageDialog(this, "Conta criada com sucesso para: " + name, "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
                clearFields();
                mainApp.showLogin();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao salvar no banco de dados. Tente novamente.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // reseta tudo e volta o ghost text
    public void clearFields() {
        nameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
        requestFocusInWindow();
    }
}
