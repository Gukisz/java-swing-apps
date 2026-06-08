import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.print.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RelatorioInternalFrame extends JPanel implements Printable {

    private ClientDAO clientDAO;
    private ProductDAO productDAO;
    private ServiceOrderDAO orderDAO;

    private JTextPane previewPane;
    private String currentHtml = "";
    private int currentReportType = 0; // 1=clientes, 2=produtos, 3=os

    private static final Color BG = Color.BLACK;
    private static final Color FG = Color.WHITE;
    private static final Color FIELD_BG = new Color(30, 30, 30);
    private static final Color ACCENT = new Color(100, 100, 100);
    private static final Font LABEL_FONT = new Font("SansSerif", Font.BOLD, 13);
    private static final Font BTN_FONT = new Font("SansSerif", Font.BOLD, 13);

    public RelatorioInternalFrame() {
        this.clientDAO = new ClientDAO();
        this.productDAO = new ProductDAO();
        this.orderDAO = new ServiceOrderDAO();
        setLayout(new BorderLayout(10, 10));
        setBackground(BG);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        add(createButtonPanel(), BorderLayout.NORTH);
        add(createPreviewPanel(), BorderLayout.CENTER);
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel.setBackground(BG);
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(ACCENT), "Gerar Relatório",
                0, 0, new Font("SansSerif", Font.BOLD, 14), FG));

        panel.add(createActionButton("Clientes", e -> generateClientReport()));
        panel.add(createActionButton("Produtos", e -> generateProductReport()));
        panel.add(createActionButton("Ordens de Serviço", e -> generateOrderReport()));

        panel.add(Box.createHorizontalStrut(30));
        panel.add(createActionButton("Imprimir", e -> printReport()));
        panel.add(createActionButton("Salvar HTML", e -> saveHtml()));

        return panel;
    }

    private JPanel createPreviewPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG);
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(ACCENT), "Pré-visualização",
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

    private void generateClientReport() {
        currentReportType = 1;
        List<Client> list = clientDAO.findAll();
        StringBuilder html = new StringBuilder();
        html.append("<html><body style='font-family:SansSerif; background:#1E1E1E; color:#FFFFFF; padding:20px;'>");
        html.append("<h2 style='color:#FFFFFF; border-bottom:2px solid #646464; padding-bottom:10px;'>Relatório de Clientes</h2>");
        html.append("<p style='color:#C8C8C8;'>Gerado em: ").append(formatDate()).append("</p>");
        html.append("<p style='color:#C8C8C8;'>Total: ").append(list.size()).append(" clientes</p>");
        html.append("<table style='width:100%; border-collapse:collapse; margin-top:20px;'>");
        html.append("<tr style='background:#282828; color:#FFFFFF;'>");
        html.append("<th style='padding:10px; border:1px solid #646464; text-align:left;'>ID</th>");
        html.append("<th style='padding:10px; border:1px solid #646464; text-align:left;'>Nome</th>");
        html.append("<th style='padding:10px; border:1px solid #646464; text-align:left;'>Telefone</th>");
        html.append("<th style='padding:10px; border:1px solid #646464; text-align:left;'>E-mail</th>");
        html.append("<th style='padding:10px; border:1px solid #646464; text-align:left;'>Endereço</th>");
        html.append("</tr>");
        for (Client c : list) {
            html.append("<tr style='background:#1E1E1E;'>");
            html.append("<td style='padding:8px; border:1px solid #646464;'>").append(c.getId()).append("</td>");
            html.append("<td style='padding:8px; border:1px solid #646464;'>").append(escape(c.getName())).append("</td>");
            html.append("<td style='padding:8px; border:1px solid #646464;'>").append(escape(c.getPhone())).append("</td>");
            html.append("<td style='padding:8px; border:1px solid #646464;'>").append(escape(c.getEmail())).append("</td>");
            html.append("<td style='padding:8px; border:1px solid #646464;'>").append(escape(c.getAddress())).append("</td>");
            html.append("</tr>");
        }
        html.append("</table></body></html>");
        currentHtml = html.toString();
        previewPane.setText(currentHtml);
        previewPane.setCaretPosition(0);
    }

    private void generateProductReport() {
        currentReportType = 2;
        List<Product> list = productDAO.findAll();
        StringBuilder html = new StringBuilder();
        html.append("<html><body style='font-family:SansSerif; background:#1E1E1E; color:#FFFFFF; padding:20px;'>");
        html.append("<h2 style='color:#FFFFFF; border-bottom:2px solid #646464; padding-bottom:10px;'>Relatório de Produtos</h2>");
        html.append("<p style='color:#C8C8C8;'>Gerado em: ").append(formatDate()).append("</p>");
        html.append("<p style='color:#C8C8C8;'>Total: ").append(list.size()).append(" produtos</p>");
        html.append("<table style='width:100%; border-collapse:collapse; margin-top:20px;'>");
        html.append("<tr style='background:#282828; color:#FFFFFF;'>");
        html.append("<th style='padding:10px; border:1px solid #646464; text-align:left;'>ID</th>");
        html.append("<th style='padding:10px; border:1px solid #646464; text-align:left;'>Nome</th>");
        html.append("<th style='padding:10px; border:1px solid #646464; text-align:left;'>Descrição</th>");
        html.append("<th style='padding:10px; border:1px solid #646464; text-align:left;'>Preço</th>");
        html.append("<th style='padding:10px; border:1px solid #646464; text-align:left;'>Estoque</th>");
        html.append("<th style='padding:10px; border:1px solid #646464; text-align:left;'>Fornecedor</th>");
        html.append("</tr>");
        for (Product p : list) {
            html.append("<tr style='background:#1E1E1E;'>");
            html.append("<td style='padding:8px; border:1px solid #646464;'>").append(p.getId()).append("</td>");
            html.append("<td style='padding:8px; border:1px solid #646464;'>").append(escape(p.getName())).append("</td>");
            html.append("<td style='padding:8px; border:1px solid #646464;'>").append(escape(p.getDescription())).append("</td>");
            html.append("<td style='padding:8px; border:1px solid #646464;'>R$ ").append(String.format("%.2f", p.getPrice())).append("</td>");
            html.append("<td style='padding:8px; border:1px solid #646464;'>").append(p.getStock()).append("</td>");
            html.append("<td style='padding:8px; border:1px solid #646464;'>").append(escape(p.getSupplier())).append("</td>");
            html.append("</tr>");
        }
        html.append("</table></body></html>");
        currentHtml = html.toString();
        previewPane.setText(currentHtml);
        previewPane.setCaretPosition(0);
    }

    private void generateOrderReport() {
        currentReportType = 3;
        List<ServiceOrder> list = orderDAO.findAll();
        StringBuilder html = new StringBuilder();
        html.append("<html><body style='font-family:SansSerif; background:#1E1E1E; color:#FFFFFF; padding:20px;'>");
        html.append("<h2 style='color:#FFFFFF; border-bottom:2px solid #646464; padding-bottom:10px;'>Relatório de Ordens de Serviço</h2>");
        html.append("<p style='color:#C8C8C8;'>Gerado em: ").append(formatDate()).append("</p>");
        html.append("<p style='color:#C8C8C8;'>Total: ").append(list.size()).append(" ordens</p>");
        html.append("<table style='width:100%; border-collapse:collapse; margin-top:20px;'>");
        html.append("<tr style='background:#282828; color:#FFFFFF;'>");
        html.append("<th style='padding:10px; border:1px solid #646464; text-align:left;'>OS</th>");
        html.append("<th style='padding:10px; border:1px solid #646464; text-align:left;'>Cliente</th>");
        html.append("<th style='padding:10px; border:1px solid #646464; text-align:left;'>Equipamento</th>");
        html.append("<th style='padding:10px; border:1px solid #646464; text-align:left;'>Serviço</th>");
        html.append("<th style='padding:10px; border:1px solid #646464; text-align:left;'>Técnico</th>");
        html.append("<th style='padding:10px; border:1px solid #646464; text-align:left;'>Valor</th>");
        html.append("<th style='padding:10px; border:1px solid #646464; text-align:left;'>Status</th>");
        html.append("</tr>");
        for (ServiceOrder so : list) {
            html.append("<tr style='background:#1E1E1E;'>");
            html.append("<td style='padding:8px; border:1px solid #646464;'>").append(so.getId()).append("</td>");
            html.append("<td style='padding:8px; border:1px solid #646464;'>").append(escape(so.getClientName())).append("</td>");
            html.append("<td style='padding:8px; border:1px solid #646464;'>").append(escape(so.getEquipment())).append("</td>");
            html.append("<td style='padding:8px; border:1px solid #646464;'>").append(escape(so.getService())).append("</td>");
            html.append("<td style='padding:8px; border:1px solid #646464;'>").append(escape(so.getTechnician())).append("</td>");
            html.append("<td style='padding:8px; border:1px solid #646464;'>R$ ").append(String.format("%.2f", so.getValue())).append("</td>");
            html.append("<td style='padding:8px; border:1px solid #646464;'>").append(escape(so.getStatus())).append("</td>");
            html.append("</tr>");
        }
        html.append("</table></body></html>");
        currentHtml = html.toString();
        previewPane.setText(currentHtml);
        previewPane.setCaretPosition(0);
    }

    private void printReport() {
        if (currentHtml.isEmpty()) {
            DarkDialog.showWarning(this, "Atenção", "Gere um relatório primeiro.");
            return;
        }
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(this);
        boolean doPrint = job.printDialog();
        if (doPrint) {
            try {
                job.print();
                DarkDialog.showInfo(this, "Sucesso", "Relatório enviado para impressão.");
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
        if (currentHtml.isEmpty()) {
            DarkDialog.showWarning(this, "Atenção", "Gere um relatório primeiro.");
            return;
        }
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Salvar Relatório como HTML");
        String filename = "relatorio_";
        if (currentReportType == 1) filename += "clientes";
        else if (currentReportType == 2) filename += "produtos";
        else filename += "ordens_servico";
        filename += "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".html";
        chooser.setSelectedFile(new File(filename));
        int result = chooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try (PrintWriter out = new PrintWriter(new FileWriter(file))) {
                out.print(currentHtml);
                DarkDialog.showInfo(this, "Sucesso", "Relatório salvo em:\n" + file.getAbsolutePath());
            } catch (IOException e) {
                DarkDialog.showError(this, "Erro", "Erro ao salvar: " + e.getMessage());
            }
        }
    }

    private String formatDate() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    private String escape(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
