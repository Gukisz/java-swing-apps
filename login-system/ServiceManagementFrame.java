import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class ServiceManagementFrame extends JFrame {

    private JDesktopPane desktopPane;

    public ServiceManagementFrame() {
        setupDarkUI();
        initComponents();
        setTitle("Sistema de Gestão de Serviços");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
    }

    // configura o UIManager para forçar o tema escuro nos menus
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
        UIManager.put("ToolTip.background", darkBg);
        UIManager.put("ToolTip.foreground", darkFg);
    }

    private void initComponents() {
        // painel principal com borda layout
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(Color.BLACK);
        setContentPane(contentPane);

        // toolbar no topo
        JToolBar toolBar = createToolBar();
        contentPane.add(toolBar, BorderLayout.NORTH);

        // desktop pane no centro
        desktopPane = new JDesktopPane();
        desktopPane.setBackground(new Color(20, 20, 20));
        desktopPane.setComponentPopupMenu(createPopupMenu());
        contentPane.add(desktopPane, BorderLayout.CENTER);

        // barra de menu
        setJMenuBar(createMenuBar());
    }

    private JToolBar createToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setBackground(new Color(30, 30, 30));
        toolBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JButton btnCliente = createToolButton("cliente(1).png", "Clientes");
        btnCliente.addActionListener(e -> openInternalFrame("Clientes", new ClientInternalFrame()));
        toolBar.add(btnCliente);

        JButton btnServico = createToolButton("descricao-do-produto.png", "Serviços");
        btnServico.addActionListener(e -> DarkDialog.showInfo(this, "Serviços", "Módulo de serviços em desenvolvimento."));
        toolBar.add(btnServico);

        JButton btnOS = createToolButton("ordem-de-servico.png", "Ordem de Serviço");
        btnOS.addActionListener(e -> openInternalFrame("Ordem de Serviço", new ServiceOrderInternalFrame()));
        toolBar.add(btnOS);

        return toolBar;
    }

    private JButton createToolButton(String iconName, String tooltip) {
        JButton btn = new JButton();
        btn.setToolTipText(tooltip);
        btn.setBackground(new Color(30, 30, 30));
        btn.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        ImageIcon icon = IconUtils.loadIcon(iconName, 32, 32);
        if (icon != null) {
            btn.setIcon(icon);
        } else {
            btn.setText(tooltip.substring(0, 1));
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        }
        return btn;
    }

    private JPopupMenu createPopupMenu() {
        JPopupMenu popup = new JPopupMenu();
        popup.setBackground(new Color(40, 40, 40));
        popup.setForeground(Color.WHITE);
        popup.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)));

        JMenuItem itemCliente = createPopupItem("Cliente");
        itemCliente.addActionListener(e -> openInternalFrame("Clientes", new ClientInternalFrame()));
        popup.add(itemCliente);

        JMenuItem itemProduto = createPopupItem("Produto");
        itemProduto.addActionListener(e -> DarkDialog.showInfo(this, "Produtos", "Módulo de produtos em desenvolvimento."));
        popup.add(itemProduto);

        JMenuItem itemServico = createPopupItem("Serviço");
        itemServico.addActionListener(e -> DarkDialog.showInfo(this, "Serviços", "Módulo de serviços em desenvolvimento."));
        popup.add(itemServico);

        return popup;
    }

    private JMenuItem createPopupItem(String text) {
        JMenuItem item = new JMenuItem(text);
        item.setBackground(new Color(40, 40, 40));
        item.setForeground(Color.WHITE);
        item.setFont(new Font("SansSerif", Font.PLAIN, 14));
        return item;
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(30, 30, 30));
        menuBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(100, 100, 100)));

        // Menu Cadastros
        JMenu menuCadastro = createMenu("Cadastros");
        JMenuItem miClientes = createMenuItem("Clientes");
        miClientes.addActionListener(e -> openInternalFrame("Clientes", new ClientInternalFrame()));
        menuCadastro.add(miClientes);

        JMenuItem miProdutos = createMenuItem("Produtos");
        miProdutos.addActionListener(e -> openInternalFrame("Produtos", new ProdutoInternalFrame()));
        menuCadastro.add(miProdutos);

        JMenuItem miFornecedores = createMenuItem("Fornecedores");
        miFornecedores.addActionListener(e -> openInternalFrame("Fornecedores", new FornecedorInternalFrame()));
        menuCadastro.add(miFornecedores);
        menuBar.add(menuCadastro);

        // Menu Movimento
        JMenu menuMovimento = createMenu("Movimento");
        JMenuItem miOS = createMenuItem("Ordem de Serviço");
        miOS.addActionListener(e -> openInternalFrame("Ordem de Serviço", new ServiceOrderInternalFrame()));
        menuMovimento.add(miOS);
        menuBar.add(menuMovimento);

        // Menu Relatório
        JMenu menuRelatorio = createMenu("Relatório");
        JMenuItem miRelClientes = createMenuItem("Clientes");
        miRelClientes.addActionListener(e -> DarkDialog.showInfo(this, "Relatório", "Relatório de Clientes em desenvolvimento."));
        menuRelatorio.add(miRelClientes);

        JMenuItem miRelServicos = createMenuItem("Serviços");
        miRelServicos.addActionListener(e -> DarkDialog.showInfo(this, "Relatório", "Relatório de Serviços em desenvolvimento."));
        menuRelatorio.add(miRelServicos);
        menuRelatorio.add(new JSeparator());

        JMenuItem miRelOS = createMenuItem("Ordem de Serviços");
        miRelOS.addActionListener(e -> DarkDialog.showInfo(this, "Relatório", "Relatório de Ordens de Serviço em desenvolvimento."));
        menuRelatorio.add(miRelOS);
        menuBar.add(menuRelatorio);

        // Menu Utilitário
        JMenu menuUtil = createMenu("Utilitário");
        JMenuItem miAgenda = createMenuItem("Agenda");
        miAgenda.addActionListener(e -> DarkDialog.showInfo(this, "Utilitário", "Agenda em desenvolvimento."));
        menuUtil.add(miAgenda);

        JMenuItem miCalc = createMenuItem("Calculadora");
        miCalc.addActionListener(this::openCalculator);
        menuUtil.add(miCalc);

        JMenuItem miBloco = createMenuItem("Bloco de Notas");
        miBloco.addActionListener(this::openNotepad);
        menuUtil.add(miBloco);
        menuBar.add(menuUtil);

        // Menu Sobre
        JMenu menuSobre = createMenu("Sobre");
        JMenuItem miInfo = createMenuItem("Informações sobre o Sistema");
        miInfo.addActionListener(e -> DarkDialog.showInfo(this, "Sobre",
            "Sistema de Gestão de Serviços\nVersão 1.0\n\nDesenvolvido com Java Swing + SQLite."));
        menuSobre.add(miInfo);
        menuBar.add(menuSobre);

        // Menu Janela
        JMenu menuJanela = createMenu("Janela");
        JMenuItem miMinimizar = createMenuItem("Minimizar Todas");
        miMinimizar.addActionListener(e -> minimizeAll());
        menuJanela.add(miMinimizar);

        JMenuItem miRestaurar = createMenuItem("Restaurar Todas");
        miRestaurar.addActionListener(e -> restoreAll());
        menuJanela.add(miRestaurar);
        menuJanela.add(new JSeparator());

        JMenuItem miCascata = createMenuItem("Cascata");
        miCascata.addActionListener(e -> cascadeWindows());
        menuJanela.add(miCascata);

        JMenuItem miGrade = createMenuItem("Grade");
        miGrade.addActionListener(e -> tileWindows());
        menuJanela.add(miGrade);

        JMenuItem miLado = createMenuItem("Lado a Lado");
        miLado.addActionListener(e -> tileWindowsHorizontal());
        menuJanela.add(miLado);
        menuJanela.add(new JSeparator());

        JMenuItem miFechar = createMenuItem("Fechar Todas");
        miFechar.addActionListener(e -> closeAll());
        menuJanela.add(miFechar);
        menuBar.add(menuJanela);

        // Menu Ajuda
        JMenu menuAjuda = createMenu("Ajuda");
        JMenuItem miAjuda = createMenuItem("Ajuda do Sistema");
        miAjuda.addActionListener(e -> DarkDialog.showInfo(this, "Ajuda", "Ajuda em desenvolvimento."));
        menuAjuda.add(miAjuda);
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

    // abre uma janela interna no desktop pane — impede duplicatas pelo titulo
    private void openInternalFrame(String title, JComponent content) {
        // verifica se ja existe uma janela com esse titulo aberta
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            if (frame.getTitle().equals(title) && !frame.isClosed()) {
                try {
                    frame.setIcon(false);     // restaura se estiver minimizada
                    frame.setSelected(true);  // traz para frente
                } catch (Exception ignored) {}
                return;
            }
        }

        JInternalFrame frame = new JInternalFrame(title, true, true, true, true);
        frame.setSize(700, 500);
        frame.setLocation((desktopPane.getWidth() - 700) / 2, (desktopPane.getHeight() - 500) / 2);
        if (desktopPane.getWidth() == 0 || desktopPane.getHeight() == 0) {
            frame.setLocation(50, 50);
        }

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.BLACK);
        content.setBackground(Color.BLACK);
        content.setForeground(Color.WHITE);
        if (content instanceof JLabel) {
            JLabel lbl = (JLabel) content;
            lbl.setFont(new Font("SansSerif", Font.BOLD, 18));
        }
        panel.add(content, BorderLayout.CENTER);

        frame.setContentPane(panel);
        frame.setVisible(true);
        desktopPane.add(frame);
        try {
            frame.setSelected(true);
        } catch (Exception ignored) {}
    }

    // organiza as janelas em cascata
    private void cascadeWindows() {
        JInternalFrame[] frames = desktopPane.getAllFrames();
        int x = 0, y = 0;
        for (JInternalFrame frame : frames) {
            try {
                frame.setIcon(false);
            } catch (Exception ignored) {}
            frame.setLocation(x, y);
            x += 30;
            y += 30;
        }
    }

    // organiza as janelas em grade (tile)
    private void tileWindows() {
        JInternalFrame[] frames = desktopPane.getAllFrames();
        int count = frames.length;
        if (count == 0) return;

        int cols = (int) Math.ceil(Math.sqrt(count));
        int rows = (int) Math.ceil((double) count / cols);

        int w = desktopPane.getWidth() / cols;
        int h = desktopPane.getHeight() / rows;

        int i = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols && i < count; c++) {
                try {
                    frames[i].setIcon(false);
                } catch (Exception ignored) {}
                frames[i].setBounds(c * w, r * h, w, h);
                i++;
            }
        }
    }

    // organiza as janelas lado a lado (horizontal)
    private void tileWindowsHorizontal() {
        JInternalFrame[] frames = desktopPane.getAllFrames();
        int count = frames.length;
        if (count == 0) return;

        int w = desktopPane.getWidth() / count;
        int h = desktopPane.getHeight();

        for (int i = 0; i < count; i++) {
            try {
                frames[i].setIcon(false);
            } catch (Exception ignored) {}
            frames[i].setBounds(i * w, 0, w, h);
        }
    }

    // minimiza todas as janelas internas
    private void minimizeAll() {
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            try {
                frame.setIcon(true);
            } catch (Exception ignored) {}
        }
    }

    // restaura todas as janelas internas
    private void restoreAll() {
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            try {
                frame.setIcon(false);
            } catch (Exception ignored) {}
        }
    }

    // fecha todas as janelas internas
    private void closeAll() {
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            frame.dispose();
        }
    }

    // abre a calculadora do sistema operacional
    private void openCalculator(ActionEvent e) {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                Runtime.getRuntime().exec("calc");
            } else if (os.contains("mac")) {
                Runtime.getRuntime().exec("open /System/Applications/Calculator.app");
            } else {
                Runtime.getRuntime().exec("gnome-calculator");
            }
        } catch (IOException ex) {
            DarkDialog.showError(this, "Erro", "Não foi possível abrir a calculadora.");
        }
    }

    // abre o bloco de notas do sistema operacional
    private void openNotepad(ActionEvent e) {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                Runtime.getRuntime().exec("notepad");
            } else if (os.contains("mac")) {
                Runtime.getRuntime().exec("open /System/Applications/TextEdit.app");
            } else {
                Runtime.getRuntime().exec("gedit");
            }
        } catch (IOException ex) {
            DarkDialog.showError(this, "Erro", "Não foi possível abrir o bloco de notas.");
        }
    }
}
