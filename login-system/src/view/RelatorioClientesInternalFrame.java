package view;
import controller.*;
import model.*;
import dao.*;

import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.print.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RelatorioClientesInternalFrame extends JPanel implements Printable {

    private ClientDAO clientDAO;
    private JTextPane previewPane;
    private String currentHtml = "";

    private static final Color BG = Color.BLACK;
    private static final Color FG = Color.WHITE;
    private static final Color FIELD_BG = new Color(30, 30, 30);
    private static final Color ACCENT = new Color(100, 100, 100);
    private static final Font BTN_FONT = new Font("SansSerif", Font.BOLD, 13);

    public RelatorioClientesInternalFrame() {
        this.clientDAO = new ClientDAO();
        setLayout(new BorderLayout(10, 10));
        setBackground(BG);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        add(createButtonPanel(), BorderLayout.NORTH);
        add(createPreviewPanel(), BorderLayout.CENTER);
        generateReport();
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel.setBackground(BG);

        panel.add(createActionButton("Imprimir", e -> printReport()));
        panel.add(createActionButton("Salvar HTML", e -> saveHtml()));
        panel.add(createActionButton("Atualizar", e -> generateReport()));

        return panel;
    }

    private JPanel createPreviewPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG);
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(ACCENT), "Relatorio de Clientes",
                0, 0, new Font("SansSerif", Font.BOLD, 14), FG));

        previewPane = new JTextPane();
        previewPane.setBackground(FIELD_BG);
        previewPane.setForeground(FG);
        previewPane.setFont(new Font("Monospaced", Font.PLAIN, 12));
        previewPane.setEditable(false);
        previewPane.setEditorKit(new HTMLEditorKit());

        JScrollPane scroll = new JScrollPane(previewPane);
        scroll.getViewport().setBackground(FIELD_BG);
        scroll.setBorder(BorderFactory.createLineBorder(ACCENT));
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    private JButton createActionButton(String text, java.awt.event.ActionListener action) {
        JButton btn = new JButton(text);
        btn.setFont(BTN_FONT);
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

    private void generateReport() {
        List<Client> list = clientDAO.findAll();
        StringBuilder html = new StringBuilder();
        html.append(buildHtmlHeader("Relatorio de Clientes", list.size()));
        html.append("<tr style='background:#282828; color:#FFFFFF;'>");
        html.append("<th style='padding:10px; border:1px solid #646464; text-align:left;'>ID</th>");
        html.append("<th style='padding:10px; border:1px solid #646464; text-align:left;'>Nome</th>");
        html.append("<th style='padding:10px; border:1px solid #646464; text-align:left;'>Telefone</th>");
        html.append("<th style='padding:10px; border:1px solid #646464; text-align:left;'>E-mail</th>");
        html.append("<th style='padding:10px; border:1px solid #646464; text-align:left;'>CPF</th>");
        html.append("</tr>");
        for (Client c : list) {
            html.append("<tr style='background:#1E1E1E;'>");
            html.append("<td style='padding:8px; border:1px solid #646464;'>").append(c.getId()).append("</td>");
            html.append("<td style='padding:8px; border:1px solid #646464;'>").append(escape(c.getName())).append("</td>");
            html.append("<td style='padding:8px; border:1px solid #646464;'>").append(escape(c.getPhone())).append("</td>");
            html.append("<td style='padding:8px; border:1px solid #646464;'>").append(escape(c.getEmail())).append("</td>");
            html.append("<td style='padding:8px; border:1px solid #646464;'>").append(escape(c.getCpf())).append("</td>");
            html.append("</tr>");
        }
        html.append("</table></body></html>");
        currentHtml = html.toString();
        previewPane.setText(currentHtml);
        previewPane.setCaretPosition(0);
    }

    private void printReport() {
        if (currentHtml.isEmpty()) return;
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(this);
        if (job.printDialog()) {
            try {
                job.print();
                DarkDialog.showInfo(this, "Sucesso", "Relatorio enviado para impressao.");
            } catch (PrinterException e) {
                DarkDialog.showError(this, "Erro", "Erro ao imprimir: " + e.getMessage());
            }
        }
    }

    public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
        if (pageIndex > 0) return NO_SUCH_PAGE;
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
        previewPane.print(g2d);
        return PAGE_EXISTS;
    }

    private void saveHtml() {
        if (currentHtml.isEmpty()) return;
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Salvar Relatorio como HTML");
        String filename = "relatorio_clientes_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".html";
        chooser.setSelectedFile(new File(filename));
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try (PrintWriter out = new PrintWriter(new FileWriter(file))) {
                out.print(currentHtml);
                DarkDialog.showInfo(this, "Sucesso", "Relatorio salvo em:\n" + file.getAbsolutePath());
            } catch (IOException e) {
                DarkDialog.showError(this, "Erro", "Erro ao salvar: " + e.getMessage());
            }
        }
    }

    private String buildHtmlHeader(String title, int count) {
        return "<html><body style='font-family:SansSerif; background:#1E1E1E; color:#FFFFFF; padding:20px;'>" +
                "<h2 style='color:#FFFFFF; border-bottom:2px solid #646464; padding-bottom:10px;'>" + title + "</h2>" +
                "<p style='color:#C8C8C8;'>Gerado em: " + formatDate() + "</p>" +
                "<p style='color:#C8C8C8;'>Total: " + count + " registros</p>" +
                "<table style='width:100%; border-collapse:collapse; margin-top:20px;'>";
    }

    private String formatDate() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    private String escape(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
