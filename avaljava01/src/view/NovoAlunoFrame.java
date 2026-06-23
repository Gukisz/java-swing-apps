package view;

import model.Aluno;
import dao.AlunoDAO;
import javax.swing.*;
import java.awt.*;

public class NovoAlunoFrame extends JInternalFrame {

    private JTextField idField, nomeField, turmaField, emailField;
    private AlunoDAO alunoDAO;

    private static final Color BG = Color.BLACK;
    private static final Color FG = Color.WHITE;
    private static final Color FIELD_BG = new Color(30, 30, 30);
    private static final Color ACCENT = new Color(100, 100, 100);
    private static final Font LABEL_FONT = new Font("SansSerif", Font.BOLD, 13);
    private static final Font FIELD_FONT = new Font("SansSerif", Font.PLAIN, 14);

    public NovoAlunoFrame() {
        alunoDAO = new AlunoDAO();
        initComponents();
        setTitle("Cadastrar Aluno");
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setSize(500, 380);
        setLocation(30, 30);
        setMinimumSize(new Dimension(400, 300));
    }

    private void initComponents() {
        JPanel content = new JPanel(new BorderLayout(10, 10));
        content.setBackground(BG);
        content.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setContentPane(content);

        content.add(createFormPanel(), BorderLayout.CENTER);
        content.add(createButtonPanel(), BorderLayout.SOUTH);
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

        JButton btnAdicionar = createActionButton("Adicionar", new Color(40, 100, 40));
        btnAdicionar.addActionListener(e -> adicionarAluno());

        JButton btnLimpar = createActionButton("Limpar", new Color(100, 100, 100));
        btnLimpar.addActionListener(e -> limparCampos());

        panel.add(btnAdicionar);
        panel.add(btnLimpar);

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

        // checa ID duplicado
        if (alunoDAO.idExists(id)) {
            DarkDialog.showError(this, "Erro", "Já existe um aluno com este ID. Use outro ID.");
            return;
        }

        Aluno aluno = new Aluno(id, nome, turma, email);
        alunoDAO.insert(aluno);
        limparCampos();
        DarkDialog.showInfo(this, "Sucesso", "Aluno cadastrado com sucesso!");
    }

    private void limparCampos() {
        idField.setText("");
        nomeField.setText("");
        turmaField.setText("");
        emailField.setText("");
        idField.requestFocus();
    }
}
