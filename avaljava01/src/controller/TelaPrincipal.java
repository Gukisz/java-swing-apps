package controller;

import view.*;
import dao.DatabaseConnection;
import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {

    private JDesktopPane desktopPane;

    public TelaPrincipal() {
        setTitle("Avaliação Java Swing 01");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setMinimumSize(new Dimension(700, 500));
        setLocationRelativeTo(null);

        // Inicializa banco de dados
        DatabaseConnection.init();

        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);

        desktopPane = new JDesktopPane();
        desktopPane.setBackground(Color.BLACK);
        mainPanel.add(desktopPane, BorderLayout.CENTER);

        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setBackground(new Color(30, 30, 30));
        statusBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(100, 100, 100)));
        JLabel statusLabel = new JLabel(" Pronto");
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        statusBar.add(statusLabel, BorderLayout.WEST);
        mainPanel.add(statusBar, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setJMenuBar(createMenuBar());
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(30, 30, 30));
        menuBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(100, 100, 100)));

        // Menu Arquivo
        JMenu menuArquivo = createMenu("Arquivo");
        JMenuItem miNovo = createMenuItem("Novo");
        miNovo.addActionListener(e -> abrirFrame(new NovoAlunoFrame()));
        menuArquivo.add(miNovo);
        menuBar.add(menuArquivo);

        // Menu Editar
        JMenu menuEditar = createMenu("Editar");
        JMenuItem miEditarAluno = createMenuItem("Editar Aluno");
        miEditarAluno.addActionListener(e -> abrirFrame(new EditarAlunoFrame()));
        menuEditar.add(miEditarAluno);
        menuBar.add(menuEditar);

        // Menu Exibir
        JMenu menuExibir = createMenu("Exibir");
        JMenuItem miZoom = createMenuItem("Zoom");
        miZoom.addActionListener(e -> DarkDialog.showInfo(this, "Zoom", "Função Zoom em desenvolvimento."));
        menuExibir.add(miZoom);
        menuExibir.add(new JSeparator());

        JMenuItem miRegua = createMenuItem("Régua");
        miRegua.addActionListener(e -> DarkDialog.showInfo(this, "Régua", "Função Régua em desenvolvimento."));
        menuExibir.add(miRegua);
        menuBar.add(menuExibir);

        // Menu Ajuda
        JMenu menuAjuda = createMenu("Ajuda");
        JMenuItem miSobre = createMenuItem("Sobre o Sistema");
        miSobre.addActionListener(e -> DarkDialog.showInfo(this, "Sobre",
                "Sistema Avaliação Java Swing\nVersão 1.0\nDesenvolvido pelos alunos"));
        menuAjuda.add(miSobre);
        menuBar.add(menuAjuda);

        return menuBar;
    }

    private void abrirFrame(JInternalFrame frame) {
        desktopPane.add(frame);
        try {
            frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
        frame.setVisible(true);
    }

    private JMenu createMenu(String text) {
        JMenu menu = new JMenu(text);
        menu.setBackground(new Color(30, 30, 30));
        menu.setForeground(Color.WHITE);
        menu.setFont(new Font("SansSerif", Font.BOLD, 14));
        menu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return menu;
    }

    private JMenuItem createMenuItem(String text) {
        JMenuItem item = new JMenuItem(text);
        item.setBackground(new Color(40, 40, 40));
        item.setForeground(Color.WHITE);
        item.setFont(new Font("SansSerif", Font.PLAIN, 14));
        return item;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new TelaPrincipal().setVisible(true);
        });
    }
}
