package schoolmanagementsystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.time.LocalDate;

public class Admin extends JFrame {
    private JFrame frame;
    private JPanel panel;

    public Admin() {
        frame = new JFrame("Admin Dashboard");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(153, 0, 51));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setPreferredSize(new Dimension(600, 50));

        panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));
        panel.setBackground(new Color(255, 230, 230));

        // Main dashboard buttons
        JButton manageStudents = new JButton("Manage Students");

        JButton manageAttendance = new JButton("Manage Attendance");
        JButton viewAnalytics = new JButton("View Analytics");
        JButton logout = new JButton("Logout");

        JButton[] buttons = {manageStudents, manageAttendance, viewAnalytics, logout};
        for (JButton button : buttons) {
            button.setFont(new Font("Arial", Font.BOLD, 16));
            button.setBackground(new Color(204, 0, 51));
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            panel.add(button);
        }

        // Show a drop-down (popup) menu on clicking Manage Students.
        manageStudents.addActionListener(e -> showManageStudentsDropdown(e));
        manageAttendance.addActionListener(e -> showManageAttendanceDropdown(e));
        viewAnalytics.addActionListener(e ->
                JOptionPane.showMessageDialog(frame, "Analytics Page (To be implemented)")
        );
        logout.addActionListener(e -> {
            frame.dispose();
            new schoolmanagementsystem.LoginGui(); // Navigate back to login
        });

        frame.add(titleLabel, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }



    private void showManageStudentsDropdown(ActionEvent e) {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem enrollItem = new JMenuItem("Enroll Student");
        JMenuItem removeItem = new JMenuItem("Remove Student");
        JMenuItem viewItem = new JMenuItem("View Students");

        enrollItem.addActionListener(ev -> openEnrollStudentDialog());
        removeItem.addActionListener(ev -> openRemoveStudentWindow());
        viewItem.addActionListener(ev -> showEnrolledStudents());

        popupMenu.add(enrollItem);
        popupMenu.add(removeItem);
        popupMenu.add(viewItem);

        JButton sourceButton = (JButton) e.getSource();
        popupMenu.show(sourceButton, sourceButton.getWidth() / 2, sourceButton.getHeight() / 2);
    }


    private void openEnrollStudentDialog() {
        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();
        JTextField sexField = new JTextField();
        JTextField dobField = new JTextField(); // Format: YYYY-MM-DD
        JTextField emailField = new JTextField();
        JTextField facultyIdField = new JTextField();
        JTextField courseIdField = new JTextField();
        JTextField noOfUnitsField = new JTextField();
        JTextField ageField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(9, 2, 5, 5));
        panel.add(new JLabel("First Name:"));
        panel.add(firstNameField);
        panel.add(new JLabel("Last Name:"));
        panel.add(lastNameField);
        panel.add(new JLabel("Sex:"));
        panel.add(sexField);
        panel.add(new JLabel("DOB (YYYY-MM-DD):"));
        panel.add(dobField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Faculty ID:"));
        panel.add(facultyIdField);
        panel.add(new JLabel("Course ID:"));
        panel.add(courseIdField);
        panel.add(new JLabel("No. of Units:"));
        panel.add(noOfUnitsField);
        panel.add(new JLabel("Age:"));
        panel.add(ageField);

        int result = JOptionPane.showConfirmDialog(frame, panel, "Enroll Student", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String sex = sexField.getText();
                Date dob = Date.valueOf(dobField.getText());
                String email = emailField.getText();
                int facultyId = Integer.parseInt(facultyIdField.getText());
                int courseId = Integer.parseInt(courseIdField.getText());
                int noOfUnits = Integer.parseInt(noOfUnitsField.getText());
                int age = Integer.parseInt(ageField.getText());

                addStudent(firstName, lastName, sex, dob, email, facultyId, courseId, age, noOfUnits);
                JOptionPane.showMessageDialog(frame, "Student enrolled successfully!");
                showEnrolledStudents();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error enrolling student: " + ex.getMessage());
            }
        }
    }


    private void openRemoveStudentWindow() {
        JFrame removeFrame = new JFrame("Remove Student");
        removeFrame.setSize(500, 300);
        removeFrame.setLayout(new BorderLayout());
        removeFrame.setLocationRelativeTo(frame);

        DefaultTableModel model = new DefaultTableModel(
                new String[]{"Student ID", "First Name", "Last Name", "Course ID"}, 0);
        JTable table = new JTable(model);
        refreshStudentTable(model);

        JButton removeSelectedButton = new JButton("Remove Selected");
        removeSelectedButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int studentId = (int) model.getValueAt(selectedRow, 0);
                deleteStudent(studentId);
                JOptionPane.showMessageDialog(removeFrame, "Student removed successfully!");
                refreshStudentTable(model);
            } else {
                JOptionPane.showMessageDialog(removeFrame, "Please select a student to remove.");
            }
        });

        removeFrame.add(new JScrollPane(table), BorderLayout.CENTER);
        removeFrame.add(removeSelectedButton, BorderLayout.SOUTH);
        removeFrame.setVisible(true);
    }


    private void refreshStudentTable(DefaultTableModel model) {
        model.setRowCount(0);
        try (Connection con = JDBconnector.get_connection();
             PreparedStatement stmt = con.prepareStatement(
                     "SELECT studentId, first_name, last_name, courseid FROM student");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("studentId"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getInt("courseid")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching students data!");
            e.printStackTrace();
        }
    }


    private void showEnrolledStudents() {
        JFrame studentsFrame = new JFrame("View Enrolled Students");
        studentsFrame.setSize(500, 300);
        studentsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        DefaultTableModel model = new DefaultTableModel(
                new String[]{"Student ID", "First Name", "Last Name", "Course ID"}, 0);
        JTable table = new JTable(model);
        refreshStudentTable(model);
        studentsFrame.add(new JScrollPane(table));
        studentsFrame.setLocationRelativeTo(null);
        studentsFrame.setVisible(true);
    }

    private void showManageAttendanceDropdown(ActionEvent e) {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem markItem = new JMenuItem("Mark Attendance");
        JMenuItem viewItem = new JMenuItem("View Attendance");
        JMenuItem reportItem = new JMenuItem("Student Attendance Report");

        markItem.addActionListener(ev -> openMarkAttendanceDialog());
        viewItem.addActionListener(ev -> openViewAttendanceWindow());
        reportItem.addActionListener(ev -> openStudentAttendanceReportWindow());

        popupMenu.add(markItem);
        popupMenu.add(viewItem);
        popupMenu.add(reportItem);

        JButton sourceButton = (JButton) e.getSource();
        popupMenu.show(sourceButton, sourceButton.getWidth() / 2, sourceButton.getHeight() / 2);
    }

    /**
     * Opens a dialog for enrolling a new student.
     */
    private void openMarkAttendanceDialog() {
        JTextField studentIdField = new JTextField();
        JTextField dateField = new JTextField(LocalDate.now().toString());// Default to current date
        String[] statuses = {"Present", "Absent"};
        JComboBox<String> statusBox = new JComboBox<>(statuses);

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.add(new JLabel("Student ID:"));
        panel.add(studentIdField);
        panel.add(new JLabel("Date (YYYY-MM-DD):"));
        panel.add(dateField);
        panel.add(new JLabel("status:"));
        panel.add(statusBox);

        int result = JOptionPane.showConfirmDialog(frame, panel, "Mark Attendance", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int unitId = Integer.parseInt(studentIdField.getText());
                Date date = Date.valueOf(dateField.getText());
                String status = String.valueOf(statusBox.getSelectedItem());
                int hours = Integer.parseInt(String.valueOf(statusBox.getSelectedItem()));
                boolean is_present = status.equalsIgnoreCase("present");

                hours = status.equalsIgnoreCase("present") ? 1 : 0;
                markAttendance(unitId, date,hours,is_present);
                JOptionPane.showMessageDialog(frame, "Attendance marked successfully!");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error marking attendance: " );
            }
        }
    }

    /**
     * Inserts an attendance record into the database.
     */
    private void markAttendance(int unitId, Date date, int hours, Boolean is_present ) {
        String query = "INSERT INTO attendancedetails(unitId, date, hours,is_present) VALUES(?,?,?,?)";
        try (Connection conn = JDBconnector.get_connection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, unitId);
            stmt.setDate(2, date);
            stmt.setInt(3, hours);
            stmt.setBoolean(4, is_present);

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    /**
     * Opens a window to view all attendance records.
     */
    private void openViewAttendanceWindow() {
        JFrame attendanceFrame = new JFrame("View Attendance");
        attendanceFrame.setSize(600, 300);
        attendanceFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        DefaultTableModel model = new DefaultTableModel(
                new String[]{"Record ID", "Student ID", "Date", "Status"}, 0);
        JTable table = new JTable(model);

        try (Connection con = JDBconnector.get_connection();
             PreparedStatement stmt = con.prepareStatement("SELECT unitId, date,hours, is_present FROM attendancedetails");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("unitId"),
                        rs.getDate("date"),
                        rs.getInt("hours"),
                        rs.getBoolean("is_present")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching attendance data!");
            e.printStackTrace();
        }
        attendanceFrame.add(new JScrollPane(table));
        attendanceFrame.setLocationRelativeTo(null);
        attendanceFrame.setVisible(true);
    }

    /**
     * Opens a window to display a student attendance report.
     * For this example, it aggregates the count of present and absent records per student.
     */
    private void openStudentAttendanceReportWindow() {
        JFrame reportFrame = new JFrame("Student Attendance Report");
        reportFrame.setSize(600, 300);
        reportFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        DefaultTableModel model = new DefaultTableModel(
                new String[]{"Student ID", "Days Present", "Days Absent"}, 0);
        JTable table = new JTable(model);

        String query = "SELECT unitid, " +
                "SUM(CASE WHEN is_present=' Present' THEN 1 ELSE 0 END) AS presentCount, " +
                "SUM(CASE WHEN is_present='Absent' THEN 0 ELSE 1 END) AS absentCount " +
                "FROM attendancedetails GROUP BY unitid";
        try (Connection con = JDBconnector.get_connection();
             PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("unitd"),
                        rs.getDate("date"),
                        rs.getInt("hours"),
                        rs.getBoolean("is_present")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching attendance report data!");
            e.printStackTrace();
            JOptionPane.showMessageDialog(reportFrame, "Error fetching attendance report data!");
        }
        reportFrame.add(new JScrollPane(table));
        reportFrame.setLocationRelativeTo(null);
        reportFrame.setVisible(true);
    }



    public void addStudent(String firstName, String lastName, String sex, Date dob, String email,
                           int facultyId, int courseId, int age, int noOfUnits) {

        String query = "INSERT INTO student(First_Name, Last_Name, sex, DOB, email, facultyId, courseId, no_of_units, Age) " +
                "VALUES(?,?,?,?,?,?,?,?,?)";


        try (Connection conn = JDBconnector.get_connection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, sex);
            stmt.setDate(4, dob);
            stmt.setString(5, email);
            stmt.setInt(6, facultyId);
            stmt.setInt(7, courseId);
            stmt.setInt(8, noOfUnits);
            stmt.setInt(9, age);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }


    public void deleteStudent(int studentId) {
        String query = "DELETE FROM student WHERE studentId = ?";
        try (Connection conn = JDBconnector.get_connection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, studentId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }public static void main(String[] args) {
        // Initialize the database (create table if not exists)
        // JDBconnector.initializeDatabase();
        // Launch the dashboard
        SwingUtilities.invokeLater(() -> new Admin());
    }
}



