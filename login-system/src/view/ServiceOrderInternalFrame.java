package view;
import controller.*;
import model.*;
import dao.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ServiceOrderInternalFrame extends JPanel {

    private ServiceOrderDAO orderDAO;
    private ClientDAO clientDAO;
    private JTable table;
    private DefaultTableModel model;
    private JComboBox<ClientComboItem> clientCombo;
    private JTextField equipmentField, defectField, serviceField, technicianField, valueField;
    private JComboBox<String> statusCombo;
    private int selectedId = -1;

    private static final Color BG = Color.BLACK;
    private static final Color FG = Color.WHITE;
    private static final Color FIELD_BG = new Color(30, 30, 30);
    private static final Color ACCENT = new Color(100, 100, 100);
    private static final Font LABEL_FONT = new Font("SansSerif", Font.BOLD, 13);
    private static final Font FIELD_FONT = new Font("SansSerif", Font.PLAIN, 14);

    public ServiceOrderInternalFrame() {
        this.orderDAO = new ServiceOrderDAO();
        this.clientDAO = new ClientDAO();
        setLayout(new BorderLayout(10, 10));
        setBackground(BG);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        add(createFormPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);

        loadData();
        loadClients();
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BG);
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(ACCENT), "Dados da Ordem de Serviço",
                0, 0, new Font("SansSerif", Font.BOLD, 14), FG));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Cliente
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        JLabel lblClient = new JLabel("Cliente:");
        lblClient.setFont(LABEL_FONT);
        lblClient.setForeground(FG);
        panel.add(lblClient, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        clientCombo = new JComboBox<>();
        styleCombo(clientCombo);
        panel.add(clientCombo, gbc);

        // Equipamento
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        JLabel lblEquip = new JLabel("Equipamento:");
        lblEquip.setFont(LABEL_FONT);
        lblEquip.setForeground(FG);
        panel.add(lblEquip, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        equipmentField = new JTextField(25);
        styleField(equipmentField);
        panel.add(equipmentField, gbc);

        // Defeito
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        JLabel lblDefect = new JLabel("Defeito:");
        lblDefect.setFont(LABEL_FONT);
        lblDefect.setForeground(FG);
        panel.add(lblDefect, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        defectField = new JTextField(25);
        styleField(defectField);
        panel.add(defectField, gbc);

        // Serviço
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        JLabel lblService = new JLabel("Serviço:");
        lblService.setFont(LABEL_FONT);
        lblService.setForeground(FG);
        panel.add(lblService, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        serviceField = new JTextField(25);
        styleField(serviceField);
        panel.add(serviceField, gbc);

        // Técnico
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0;
        JLabel lblTech = new JLabel("Técnico:");
        lblTech.setFont(LABEL_FONT);
        lblTech.setForeground(FG);
        panel.add(lblTech, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        technicianField = new JTextField(20);
        styleField(technicianField);
        panel.add(technicianField, gbc);

        // Valor
        gbc.gridx = 0; gbc.gridy = 5; gbc.weightx = 0;
        JLabel lblValue = new JLabel("Valor (R$):");
        lblValue.setFont(LABEL_FONT);
        lblValue.setForeground(FG);
        panel.add(lblValue, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        valueField = new JTextField(10);
        styleField(valueField);
        panel.add(valueField, gbc);

        // Status
        gbc.gridx = 0; gbc.gridy = 6; gbc.weightx = 0;
        JLabel lblStatus = new JLabel("Status:");
        lblStatus.setFont(LABEL_FONT);
        lblStatus.setForeground(FG);
        panel.add(lblStatus, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        statusCombo = new JComboBox<>(new String[]{"Aberta", "Em andamento", "Finalizada", "Cancelada"});
        styleCombo(statusCombo);
        panel.add(statusCombo, gbc);

        return panel;
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

    private void styleCombo(JComboBox<?> combo) {
        combo.setBackground(FIELD_BG);
        combo.setForeground(FG);
        combo.setFont(FIELD_FONT);
        combo.setBorder(BorderFactory.createLineBorder(ACCENT));
        // renderer escuro
        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setBackground(isSelected ? new Color(60, 60, 60) : FIELD_BG);
                setForeground(FG);
                return this;
            }
        });
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG);

        model = new DefaultTableModel(new String[]{"OS", "Cliente", "Equipamento", "Defeito", "Serviço", "Técnico", "Valor", "Status"}, 0) {
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
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        table.setRowHeight(26);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                selectedId = (int) model.getValueAt(table.getSelectedRow(), 0);
                String clientName = (String) model.getValueAt(table.getSelectedRow(), 1);
                equipmentField.setText((String) model.getValueAt(table.getSelectedRow(), 2));
                defectField.setText((String) model.getValueAt(table.getSelectedRow(), 3));
                serviceField.setText((String) model.getValueAt(table.getSelectedRow(), 4));
                technicianField.setText((String) model.getValueAt(table.getSelectedRow(), 5));
                String valStr = String.valueOf(model.getValueAt(table.getSelectedRow(), 6));
                valueField.setText(valStr);
                statusCombo.setSelectedItem(model.getValueAt(table.getSelectedRow(), 7));

                // seleciona cliente no combo
                for (int i = 0; i < clientCombo.getItemCount(); i++) {
                    if (clientCombo.getItemAt(i).getName().equals(clientName)) {
                        clientCombo.setSelectedIndex(i);
                        break;
                    }
                }
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

    private void loadClients() {
        clientCombo.removeAllItems();
        List<Client> list = clientDAO.findAll();
        for (Client c : list) {
            clientCombo.addItem(new ClientComboItem(c.getId(), c.getName()));
        }
    }

    private void loadData() {
        populateTable(orderDAO.findAll());
    }

    private void populateTable(List<ServiceOrder> list) {
        model.setRowCount(0);
        for (ServiceOrder so : list) {
            model.addRow(new Object[]{
                so.getId(),
                so.getClientName(),
                so.getEquipment(),
                so.getDefect(),
                so.getService(),
                so.getTechnician(),
                String.format("%.2f", so.getValue()),
                so.getStatus()
            });
        }
    }

    private void save() {
        if (clientCombo.getSelectedItem() == null) {
            DarkDialog.showError(this, "Erro", "Selecione um cliente.");
            return;
        }
        if (equipmentField.getText().trim().isEmpty()) {
            DarkDialog.showError(this, "Erro", "O equipamento é obrigatório.");
            return;
        }

        ServiceOrder so = buildOrder();
        if (orderDAO.insert(so)) {
            DarkDialog.showInfo(this, "Sucesso", "Ordem de serviço cadastrada com sucesso!");
            clearForm();
            loadData();
            loadClients(); // atualiza combo
        } else {
            DarkDialog.showError(this, "Erro", "Erro ao cadastrar OS.");
        }
    }

    private void update() {
        if (selectedId == -1) {
            DarkDialog.showWarning(this, "Atenção", "Selecione uma OS na tabela para atualizar.");
            return;
        }
        if (clientCombo.getSelectedItem() == null) {
            DarkDialog.showError(this, "Erro", "Selecione um cliente.");
            return;
        }
        if (equipmentField.getText().trim().isEmpty()) {
            DarkDialog.showError(this, "Erro", "O equipamento é obrigatório.");
            return;
        }

        ServiceOrder so = buildOrder();
        so.setId(selectedId);
        if (orderDAO.update(so)) {
            DarkDialog.showInfo(this, "Sucesso", "Ordem de serviço atualizada com sucesso!");
            clearForm();
            loadData();
        } else {
            DarkDialog.showError(this, "Erro", "Erro ao atualizar OS.");
        }
    }

    private void delete() {
        if (selectedId == -1) {
            DarkDialog.showWarning(this, "Atenção", "Selecione uma OS na tabela para excluir.");
            return;
        }
        if (DarkDialog.showConfirm(this, "Confirmar", "Deseja realmente excluir esta OS?")) {
            if (orderDAO.delete(selectedId)) {
                DarkDialog.showInfo(this, "Sucesso", "Ordem de serviço excluída com sucesso!");
                clearForm();
                loadData();
            } else {
                DarkDialog.showError(this, "Erro", "Erro ao excluir OS.");
            }
        }
    }

    private ServiceOrder buildOrder() {
        ClientComboItem item = (ClientComboItem) clientCombo.getSelectedItem();
        double value = 0.0;
        try {
            value = Double.parseDouble(valueField.getText().trim().replace(",", "."));
        } catch (NumberFormatException ignored) {}

        ServiceOrder so = new ServiceOrder();
        so.setClientId(item.getId());
        so.setEquipment(equipmentField.getText().trim());
        so.setDefect(defectField.getText().trim());
        so.setService(serviceField.getText().trim());
        so.setTechnician(technicianField.getText().trim());
        so.setValue(value);
        so.setStatus((String) statusCombo.getSelectedItem());
        return so;
    }

    private void clearForm() {
        selectedId = -1;
        if (clientCombo.getItemCount() > 0) clientCombo.setSelectedIndex(0);
        equipmentField.setText("");
        defectField.setText("");
        serviceField.setText("");
        technicianField.setText("");
        valueField.setText("");
        statusCombo.setSelectedIndex(0);
        table.clearSelection();
    }

    // item do combo de clientes
    private static class ClientComboItem {
        private int id;
        private String name;
        public ClientComboItem(int id, String name) { this.id = id; this.name = name; }
        public int getId() { return id; }
        public String getName() { return name; }
        @Override
        public String toString() { return name; }
    }
}
