import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FornecedorInternalFrame extends JPanel {

    private FornecedorDAO fornecedorDAO;
    private JTable table;
    private DefaultTableModel model;
    private JTextField nomeField, cnpjField, telefoneField, emailField, enderecoField;
    private int selectedId = -1;

    private static final Color BG = Color.BLACK;
    private static final Color FG = Color.WHITE;
    private static final Color FIELD_BG = new Color(30, 30, 30);
    private static final Color ACCENT = new Color(100, 100, 100);
    private static final Font LABEL_FONT = new Font("SansSerif", Font.BOLD, 13);
    private static final Font FIELD_FONT = new Font("SansSerif", Font.PLAIN, 14);

    public FornecedorInternalFrame() {
        this.fornecedorDAO = new FornecedorDAO();
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
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(ACCENT), "Dados do Fornecedor",
                0, 0, new Font("SansSerif", Font.BOLD, 14), FG));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        nomeField = addFormRow(panel, gbc, 0, "Nome:", new JTextField(25));
        cnpjField = addFormRow(panel, gbc, 1, "CNPJ:", new JTextField(18));
        telefoneField = addFormRow(panel, gbc, 2, "Telefone:", new JTextField(15));
        emailField = addFormRow(panel, gbc, 3, "E-mail:", new JTextField(20));
        enderecoField = addFormRow(panel, gbc, 4, "Endereço:", new JTextField(30));

        return panel;
    }

    private JTextField addFormRow(JPanel panel, GridBagConstraints gbc, int row, String label, JTextField field) {
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        JLabel lbl = new JLabel(label);
        lbl.setFont(LABEL_FONT);
        lbl.setForeground(FG);
        panel.add(lbl, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
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

        model = new DefaultTableModel(new String[]{"ID", "Nome", "CNPJ", "Telefone", "E-mail", "Endereço"}, 0) {
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
                nomeField.setText((String) model.getValueAt(table.getSelectedRow(), 1));
                cnpjField.setText((String) model.getValueAt(table.getSelectedRow(), 2));
                telefoneField.setText((String) model.getValueAt(table.getSelectedRow(), 3));
                emailField.setText((String) model.getValueAt(table.getSelectedRow(), 4));
                enderecoField.setText((String) model.getValueAt(table.getSelectedRow(), 5));
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
        populateTable(fornecedorDAO.findAll());
    }

    private void populateTable(List<Fornecedor> list) {
        model.setRowCount(0);
        for (Fornecedor f : list) {
            model.addRow(new Object[]{f.getId(), f.getNome(), f.getCnpj(), f.getTelefone(), f.getEmail(), f.getEndereco()});
        }
    }

    private void save() {
        String nome = nomeField.getText().trim();
        if (nome.isEmpty()) {
            DarkDialog.showError(this, "Erro", "O nome do fornecedor é obrigatório.");
            return;
        }
        Fornecedor f = new Fornecedor(nome, cnpjField.getText().trim(), telefoneField.getText().trim(),
                emailField.getText().trim(), enderecoField.getText().trim());
        if (fornecedorDAO.insert(f)) {
            DarkDialog.showInfo(this, "Sucesso", "Fornecedor cadastrado com sucesso!");
            clearForm(); loadData();
        } else {
            DarkDialog.showError(this, "Erro", "Erro ao cadastrar fornecedor.");
        }
    }

    private void update() {
        if (selectedId == -1) {
            DarkDialog.showWarning(this, "Atenção", "Selecione um fornecedor na tabela para atualizar.");
            return;
        }
        String nome = nomeField.getText().trim();
        if (nome.isEmpty()) {
            DarkDialog.showError(this, "Erro", "O nome do fornecedor é obrigatório.");
            return;
        }
        Fornecedor f = new Fornecedor(nome, cnpjField.getText().trim(), telefoneField.getText().trim(),
                emailField.getText().trim(), enderecoField.getText().trim());
        f.setId(selectedId);
        if (fornecedorDAO.update(f)) {
            DarkDialog.showInfo(this, "Sucesso", "Fornecedor atualizado com sucesso!");
            clearForm(); loadData();
        } else {
            DarkDialog.showError(this, "Erro", "Erro ao atualizar fornecedor.");
        }
    }

    private void delete() {
        if (selectedId == -1) {
            DarkDialog.showWarning(this, "Atenção", "Selecione um fornecedor na tabela para excluir.");
            return;
        }
        if (DarkDialog.showConfirm(this, "Confirmar", "Deseja realmente excluir este fornecedor?")) {
            if (fornecedorDAO.delete(selectedId)) {
                DarkDialog.showInfo(this, "Sucesso", "Fornecedor excluído com sucesso!");
                clearForm(); loadData();
            } else {
                DarkDialog.showError(this, "Erro", "Erro ao excluir fornecedor.");
            }
        }
    }

    private void clearForm() {
        selectedId = -1;
        nomeField.setText("");
        cnpjField.setText("");
        telefoneField.setText("");
        emailField.setText("");
        enderecoField.setText("");
        table.clearSelection();
    }
}
