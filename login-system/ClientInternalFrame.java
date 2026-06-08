import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ClientInternalFrame extends JPanel {

    private ClientDAO clientDAO;
    private JTable table;
    private DefaultTableModel model;
    private JTextField searchField;
    private JTextField nameField, phoneField, emailField, cpfField;
    private int selectedId = -1;

    private static final Color BG = Color.BLACK;
    private static final Color FG = Color.WHITE;
    private static final Color FIELD_BG = new Color(30, 30, 30);
    private static final Color ACCENT = new Color(100, 100, 100);
    private static final Font LABEL_FONT = new Font("SansSerif", Font.BOLD, 13);
    private static final Font FIELD_FONT = new Font("SansSerif", Font.PLAIN, 14);

    public ClientInternalFrame() {
        this.clientDAO = new ClientDAO();
        setLayout(new BorderLayout(10, 10));
        setBackground(BG);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        add(createFormPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);

        loadData();
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BG);
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(ACCENT), "Dados do Cliente",
                0, 0, new Font("SansSerif", Font.BOLD, 14), FG));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        nameField = addFormRow(panel, gbc, 0, "Nome:", new JTextField(25));
        phoneField = addFormRow(panel, gbc, 1, "Telefone:", new JTextField(15));
        emailField = addFormRow(panel, gbc, 2, "E-mail:", new JTextField(20));
        cpfField = addFormRow(panel, gbc, 3, "CPF:", new JTextField(15));

        return panel;
    }

    private JTextField addFormRow(JPanel panel, GridBagConstraints gbc, int row, String label, JTextField field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        JLabel lbl = new JLabel(label);
        lbl.setFont(LABEL_FONT);
        lbl.setForeground(FG);
        panel.add(lbl, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        styleField(field);
        panel.add(field, gbc);
        return field;
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

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG);

        // busca
        JPanel searchPanel = new JPanel(new BorderLayout(5, 0));
        searchPanel.setBackground(BG);
        JLabel searchLabel = new JLabel("Buscar:");
        searchLabel.setFont(LABEL_FONT);
        searchLabel.setForeground(FG);
        searchPanel.add(searchLabel, BorderLayout.WEST);

        searchField = new JTextField();
        styleField(searchField);
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { filter(); }
            public void removeUpdate(DocumentEvent e) { filter(); }
            public void changedUpdate(DocumentEvent e) { filter(); }
            private void filter() {
                String q = searchField.getText().trim();
                if (q.isEmpty()) loadData();
                else {
                    List<Client> list = clientDAO.searchByName(q);
                    populateTable(list);
                }
            }
        });
        searchPanel.add(searchField, BorderLayout.CENTER);
        panel.add(searchPanel, BorderLayout.NORTH);

        // tabela
        model = new DefaultTableModel(new String[]{"ID", "Nome", "Telefone", "E-mail", "CPF"}, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(model);
        table.setBackground(FIELD_BG);
        table.setForeground(FG);
        table.setGridColor(ACCENT);
        table.setSelectionBackground(new Color(60, 60, 60));
        table.setSelectionForeground(FG);
        table.setFont(FIELD_FONT);
        table.getTableHeader().setBackground(new Color(40, 40, 40));
        table.getTableHeader().setForeground(FG);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        table.setRowHeight(26);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                selectedId = (int) model.getValueAt(table.getSelectedRow(), 0);
                nameField.setText((String) model.getValueAt(table.getSelectedRow(), 1));
                phoneField.setText((String) model.getValueAt(table.getSelectedRow(), 2));
                emailField.setText((String) model.getValueAt(table.getSelectedRow(), 3));
                cpfField.setText((String) model.getValueAt(table.getSelectedRow(), 4));
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.getViewport().setBackground(BG);
        scroll.setBorder(BorderFactory.createLineBorder(ACCENT));
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panel.setBackground(BG);

        panel.add(createActionButton("Novo", e -> clearForm()));
        panel.add(createActionButton("Salvar", e -> save()));
        panel.add(createActionButton("Atualizar", e -> update()));
        panel.add(createActionButton("Excluir", e -> delete()));

        return panel;
    }

    private JButton createActionButton(String text, java.awt.event.ActionListener action) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setBackground(new Color(40, 40, 40));
        btn.setForeground(FG);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT),
                BorderFactory.createEmptyBorder(8, 18, 8, 18)));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addActionListener(action);
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { btn.setBackground(new Color(60, 60, 60)); }
            public void mouseExited(java.awt.event.MouseEvent evt) { btn.setBackground(new Color(40, 40, 40)); }
        });
        return btn;
    }

    private void loadData() {
        populateTable(clientDAO.findAll());
    }

    private void populateTable(List<Client> list) {
        model.setRowCount(0);
        for (Client c : list) {
            model.addRow(new Object[]{c.getId(), c.getName(), c.getPhone(), c.getEmail(), c.getCpf()});
        }
    }

    private void save() {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            DarkDialog.showError(this, "Erro", "O nome do cliente é obrigatório.");
            return;
        }
        String cpf = cpfField.getText().trim();
        if (!cpf.isEmpty() && clientDAO.cpfExists(cpf)) {
            DarkDialog.showError(this, "Erro", "CPF já cadastrado.");
            return;
        }
        Client c = new Client(name, phoneField.getText().trim(), emailField.getText().trim(), cpf);
        if (clientDAO.insert(c)) {
            DarkDialog.showInfo(this, "Sucesso", "Cliente cadastrado com sucesso!");
            clearForm();
            loadData();
        } else {
            DarkDialog.showError(this, "Erro", "Erro ao cadastrar cliente.");
        }
    }

    private void update() {
        if (selectedId == -1) {
            DarkDialog.showWarning(this, "Atenção", "Selecione um cliente na tabela para atualizar.");
            return;
        }
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            DarkDialog.showError(this, "Erro", "O nome do cliente é obrigatório.");
            return;
        }
        String cpf = cpfField.getText().trim();
        if (!cpf.isEmpty()) {
            Client existing = clientDAO.findByCpf(cpf);
            if (existing != null && existing.getId() != selectedId) {
                DarkDialog.showError(this, "Erro", "CPF já cadastrado para outro cliente.");
                return;
            }
        }
        Client c = new Client(name, phoneField.getText().trim(), emailField.getText().trim(), cpf);
        c.setId(selectedId);
        if (clientDAO.update(c)) {
            DarkDialog.showInfo(this, "Sucesso", "Cliente atualizado com sucesso!");
            clearForm();
            loadData();
        } else {
            DarkDialog.showError(this, "Erro", "Erro ao atualizar cliente.");
        }
    }

    private void delete() {
        if (selectedId == -1) {
            DarkDialog.showWarning(this, "Atenção", "Selecione um cliente na tabela para excluir.");
            return;
        }
        if (DarkDialog.showConfirm(this, "Confirmar", "Deseja realmente excluir este cliente?")) {
            if (clientDAO.delete(selectedId)) {
                DarkDialog.showInfo(this, "Sucesso", "Cliente excluído com sucesso!");
                clearForm();
                loadData();
            } else {
                DarkDialog.showError(this, "Erro", "Erro ao excluir cliente.");
            }
        }
    }

    private void clearForm() {
        selectedId = -1;
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
        cpfField.setText("");
        table.clearSelection();
    }
}
