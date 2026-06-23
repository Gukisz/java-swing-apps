package view;

import model.Aluno;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NovoAlunoFrame extends JInternalFrame {

    private JTextField idField, nomeField, turmaField, emailField;
    private JTable table;
    private DefaultTableModel model;
    private List<Aluno> alunos = new ArrayList<>();

    private static final Color BG = Color.BLACK;
    private static final Color FG = Color.WHITE;
    private static final Color FIELD_BG = new Color(30, 30, 30);
    private static final Color ACCENT = new Color(100, 100, 100);
    private static final Font LABEL_FONT = new Font("SansSerif", Font.BOLD, 13);
    private static final Font FIELD_FONT = new Font("SansSerif", Font.PLAIN, 14);

    public NovoAlunoFrame() {
        initComponents();
        setTitle("Novo Aluno");
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setMaximizable(true);
        setSize(650, 450);
        setLocation(30, 30);
        setMinimumSize(new Dimension(500, 350));
    }

    private void initComponents() {
        JPanel content = new JPanel(new BorderLayout(10, 10));
        content.setBackground(BG);
        content.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setContentPane(content);

        content.add(createFormPanel(), BorderLayout.NORTH);
        content.add(createTablePanel(), BorderLayout.CENTER);
        content.add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BG);
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(ACCENT), "Dados do Aluno",
                0, 0, new Font("SansSerif", Font.BOLD, 14), FG));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        idField = addFormRow(panel, gbc, 0, "ID:", new JTextField(10));
        nomeField = addFormRow(panel, gbc, 1, "Nome:", new JTextField(25));
        turmaField = addFormRow(panel, gbc, 2, "Turma:", new JTextField(15));
        emailField = addFormRow(panel, gbc, 3, "E-mail:", new JTextField(20));

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
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(ACCENT), "Alunos Cadastrados",
                0, 0, new Font("SansSerif", Font.BOLD, 14), FG));

        model = new DefaultTableModel(new String[]{"ID", "Nome", "Turma", "E-mail"}, 0) {
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

        JScrollPane scroll = new JScrollPane(table);
        scroll.getViewport().setBackground(BG);
        scroll.setBorder(BorderFactory.createLineBorder(ACCENT));
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panel.setBackground(BG);

        JButton btnAdicionar = createActionButton("Adicionar", new Color(40, 100, 40));
        btnAdicionar.addActionListener(e -> adicionarAluno());

        JButton btnExcluir = createActionButton("Excluir", new Color(140, 40, 40));
        btnExcluir.addActionListener(e -> excluirAluno());

        panel.add(btnAdicionar);
        panel.add(btnExcluir);

        return panel;
    }

    private JButton createActionButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setBackground(bg);
        btn.setForeground(FG);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT),
                BorderFactory.createEmptyBorder(8, 22, 8, 22)));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(bg.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(bg);
            }
        });
        return btn;
    }

    private void adicionarAluno() {
        String idStr = idField.getText().trim();
        String nome = nomeField.getText().trim();
        String turma = turmaField.getText().trim();
        String email = emailField.getText().trim();

        if (idStr.isEmpty() || nome.isEmpty() || turma.isEmpty() || email.isEmpty()) {
            DarkDialog.showError(this, "Erro", "Preencha todos os campos.");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            DarkDialog.showError(this, "Erro", "ID deve ser um número válido.");
            return;
        }

        Aluno aluno = new Aluno(id, nome, turma, email);
        alunos.add(aluno);
        ordenarPorNome();
        atualizarTabela();
        limparCampos();
        DarkDialog.showInfo(this, "Sucesso", "Aluno cadastrado com sucesso!");
    }

    private void excluirAluno() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            DarkDialog.showWarning(this, "Atenção", "Selecione um aluno na tabela para excluir.");
            return;
        }

        if (DarkDialog.showConfirm(this, "Confirmar", "Deseja realmente excluir este aluno?")) {
            alunos.remove(selectedRow);
            atualizarTabela();
            DarkDialog.showInfo(this, "Sucesso", "Aluno excluído com sucesso!");
        }
    }

    private void ordenarPorNome() {
        Collections.sort(alunos, Comparator.comparing(Aluno::getNome, String.CASE_INSENSITIVE_ORDER));
    }

    private void atualizarTabela() {
        model.setRowCount(0);
        for (Aluno a : alunos) {
            model.addRow(new Object[]{a.getId(), a.getNome(), a.getTurma(), a.getEmail()});
        }
    }

    private void limparCampos() {
        idField.setText("");
        nomeField.setText("");
        turmaField.setText("");
        emailField.setText("");
        idField.requestFocus();
    }
}
