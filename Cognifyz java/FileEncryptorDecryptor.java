import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;

public class FileEncryptorDecryptor extends JFrame {

    private JTextField keyField;
    private JTextArea logArea;

    public FileEncryptorDecryptor() {
        setTitle("File Encryptor/Decryptor");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        keyField = new JTextField();
        JButton encryptButton = new JButton("Encrypt");
        JButton decryptButton = new JButton("Decrypt");
        logArea = new JTextArea();
        logArea.setEditable(false);

        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                encryptFile();
            }
        });

        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                decryptFile();
            }
        });

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Enter Secret Key:"));
        panel.add(keyField);
        panel.add(encryptButton);
        panel.add(decryptButton);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(logArea), BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void encryptFile() {
        try {
            String key = keyField.getText();
            if (key.isEmpty()) {
                logArea.append("Please enter a secret key.\n");
                return;
            }

            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File inputFile = fileChooser.getSelectedFile();
                byte[] inputBytes = Files.readAllBytes(inputFile.toPath());
                byte[] encryptedBytes = cipher.doFinal(inputBytes);

                File outputFile = new File(inputFile.getParent(), "encrypted_" + inputFile.getName());
                Files.write(outputFile.toPath(), encryptedBytes);
                logArea.append("File encrypted successfully.\n");
            }
        } catch (Exception ex) {
            logArea.append("Error during encryption: " + ex.getMessage() + "\n");
            ex.printStackTrace();
        }
    }

    private void decryptFile() {
        try {
            String key = keyField.getText();
            if (key.isEmpty()) {
                logArea.append("Please enter a secret key.\n");
                return;
            }

            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File inputFile = fileChooser.getSelectedFile();
                byte[] inputBytes = Files.readAllBytes(inputFile.toPath());
                byte[] decryptedBytes = cipher.doFinal(inputBytes);

                File outputFile = new File(inputFile.getParent(), "decrypted_" + inputFile.getName());
                Files.write(outputFile.toPath(), decryptedBytes);
                logArea.append("File decrypted successfully.\n");
            }
        } catch (Exception ex) {
            logArea.append("Error during decryption: " + ex.getMessage() + "\n");
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FileEncryptorDecryptor());
    }
}
