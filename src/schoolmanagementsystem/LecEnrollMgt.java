package schoolmanagementsystem;

import javax.swing.*;
import java.awt.*;

public class LecEnrollMgt {
    public LecEnrollMgt() {
        JFrame frame = new JFrame("Enrollment Management");
        frame.setSize(400, 350);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Enrollments", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(0, 102, 204));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setPreferredSize(new Dimension(400, 50));

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBackground(new Color(204, 229, 255));

        JButton enrollStudent = new JButton("Enroll Student");
        JButton removeEnrollment = new JButton("Remove Enrollment");
        JButton viewEnrollments = new JButton("View Enrolled Students");
        JButton goBack = new JButton("Go Back");


        frame.add(titleLabel, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);



        for (JButton button : new JButton[]{enrollStudent, removeEnrollment, viewEnrollments, goBack}) {
            button.setFont(new Font("Arial", Font.BOLD, 14));
            button.setBackground(new Color(0, 76, 153));
            button.setForeground(Color.WHITE);
            panel.add(button);
        }

        panel.add(enrollStudent);
        panel.add(removeEnrollment);
        panel.add(viewEnrollments);
        frame.add(panel);
        frame.setVisible(true);



        frame.add(titleLabel, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

