package view;

import model.Aluno;
import dao.AlunoDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EditarAlunoFrame extends JInternalFrame {

    private JTextField idField, nomeField, turmaField, emailField;
    private JTable table;
    private DefaultTableModel model;
    private AlunoDAO alunoDAO;

    private int editandoId = -1;

    private static final Color BG = Color.BLACK;
    private static final Color FG = Color.WHITE;
    private static final Color FIELD_BG = new Color(30, 30, 30);
    private static final Color ACCENT = new Color(100, 100, 100);
    private static final Font LABEL_FONT = new Font("SansSerif", Font.BOLD, 13);
    private static final Font FIELD_FONT = new Font("SansSerif", Font.PLAIN, 14);

    public EditarAlunoFrame() {
        alunoDAO = new AlunoDAO();
        initComponents();
        setTitle("Editar Alunos");
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setMaximizable(true);
        setSize(700, 550);
        setLocation(30, 30);
        setMinimumSize(new Dimension(550, 400));
        carregarDados();
    }

    private void initComponents() {
        JPanel content = new JPanel(new BorderLayout(10, 10));
        content.setBackground(BG);
        content.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setContentPane(content);

        content.add(createTablePanel(), BorderLayout.NORTH);
        content.add(createFormPanel(), BorderLayout.CENTER);
        content.add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG);
        panel.setPreferredSize(new Dimension(0, 180));
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

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    carregarParaEdicao();
                }
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.getViewport().setBackground(BG);
        scroll.setBorder(BorderFactory.createLineBorder(ACCENT));
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BG);
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(ACCENT), "Dados do Aluno",
                0, 0, new Font("SansSerif", Font.BOLD, 14), FG));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        idField = addFormRow(panel, gbc, 0, "ID:", new JTextField(10));
        idField.setEditable(false); // ID não pode ser alterado na edição
        idField.setBackground(new Color(50, 50, 50));

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

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panel.setBackground(BG);

        JButton btnCarregar = createActionButton("Carregar", new Color(70, 70, 180));
        btnCarregar.addActionListener(e -> carregarParaEdicao());

        JButton btnSalvar = createActionButton("Salvar", new Color(40, 100, 40));
        btnSalvar.addActionListener(e -> salvarEdicao());

        JButton btnExcluir = createActionButton("Excluir", new Color(140, 40, 40));
        btnExcluir.addActionListener(e -> excluirAluno());

        JButton btnCancelar = createActionButton("Cancelar", new Color(100, 100, 100));
        btnCancelar.addActionListener(e -> cancelarEdicao());

        panel.add(btnCarregar);
        panel.add(btnSalvar);
        panel.add(btnExcluir);
        panel.add(btnCancelar);

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

    private void carregarDados() {
        List<Aluno> alunos = alunoDAO.findAll();
        model.setRowCount(0);
        for (Aluno a : alunos) {
            model.addRow(new Object[]{a.getId(), a.getNome(), a.getTurma(), a.getEmail()});
        }
    }

    private void carregarParaEdicao() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            DarkDialog.showWarning(this, "Atenção", "Selecione um aluno na tabela para editar.");
            return;
        }

        editandoId = (int) model.getValueAt(selectedRow, 0);
        String nome = (String) model.getValueAt(selectedRow, 1);
        String turma = (String) model.getValueAt(selectedRow, 2);
        String email = (String) model.getValueAt(selectedRow, 3);

        idField.setText(String.valueOf(editandoId));
        nomeField.setText(nome);
        turmaField.setText(turma);
        emailField.setText(email);

        setTitle("Editar Alunos - Editando ID " + editandoId);
    }

    private void salvarEdicao() {
        if (editandoId == -1) {
            DarkDialog.showWarning(this, "Atenção", "Selecione um aluno e clique em Carregar primeiro.");
            return;
        }

        String nome = nomeField.getText().trim();
        String turma = turmaField.getText().trim();
        String email = emailField.getText().trim();

        if (nome.isEmpty() || turma.isEmpty() || email.isEmpty()) {
            DarkDialog.showError(this, "Erro", "Preencha todos os campos.");
            return;
        }

        Aluno aluno = new Aluno(editandoId, nome, turma, email);
        alunoDAO.update(aluno);
        carregarDados();
        cancelarEdicao();
        DarkDialog.showInfo(this, "Sucesso", "Aluno atualizado com sucesso!");
    }

    private void excluirAluno() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            DarkDialog.showWarning(this, "Atenção", "Selecione um aluno na tabela para excluir.");
            return;
        }

        int id = (int) model.getValueAt(selectedRow, 0);
        String nome = (String) model.getValueAt(selectedRow, 1);

        if (DarkDialog.showConfirm(this, "Confirmar", "Deseja realmente excluir o aluno \"" + nome + "\"?")) {
            alunoDAO.delete(id);
            carregarDados();
            cancelarEdicao();
            DarkDialog.showInfo(this, "Sucesso", "Aluno excluído com sucesso!");
        }
    }

    private void cancelarEdicao() {
        editandoId = -1;
        idField.setText("");
        nomeField.setText("");
        turmaField.setText("");
        emailField.setText("");
        setTitle("Editar Alunos");
    }
}
