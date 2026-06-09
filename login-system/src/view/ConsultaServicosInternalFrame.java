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

public class ConsultaServicosInternalFrame extends JPanel {

    private ServiceDAO serviceDAO;
    private JTable table;
    private DefaultTableModel model;
    private JTextField searchField;

    private static final Color BG = Color.BLACK;
    private static final Color FG = Color.WHITE;
    private static final Color FIELD_BG = new Color(30, 30, 30);
    private static final Color ACCENT = new Color(100, 100, 100);
    private static final Font FIELD_FONT = new Font("SansSerif", Font.PLAIN, 14);

    public ConsultaServicosInternalFrame() {
        this.serviceDAO = new ServiceDAO();
        setLayout(new BorderLayout(10, 10));
        setBackground(BG);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        add(createTablePanel(), BorderLayout.CENTER);
        loadData();
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG);

        JPanel searchPanel = new JPanel(new BorderLayout(5, 0));
        searchPanel.setBackground(BG);
        JLabel lbl = new JLabel("Buscar:");
        lbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        lbl.setForeground(FG);
        searchPanel.add(lbl, BorderLayout.WEST);

        searchField = new JTextField();
        styleField(searchField);
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { filter(); }
            public void removeUpdate(DocumentEvent e) { filter(); }
            public void changedUpdate(DocumentEvent e) { filter(); }
            private void filter() {
                String q = searchField.getText().trim();
                if (q.isEmpty()) loadData();
                else populateTable(serviceDAO.searchByName(q));
            }
        });
        searchPanel.add(searchField, BorderLayout.CENTER);
        panel.add(searchPanel, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[]{"ID", "Nome", "Descricao", "Preco"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = createStyledTable(model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.getViewport().setBackground(BG);
        scroll.setBorder(BorderFactory.createLineBorder(ACCENT));
        panel.add(scroll, BorderLayout.CENTER);

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

    private void loadData() {
        populateTable(serviceDAO.findAll());
    }

    private void populateTable(List<Service> list) {
        model.setRowCount(0);
        for (Service s : list) {
            model.addRow(new Object[]{s.getId(), s.getName(), s.getDescription(), String.format("%.2f", s.getPrice())});
        }
    }
}
