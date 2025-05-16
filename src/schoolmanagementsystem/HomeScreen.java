package schoolmanagementsystem;

import javax.swing.*;
import java.awt.*;

public class HomeScreen {
    private JFrame frame;
    private JProgressBar progressBar;

    public HomeScreen() {
        frame = new JFrame();
        frame.setUndecorated(false);
        frame.setSize(900, 500);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel background = new JLabel(new ImageIcon("src/resources/university.png"));
        background.setLayout(new BorderLayout());

        JLabel label = new JLabel("Welcome to Union University Management System", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(Color.BLACK);

        // Progress Bar
        progressBar = new JProgressBar();
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.setStringPainted(true);
        progressBar.setForeground(new Color(50, 150, 255)); // Custom color

        // Adding components
        panel.add(label, BorderLayout.NORTH);
        panel.add(progressBar, BorderLayout.SOUTH);
        background.add(panel, BorderLayout.SOUTH);

        frame.setContentPane(background);
        frame.setVisible(true);

        // Load Progress
        loadProgress();
    }

    private void loadProgress() {
        try {
            for (int i = 0; i <= 100; i += 5) {
                Thread.sleep(100);
                progressBar.setValue(i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        frame.dispose();
        new schoolmanagementsystem.LoginGui();
    }

    public static void main(String[] args) {
        new HomeScreen();
    }
}


