package schoolmanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGui {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> userTypeBox;

    public LoginGui() {
        frame = new JFrame("University Management System - Login");
        frame.setSize(750, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel backgroundLabel = new JLabel(new ImageIcon("C:\\Users\\Austin M\\OneDrive\\Pictures\\Saved Pictures\\UNION UNIVERSITY.jpg"));
        backgroundLabel.setLayout(new GridBagLayout());


        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        loginPanel.setBackground(new Color(200, 200, 180, 180));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;


        JLabel titleLabel = new JLabel("University Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 102, 204));

        JLabel userTypeLabel = new JLabel("Select Role:");
        userTypeBox = new JComboBox<>(new String[]{"Admin","Student"});

        JLabel userLabel = new JLabel("Username:");
        usernameField = new JTextField(15);

        JLabel passLabel = new JLabel("Password:");
        passwordField = new JPasswordField(15);

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(0, 153, 76));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));


        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; loginPanel.add(titleLabel, gbc);
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1; loginPanel.add(userTypeLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; loginPanel.add(userTypeBox, gbc);
        gbc.gridx = 0; gbc.gridy = 2; loginPanel.add(userLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; loginPanel.add(usernameField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; loginPanel.add(passLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3; loginPanel.add(passwordField, gbc);
        gbc.gridx = 1; gbc.gridy = 4; loginPanel.add(loginButton, gbc);

        backgroundLabel.add(loginPanel);
        frame.add(backgroundLabel, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String userType = (String) userTypeBox.getSelectedItem();

        if (username.equals("admin") && password.equals("admin123") && userType.equals("Admin")) {
            JOptionPane.showMessageDialog(frame, "Admin Login Successful!");
            new Admin ();
            frame.dispose();
        } else if (username.equals("student") && password.equals("student123") && userType.equals("Student")) {
            JOptionPane.showMessageDialog(frame, "Student Login Successful!");
            new StudentDashboard();
            frame.dispose();
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid Credentials!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new LoginGui();
    }
}
