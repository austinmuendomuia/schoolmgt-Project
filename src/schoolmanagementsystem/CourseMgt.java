package schoolmanagementsystem;

import javax.swing.*;
import java.awt.*;

public class CourseMgt {
    private JFrame frame;
    private JPanel panel;

    public CourseMgt() {
        frame = new JFrame("Manage Courses");
        frame.setSize(400, 350);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Manage Courses", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(0, 102, 204));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setPreferredSize(new Dimension(400, 50));

        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));
        panel.setBackground(new Color(230, 240, 255));

        JButton addCourse = new JButton("Add Course");
        JButton deleteCourse = new JButton("Delete Course");
        JButton viewCourses = new JButton("View Courses");
        JButton goBack = new JButton("Go Back");

        JButton[] buttons = {addCourse, deleteCourse, viewCourses, goBack};
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

