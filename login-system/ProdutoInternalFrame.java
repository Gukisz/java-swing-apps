import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProdutoInternalFrame extends JPanel {

    private ProdutoDAO produtoDAO;
    private JTable table;
    private DefaultTableModel model;
    private JTextField nomeField, descricaoField, precoField, quantidadeField;
    private int selectedId = -1;

    private static final Color BG = Color.BLACK;
    private static final Color FG = Color.WHITE;
    private static final Color FIELD_BG = new Color(30, 30, 30);
    private static final Color ACCENT = new Color(100, 100, 100);
    private static final Font LABEL_FONT = new Font("SansSerif", Font.BOLD, 13);
    private static final Font FIELD_FONT = new Font("SansSerif", Font.PLAIN, 14);

    public ProdutoInternalFrame() {
        this.produtoDAO = new ProdutoDAO();
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
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(ACCENT), "Dados do Produto",
                0, 0, new Font("SansSerif", Font.BOLD, 14), FG));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        nomeField = addFormRow(panel, gbc, 0, "Nome:", new JTextField(25));
        descricaoField = addFormRow(panel, gbc, 1, "Descrição:", new JTextField(30));
        precoField = addFormRow(panel, gbc, 2, "Preço (R$):", new JTextField(10));
        quantidadeField = addFormRow(panel, gbc, 3, "Quantidade:", new JTextField(10));

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

        model = new DefaultTableModel(new String[]{"ID", "Nome", "Descrição", "Preço", "Qtd"}, 0) {
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
                descricaoField.setText((String) model.getValueAt(table.getSelectedRow(), 2));
                precoField.setText(String.valueOf(model.getValueAt(table.getSelectedRow(), 3)));
                quantidadeField.setText(String.valueOf(model.getValueAt(table.getSelectedRow(), 4)));
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
        populateTable(produtoDAO.findAll());
    }

    private void populateTable(List<Produto> list) {
        model.setRowCount(0);
        for (Produto p : list) {
            model.addRow(new Object[]{p.getId(), p.getNome(), p.getDescricao(),
                    String.format("%.2f", p.getPreco()), p.getQuantidade()});
        }
    }

    private void save() {
        String nome = nomeField.getText().trim();
        if (nome.isEmpty()) {
            DarkDialog.showError(this, "Erro", "O nome do produto é obrigatório.");
            return;
        }
        double preco = 0;
        int qtd = 0;
        try { preco = Double.parseDouble(precoField.getText().trim().replace(",", ".")); } catch (Exception ignored) {}
        try { qtd = Integer.parseInt(quantidadeField.getText().trim()); } catch (Exception ignored) {}

        Produto p = new Produto(nome, descricaoField.getText().trim(), preco, qtd);
        if (produtoDAO.insert(p)) {
            DarkDialog.showInfo(this, "Sucesso", "Produto cadastrado com sucesso!");
            clearForm(); loadData();
        } else {
            DarkDialog.showError(this, "Erro", "Erro ao cadastrar produto.");
        }
    }

    private void update() {
        if (selectedId == -1) {
            DarkDialog.showWarning(this, "Atenção", "Selecione um produto na tabela para atualizar.");
            return;
        }
        String nome = nomeField.getText().trim();
        if (nome.isEmpty()) {
            DarkDialog.showError(this, "Erro", "O nome do produto é obrigatório.");
            return;
        }
        double preco = 0;
        int qtd = 0;
        try { preco = Double.parseDouble(precoField.getText().trim().replace(",", ".")); } catch (Exception ignored) {}
        try { qtd = Integer.parseInt(quantidadeField.getText().trim()); } catch (Exception ignored) {}

        Produto p = new Produto(nome, descricaoField.getText().trim(), preco, qtd);
        p.setId(selectedId);
        if (produtoDAO.update(p)) {
            DarkDialog.showInfo(this, "Sucesso", "Produto atualizado com sucesso!");
            clearForm(); loadData();
        } else {
            DarkDialog.showError(this, "Erro", "Erro ao atualizar produto.");
        }
    }

    private void delete() {
        if (selectedId == -1) {
            DarkDialog.showWarning(this, "Atenção", "Selecione um produto na tabela para excluir.");
            return;
        }
        if (DarkDialog.showConfirm(this, "Confirmar", "Deseja realmente excluir este produto?")) {
            if (produtoDAO.delete(selectedId)) {
                DarkDialog.showInfo(this, "Sucesso", "Produto excluído com sucesso!");
                clearForm(); loadData();
            } else {
                DarkDialog.showError(this, "Erro", "Erro ao excluir produto.");
            }
        }
    }

    private void clearForm() {
        selectedId = -1;
        nomeField.setText("");
        descricaoField.setText("");
        precoField.setText("");
        quantidadeField.setText("");
        table.clearSelection();
    }
}
