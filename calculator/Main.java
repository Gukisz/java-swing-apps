import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(Main::createCalculator);
    }

    private static void createCalculator() {
        JFrame frame = new JFrame("Calculadora Rosa");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(320, 460);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(new Color(250, 235, 243));
        frame.setLayout(new BorderLayout(10, 10));

        JTextField display = createDisplay();
        JPanel buttons = createButtonPanel(display);

        frame.add(display, BorderLayout.NORTH);
        frame.add(buttons, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private static JTextField createDisplay() {
        JTextField display = new JTextField("0");
        display.setFont(new Font("Segoe UI", Font.PLAIN, 32));
        display.setBackground(new Color(255, 245, 250)); 
        display.setForeground(new Color(45, 45, 45)); 
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setEditable(false);
        display.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        return display;
    }

    private static JPanel createButtonPanel(JTextField display) {
        JPanel panel = new JPanel(new GridLayout(5, 4, 8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(250, 235, 243));

        String[] keys = {
            "C", "←", "%", "/",
            "7", "8", "9", "*",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "±", "0", ".", "="
        };

        CalculatorState state = new CalculatorState(display);

        for (String key : keys) {
            JButton button = createButton(key);
            button.addActionListener(e -> state.press(key));
            panel.add(button);
        }

        return panel;
    }

    private static JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 22));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));
        button.setOpaque(true);

        if ("=".equals(text)) {
            button.setBackground(new Color(255, 105, 180)); 
            button.setForeground(Color.WHITE);
        } else if ("+".equals(text) || "-".equals(text) || "*".equals(text) || "/".equals(text)) {
            button.setBackground(new Color(255, 140, 180));
            button.setForeground(Color.WHITE);
        } else if ("C".equals(text) || "←".equals(text) || "%".equals(text) || "±".equals(text)) {
            button.setBackground(new Color(255, 220, 230));
            button.setForeground(new Color(45, 45, 45));
        } else {
            button.setBackground(new Color(255, 190, 210)); 
            button.setForeground(new Color(45, 45, 45));
        }

        return button;
    }

    private static class CalculatorState {
        private final JTextField display;
        private String currentValue = "0";
        private double accumulator = 0;
        private String operator = "";
        private boolean startNewNumber = true;

        CalculatorState(JTextField display) {
            this.display = display;
        }

        void press(String key) {
            switch (key) {
                case "C":
                    clear();
                    break;
                case "←":
                    backspace();
                    break;
                case "%":
                    percent();
                    break;
                case "±":
                    toggleSign();
                    break;
                case "+":
                case "-":
                case "*":
                case "/":
                    applyOperator(key);
                    break;
                case "=":
                    computeResult();
                    break;
                case ".":
                    appendDot();
                    break;
                default:
                    appendDigit(key);
                    break;
            }
            updateDisplay();
        }

        private void clear() {
            currentValue = "0";
            accumulator = 0;
            operator = "";
            startNewNumber = true;
        }

        private void backspace() {
            if (startNewNumber || currentValue.length() <= 1) {
                currentValue = "0";
                startNewNumber = true;
                return;
            }
            currentValue = currentValue.substring(0, currentValue.length() - 1);
            if (currentValue.equals("-") || currentValue.isEmpty()) {
                currentValue = "0";
                startNewNumber = true;
            }
        }

        private void percent() {
            double value = parseCurrentValue();
            currentValue = formatValue(value / 100.0);
            startNewNumber = true;
        }

        private void toggleSign() {
            if (currentValue.equals("0")) {
                return;
            }
            if (currentValue.startsWith("-")) {
                currentValue = currentValue.substring(1);
            } else {
                currentValue = "-" + currentValue;
            }
        }

        private void applyOperator(String nextOperator) {
            if (!operator.isEmpty() && !startNewNumber) {
                accumulator = calculate(accumulator, parseCurrentValue(), operator);
                currentValue = formatValue(accumulator);
            } else {
                accumulator = parseCurrentValue();
            }
            operator = nextOperator;
            startNewNumber = true;
        }

        private void computeResult() {
            if (operator.isEmpty()) {
                return;
            }
            accumulator = calculate(accumulator, parseCurrentValue(), operator);
            currentValue = formatValue(accumulator);
            operator = "";
            startNewNumber = true;
        }

        private void appendDot() {
            if (startNewNumber) {
                currentValue = "0.";
                startNewNumber = false;
                return;
            }
            if (!currentValue.contains(".")) {
                currentValue += ".";
            }
        }

        private void appendDigit(String digit) {
            if (startNewNumber) {
                currentValue = digit;
                startNewNumber = false;
                return;
            }
            if (currentValue.equals("0")) {
                currentValue = digit;
            } else {
                currentValue += digit;
            }
        }

        private double parseCurrentValue() {
            try {
                return Double.parseDouble(currentValue);
            } catch (NumberFormatException e) {
                return 0.0;
            }
        }

        private double calculate(double left, double right, String op) {
            switch (op) {
                case "+":
                    return left + right;
                case "-":
                    return left - right;
                case "*":
                    return left * right;
                case "/":
                    return right == 0 ? 0 : left / right;
                default:
                    return right;
            }
        }

        private String formatValue(double value) {
            if (value == (long) value) {
                return String.format("%d", (long) value);
            }
            String text = String.format("%.8f", value);
            return text.replaceFirst("\\.0+$", "").replaceFirst("(\\.[0-9]*?)0+$", "$1");
        }

        private void updateDisplay() {
            display.setText(currentValue);
        }
    }
}
