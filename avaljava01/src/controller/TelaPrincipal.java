package controller;

import view.*;
import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        setupDarkUI();
        initComponents();
        setTitle("Avaliação Java Swing 01");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(600, 400));
    }

    private void setupDarkUI() {
        Color darkBg = new Color(30, 30, 30);
        Color darkFg = Color.WHITE;
        Color darkSel = new Color(60, 60, 60);
        Color popupBg = new Color(40, 40, 40);

        UIManager.put("MenuBar.background", darkBg);
        UIManager.put("Menu.background", darkBg);
        UIManager.put("Menu.foreground", darkFg);
        UIManager.put("Menu.selectionBackground", darkSel);
        UIManager.put("Menu.selectionForeground", darkFg);
        UIManager.put("MenuItem.background", popupBg);
        UIManager.put("MenuItem.foreground", darkFg);
        UIManager.put("MenuItem.selectionBackground", darkSel);
        UIManager.put("MenuItem.selectionForeground", darkFg);
        UIManager.put("PopupMenu.background", popupBg);
        UIManager.put("PopupMenu.foreground", darkFg);
    }

    private void initComponents() {
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(Color.BLACK);
        setContentPane(contentPane);

        JLabel lbl = new JLabel("Sistema de Cadastro de Alunos", SwingConstants.CENTER);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 28));
        lbl.setForeground(Color.WHITE);
        contentPane.add(lbl, BorderLayout.CENTER);

        setJMenuBar(createMenuBar());
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(30, 30, 30));
        menuBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(100, 100, 100)));

        // Menu Arquivo
        JMenu menuArquivo = createMenu("Arquivo");
        JMenuItem miNovo = createMenuItem("Novo");
        miNovo.addActionListener(e -> new NovoAlunoFrame().setVisible(true));
        menuArquivo.add(miNovo);

        JMenuItem miEditar = createMenuItem("Editar");
        miEditar.addActionListener(e -> DarkDialog.showInfo(this, "Editar", "Função Editar em desenvolvimento."));
        menuArquivo.add(miEditar);
        menuBar.add(menuArquivo);

        // Menu Editar
        JMenu menuEditar = createMenu("Editar");
        JMenuItem miDesfazer = createMenuItem("Desfazer");
        miDesfazer.addActionListener(e -> DarkDialog.showInfo(this, "Desfazer", "Função Desfazer em desenvolvimento."));
        menuEditar.add(miDesfazer);

        JMenuItem miRefazer = createMenuItem("Refazer");
        miRefazer.addActionListener(e -> DarkDialog.showInfo(this, "Refazer", "Função Refazer em desenvolvimento."));
        menuEditar.add(miRefazer);
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
        SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));
    }
}
