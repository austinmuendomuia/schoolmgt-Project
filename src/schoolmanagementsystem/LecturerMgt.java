package schoolmanagementsystem;

import javax.swing.*;
import java.awt.*;

public class LecturerMgt {
    private JFrame frame;
    private JPanel panel;

    public LecturerMgt() {
        frame = new JFrame("Lecturers");
        frame.setSize(400, 350);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Lecturers", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(0, 102, 204));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setPreferredSize(new Dimension(400, 50));

        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));
        panel.setBackground(new Color(230, 240, 255));

        JButton addLecturer = new JButton("Add Lecturer");
        JButton deleteLecturer = new JButton("Delete Lecturer");
        JButton viewLecturers = new JButton("View Lecturers");
        JButton goBack = new JButton("Go Back");

        JButton[] buttons = {addLecturer, deleteLecturer, viewLecturers, goBack};
        for (JButton button : buttons) {
            button.setFont(new Font("Arial", Font.BOLD, 14));
            button.setBackground(new Color(0, 153, 204));
            button.setForeground(Color.WHITE);
            panel.add(button);
        }

        goBack.addActionListener(e -> frame.dispose());

        frame.add(titleLabel, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

