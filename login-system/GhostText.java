import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

// faz o texto de placeholder aparecer e sumir igual nos apps modernos
public class GhostText implements FocusListener, DocumentListener {
    private final JTextField textfield;
    private final String ghostText;
    private boolean showingGhost; // controla se o ghost ta aparecendo ou nao
    private Color ghostColor = new Color(200, 200, 200);
    private Color foregroundColor;

    public GhostText(JTextField textfield, String ghostText) {
        this.textfield = textfield;
        this.ghostText = ghostText;
        this.foregroundColor = Color.WHITE;
        this.textfield.addFocusListener(this);
        this.textfield.getDocument().addDocumentListener(this);
        updateState();
    }

    // coloca o texto fantasma no campo
    private void showGhost() {
        showingGhost = true;
        textfield.setText(ghostText);
        textfield.setForeground(ghostColor);
    }

    // remove o texto fantasma e prepara pro usuario digitar
    private void hideGhost() {
        showingGhost = false;
        textfield.setText("");
        textfield.setForeground(foregroundColor);
    }

    // ve se o campo ta vazio e sem foco, se sim mostra o ghost
    private void updateState() {
        String text = textfield.getText();
        boolean hasText = text != null && !text.isEmpty() && !text.equals(ghostText);

        if (!textfield.hasFocus() && !hasText) {
            showGhost();
        } else {
            showingGhost = false;
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        // quando clica no campo, esconde o ghost
        if (showingGhost) {
            hideGhost();
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        // quando sai do campo, ve se precisa mostrar o ghost de novo
        updateState();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        // quando o usuario digita alguma coisa, desativa o ghost
        String text = textfield.getText();
        if (text != null && !text.isEmpty() && !text.equals(ghostText)) {
            showingGhost = false;
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        // se apagou tudo e nao tem foco, volta o ghost
        if (textfield.getText().isEmpty() && !textfield.hasFocus()) {
            SwingUtilities.invokeLater(this::updateState);
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        // nao usado mas precisa implementar pela interface
    }

    // os paineis usam isso pra saber se o campo ta preenchido ou é ghost
    public boolean isShowingGhost() {
        return showingGhost;
    }
}
