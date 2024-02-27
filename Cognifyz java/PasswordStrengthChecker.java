import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

 class PasswordStrengthChecker extends JFrame {

    private JTextField passwordField;
    private JTextArea resultArea;

    public PasswordStrengthChecker() {
        setTitle("Password Strength Checker");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        passwordField = new JTextField();
        JButton checkButton = new JButton("Check Strength");
        resultArea = new JTextArea();
        resultArea.setEditable(false);

        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkPasswordStrength();
            }
        });

        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.add(new JLabel("Enter Password:"));
        panel.add(passwordField);
        panel.add(checkButton);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void checkPasswordStrength() {
        String password = passwordField.getText();
        int length = password.length();
        boolean hasUppercase = !password.equals(password.toLowerCase());
        boolean hasLowercase = !password.equals(password.toUpperCase());
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecialChar = !password.matches("[a-zA-Z0-9]*");

        StringBuilder result = new StringBuilder("Password Strength Analysis:\n");
        result.append("Length: ").append(length >= 8 ? "✓" : "✗").append("\n");
        result.append("Uppercase Letter: ").append(hasUppercase ? "✓" : "✗").append("\n");
        result.append("Lowercase Letter: ").append(hasLowercase ? "✓" : "✗").append("\n");
        result.append("Digit: ").append(hasDigit ? "✓" : "✗").append("\n");
        result.append("Special Character: ").append(hasSpecialChar ? "✓" : "✗").append("\n");

        int score = calculatePasswordScore(length, hasUppercase, hasLowercase, hasDigit, hasSpecialChar);
        result.append("\nPassword Score: ").append(score).append(" out of 5");

        resultArea.setText(result.toString());
    }

    private int calculatePasswordScore(int length, boolean hasUppercase, boolean hasLowercase, boolean hasDigit, boolean hasSpecialChar) {
        int score = 0;

        if (length >= 8) score++;
        if (hasUppercase) score++;
        if (hasLowercase) score++;
        if (hasDigit) score++;
        if (hasSpecialChar) score++;

        return score;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PasswordStrengthChecker());
    }
}
