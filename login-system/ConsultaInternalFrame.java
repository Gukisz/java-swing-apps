import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ConsultaInternalFrame extends JPanel {

    private ClientDAO clientDAO;
    private ProductDAO productDAO;
    private SupplierDAO supplierDAO;

    private JTabbedPane tabbedPane;
    private JTable clientTable, productTable, supplierTable;
    private DefaultTableModel clientModel, productModel, supplierModel;
    private JTextField clientSearch, productSearch, supplierSearch;

    private static final Color BG = Color.BLACK;
    private static final Color FG = Color.WHITE;
    private static final Color FIELD_BG = new Color(30, 30, 30);
    private static final Color ACCENT = new Color(100, 100, 100);
    private static final Font LABEL_FONT = new Font("SansSerif", Font.BOLD, 13);
    private static final Font FIELD_FONT = new Font("SansSerif", Font.PLAIN, 14);

    public ConsultaInternalFrame() {
        this.clientDAO = new ClientDAO();
        this.productDAO = new ProductDAO();
        this.supplierDAO = new SupplierDAO();
        setLayout(new BorderLayout());
        setBackground(BG);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(new Color(30, 30, 30));
        tabbedPane.setForeground(FG);
        tabbedPane.setFont(new Font("SansSerif", Font.BOLD, 14));
        tabbedPane.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() {
            protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
                g.setColor(ACCENT);
                g.drawRect(0, 0, tabPane.getWidth() - 1, tabPane.getHeight() - 1);
            }
        });

        tabbedPane.addTab("Clientes", createClientPanel());
        tabbedPane.addTab("Produtos", createProductPanel());
        tabbedPane.addTab("Fornecedores", createSupplierPanel());

        add(tabbedPane, BorderLayout.CENTER);

        loadClients();
        loadProducts();
        loadSuppliers();
    }

    private JPanel createClientPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BG);

        JPanel searchPanel = new JPanel(new BorderLayout(5, 0));
        searchPanel.setBackground(BG);
        JLabel lbl = new JLabel("Buscar:");
        lbl.setFont(LABEL_FONT);
        lbl.setForeground(FG);
        searchPanel.add(lbl, BorderLayout.WEST);

        clientSearch = new JTextField();
        styleField(clientSearch);
        clientSearch.getDocument().addDocumentListener(new SimpleDocumentListener(() -> {
            String q = clientSearch.getText().trim();
            if (q.isEmpty()) loadClients();
            else populateClientTable(clientDAO.searchByName(q));
        }));
        searchPanel.add(clientSearch, BorderLayout.CENTER);
        panel.add(searchPanel, BorderLayout.NORTH);

        clientModel = new DefaultTableModel(new String[]{"ID", "Nome", "Telefone", "E-mail", "Endereço"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        clientTable = createStyledTable(clientModel);
        panel.add(new JScrollPane(clientTable), BorderLayout.CENTER);
        ((JScrollPane)panel.getComponent(1)).getViewport().setBackground(BG);
        ((JScrollPane)panel.getComponent(1)).setBorder(BorderFactory.createLineBorder(ACCENT));

        return panel;
    }

    private JPanel createProductPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BG);

        JPanel searchPanel = new JPanel(new BorderLayout(5, 0));
        searchPanel.setBackground(BG);
        JLabel lbl = new JLabel("Buscar:");
        lbl.setFont(LABEL_FONT);
        lbl.setForeground(FG);
        searchPanel.add(lbl, BorderLayout.WEST);

        productSearch = new JTextField();
        styleField(productSearch);
        productSearch.getDocument().addDocumentListener(new SimpleDocumentListener(() -> {
            String q = productSearch.getText().trim();
            if (q.isEmpty()) loadProducts();
            else populateProductTable(productDAO.searchByName(q));
        }));
        searchPanel.add(productSearch, BorderLayout.CENTER);
        panel.add(searchPanel, BorderLayout.NORTH);

        productModel = new DefaultTableModel(new String[]{"ID", "Nome", "Descrição", "Preço", "Estoque", "Fornecedor"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        productTable = createStyledTable(productModel);
        panel.add(new JScrollPane(productTable), BorderLayout.CENTER);
        ((JScrollPane)panel.getComponent(1)).getViewport().setBackground(BG);
        ((JScrollPane)panel.getComponent(1)).setBorder(BorderFactory.createLineBorder(ACCENT));

        return panel;
    }

    private JPanel createSupplierPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BG);

        JPanel searchPanel = new JPanel(new BorderLayout(5, 0));
        searchPanel.setBackground(BG);
        JLabel lbl = new JLabel("Buscar:");
        lbl.setFont(LABEL_FONT);
        lbl.setForeground(FG);
        searchPanel.add(lbl, BorderLayout.WEST);

        supplierSearch = new JTextField();
        styleField(supplierSearch);
        supplierSearch.getDocument().addDocumentListener(new SimpleDocumentListener(() -> {
            String q = supplierSearch.getText().trim();
            if (q.isEmpty()) loadSuppliers();
            else populateSupplierTable(supplierDAO.searchByName(q));
        }));
        searchPanel.add(supplierSearch, BorderLayout.CENTER);
        panel.add(searchPanel, BorderLayout.NORTH);

        supplierModel = new DefaultTableModel(new String[]{"ID", "Nome", "Telefone", "E-mail", "Endereço", "CNPJ"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        supplierTable = createStyledTable(supplierModel);
        panel.add(new JScrollPane(supplierTable), BorderLayout.CENTER);
        ((JScrollPane)panel.getComponent(1)).getViewport().setBackground(BG);
        ((JScrollPane)panel.getComponent(1)).setBorder(BorderFactory.createLineBorder(ACCENT));

        return panel;
    }

    private JTable createStyledTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setBackground(FIELD_BG);
        table.setForeground(FG);
        table.setGridColor(ACCENT);
        table.setSelectionBackground(new Color(60, 60, 60));
        table.setSelectionForeground(FG);
        table.setFont(FIELD_FONT);
        table.getTableHeader().setBackground(new Color(40, 40, 40));
        table.getTableHeader().setForeground(FG);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        table.setRowHeight(26);
        return table;
    }

    private void styleField(JTextField field) {
        field.setBackground(FIELD_BG);
        field.setForeground(FG);
        field.setCaretColor(FG);
        field.setFont(FIELD_FONT);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)));
    }

    private void loadClients() {
        populateClientTable(clientDAO.findAll());
    }

    private void populateClientTable(List<Client> list) {
        clientModel.setRowCount(0);
        for (Client c : list) {
            clientModel.addRow(new Object[]{c.getId(), c.getName(), c.getPhone(), c.getEmail(), c.getCpf()});
        }
    }

    private void loadProducts() {
        populateProductTable(productDAO.findAll());
    }

    private void populateProductTable(List<Product> list) {
        productModel.setRowCount(0);
        for (Product p : list) {
            productModel.addRow(new Object[]{p.getId(), p.getName(), p.getDescription(),
                    String.format("%.2f", p.getPrice()), p.getStock(), p.getSupplier()});
        }
    }

    private void loadSuppliers() {
        populateSupplierTable(supplierDAO.findAll());
    }

    private void populateSupplierTable(List<Supplier> list) {
        supplierModel.setRowCount(0);
        for (Supplier s : list) {
            supplierModel.addRow(new Object[]{s.getId(), s.getName(), s.getPhone(), s.getEmail(), s.getAddress(), s.getCnpj()});
        }
    }

    private static class SimpleDocumentListener implements DocumentListener {
        private Runnable action;
        public SimpleDocumentListener(Runnable action) { this.action = action; }
        public void insertUpdate(DocumentEvent e) { action.run(); }
        public void removeUpdate(DocumentEvent e) { action.run(); }
        public void changedUpdate(DocumentEvent e) { action.run(); }
    }
}
