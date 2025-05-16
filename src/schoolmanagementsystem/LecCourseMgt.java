package schoolmanagementsystem;

import javax.swing.*;
import java.awt.*;

public class LecCourseMgt {
    public LecCourseMgt() {
        JFrame frame = new JFrame("Lecturer - Manage Courses");
        frame.setSize(400, 350);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("My Courses", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(153, 0, 51));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setPreferredSize(new Dimension(400, 50));

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBackground(new Color(255, 230, 230));

        JButton enrollStudent = new JButton("Enroll Student");
        JButton removeStudent = new JButton("Remove Student");
        JButton viewStudents = new JButton("View Enrolled Students");
        JButton goBack = new JButton("Go Back");

        for (JButton button : new JButton[]{enrollStudent, removeStudent, viewStudents, goBack}) {
            button.setFont(new Font("Arial", Font.BOLD, 14));
            button.setBackground(new Color(204, 0, 51));
            button.setForeground(Color.WHITE);
            panel.add(button);
        }

        enrollStudent.addActionListener(e -> new LecCourseMgt());
        removeStudent.addActionListener(e -> new LecCourseMgt());
        viewStudents.addActionListener(e -> new LecCourseMgt());
        goBack.addActionListener(e -> frame.dispose());

        frame.add(titleLabel, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame.add(titleLabel, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

