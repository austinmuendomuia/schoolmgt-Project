package schoolmanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class LecturerDashboard1 {
    private JFrame frame;
    private int lecturerId; // you can set or pass this value as needed
    private Connection connection; // database connection

    public LecturerDashboard1() {
        // For demonstration purposes, assign a dummy lecturer ID.
        this.lecturerId = 1;

        // Try to establish a connection to the database.
        try {
            connection = schoolmanagementsystem.JDBconnector.get_connection();
            if (connection != null) {
                System.out.println("Connected to the database successfully.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database connection failed: " + e.getMessage(),
                    "Connection Error", JOptionPane.ERROR_MESSAGE);
        }

        frame = new JFrame("Lecturer Dashboard");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Lecturer Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(153, 0, 51));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setPreferredSize(new Dimension(600, 60));

        // Create a panel with a grid layout for the main options.
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBackground(new Color(255, 230, 230));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton manageCourses = createStyledButton("Manage Courses");
        JButton manageEnrollments = createStyledButton("Manage Enrollments");
        JButton manageAttendance = createStyledButton("Manage Attendance");
        JButton viewAnalytics = createStyledButton("View Analytics");
        JButton logout = createStyledButton("Logout");

        // Add buttons to the panel.
        for (JButton button : new JButton[]{manageCourses, manageEnrollments, manageAttendance, viewAnalytics, logout}) {
            panel.add(button);
        }

        // Action Listeners for buttons.
        manageCourses.addActionListener(e -> showManageCoursesDialog());
        manageEnrollments.addActionListener(e ->
                JOptionPane.showMessageDialog(frame, "Manage Enrollments - To be implemented"));
        manageAttendance.addActionListener(e ->
                JOptionPane.showMessageDialog(frame, "Manage Attendance - To be implemented"));
        viewAnalytics.addActionListener(e ->
                JOptionPane.showMessageDialog(frame, "View Analytics - To be implemented"));
        logout.addActionListener(e -> {
            frame.dispose();
            new schoolmanagementsystem.LoginGui(); // Navigate back to login (ensure LoginGui exists)
        });

        frame.add(titleLabel, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Helper method to create uniformly styled buttons.
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(80, 152, 29));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return button;
    }

    // This method creates and shows a modal dialog with inline options for managing courses.
    private void showManageCoursesDialog() {
        JDialog dialog = new JDialog(frame, "Course Management", true);
        dialog.setSize(300, 200);
        dialog.setLayout(new GridLayout(3, 1, 10, 10));
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JButton enrollStudents = new JButton("Enroll Students");
        JButton removeStudents = new JButton("Remove Students");
        JButton viewStudents = new JButton("View Students");

        // Style the inline buttons similarly.
        for (JButton btn : new JButton[]{enrollStudents, removeStudents, viewStudents}) {
            btn.setFont(new Font("Arial", Font.BOLD, 16));
            btn.setBackground(new Color(204, 0, 51));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            dialog.add(btn);
        }

        // Inline actions for each button.
        enrollStudents.addActionListener(e -> {
            System.out.println("Enroll Students button clicked."); // Debug output
            JOptionPane.showMessageDialog(dialog, "Enroll Students functionality to be implemented");
        });
        removeStudents.addActionListener(e -> {
            System.out.println("Remove Students button clicked."); // Debug output
            JOptionPane.showMessageDialog(dialog, "Remove Students functionality to be implemented");
        });
        viewStudents.addActionListener(e -> {
            System.out.println("View Students button clicked."); // Debug output
            JOptionPane.showMessageDialog(dialog, "View Students functionality to be implemented");
        });

        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        new schoolmanagementsystem.LecturerDashboard();
    }
}

