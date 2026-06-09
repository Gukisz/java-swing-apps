package controller;
import view.*;
import model.*;
import dao.*;

import javax.swing.*;
import java.awt.*;

public class MainApp extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public MainApp() {
        setTitle("Login & Cadastro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 600);
        setMinimumSize(new Dimension(360, 520));
        setLocationRelativeTo(null);
        setResizable(true);
        // fundo preto pra dar aquele contraste
        getContentPane().setBackground(Color.BLACK);

        // card layout pra gente alternar entre login e cadastro
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(Color.BLACK);

        // cria as duas telas e adiciona no card pra navegar
        LoginPanel loginPanel = new LoginPanel(this);
        RegisterPanel registerPanel = new RegisterPanel(this);

        mainPanel.add(loginPanel, "LOGIN");
        mainPanel.add(registerPanel, "REGISTER");

        add(mainPanel);

        // começa com a tela de login
        cardLayout.show(mainPanel, "LOGIN");
    }

    // só troca o card que ta aparecendo
    public void showLogin() {
        cardLayout.show(mainPanel, "LOGIN");
    }

    public void showRegister() {
        cardLayout.show(mainPanel, "REGISTER");
    }

    public static void main(String[] args) {
        try {
            // Usa o visual multiplataforma (Metal) para evitar que o tema do sistema (como GTK no Linux) force tela branca e ignore o fundo preto
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // cria a tabela de usuarios se nao existir ainda
        DatabaseSetup.init();

        // roda a interface na thread certa do swing
        SwingUtilities.invokeLater(() -> {
            new MainApp().setVisible(true);
        });
    }
}
