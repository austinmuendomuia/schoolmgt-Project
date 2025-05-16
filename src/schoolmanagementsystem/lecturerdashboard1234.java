package schoolmanagementsystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.time.LocalDate;

public class lecturerdashboard1234 {
    private JFrame frame;

    public lecturerdashboard1234() {
        frame = new JFrame("Lecturer Dashboard");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel title = new JLabel("Lecturer Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setOpaque(true);
        title.setBackground(new Color(153, 0, 51));
        title.setForeground(Color.WHITE);
        title.setPreferredSize(new Dimension(600, 50));

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBackground(new Color(255, 230, 230));
        JButton btnStudents = new JButton("Manage Students");
        JButton btnAttendance = new JButton("Manage Attendance");
        JButton btnAnalytics = new JButton("View Analytics");
        JButton btnLogout = new JButton("Logout");
        for (JButton btn : new JButton[]{btnStudents, btnAttendance, btnAnalytics, btnLogout}) {
            btn.setFont(new Font("Arial", Font.BOLD, 16));
            btn.setBackground(new Color(204, 0, 51));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            panel.add(btn);
        }

        btnStudents.addActionListener(e -> showDropdown(e, "student"));
        btnAttendance.addActionListener(e -> showDropdown(e, "attendance"));
        btnAnalytics.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Analytics (TBD)"));
        btnLogout.addActionListener(e -> { frame.dispose(); new schoolmanagementsystem.LoginGui(); });

        frame.add(title, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Generic drop-down method for both student and attendance menus
    private void showDropdown(ActionEvent e, String type) {
        JPopupMenu menu = new JPopupMenu();
        if ("student".equals(type)) {
            JMenuItem enroll = new JMenuItem("Enroll Student");
            JMenuItem remove = new JMenuItem("Remove Student");
            JMenuItem view = new JMenuItem("View Students");
            enroll.addActionListener(ev -> openEnrollStudentDialog());
            remove.addActionListener(ev -> openRemoveStudentDialog());
            view.addActionListener(ev -> openViewStudents());
            menu.add(enroll); menu.add(remove); menu.add(view);
        } else if ("attendance".equals(type)) {
            JMenuItem mark = new JMenuItem("Mark Attendance");
            JMenuItem view = new JMenuItem("View Attendance");
            JMenuItem report = new JMenuItem("Student Attendance Report");
            mark.addActionListener(ev -> openMarkAttendanceDialog());
            view.addActionListener(ev -> openViewAttendanceDialog());
            report.addActionListener(ev -> openAttendanceReportDialog());
            menu.add(mark); menu.add(view); menu.add(report);
        }
        JButton src = (JButton) e.getSource();
        menu.show(src, src.getWidth() / 2, src.getHeight() / 2);
    }

    ////////////// Student Management Methods //////////////
    private void openEnrollStudentDialog() {
        JTextField first = new JTextField(), last = new JTextField(), sex = new JTextField(),
                dob = new JTextField(), email = new JTextField(),
                faculty = new JTextField(), course = new JTextField(), units = new JTextField(), age = new JTextField();
        JPanel p = new JPanel(new GridLayout(9, 2, 5, 5));
        p.add(new JLabel("First Name:")); p.add(first);
        p.add(new JLabel("Last Name:")); p.add(last);
        p.add(new JLabel("Sex:")); p.add(sex);
        p.add(new JLabel("DOB (YYYY-MM-DD):")); p.add(dob);
        p.add(new JLabel("Email:")); p.add(email);
        p.add(new JLabel("Faculty ID:")); p.add(faculty);
        p.add(new JLabel("Course ID:")); p.add(course);
        p.add(new JLabel("No. of Units:")); p.add(units);
        p.add(new JLabel("Age:")); p.add(age);
        if (JOptionPane.showConfirmDialog(frame, p, "Enroll Student", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                addStudent(first.getText(), last.getText(), sex.getText(), Date.valueOf(dob.getText()),
                        email.getText(), Integer.parseInt(faculty.getText()),
                        Integer.parseInt(course.getText()), Integer.parseInt(age.getText()),
                        Integer.parseInt(units.getText()));
                JOptionPane.showMessageDialog(frame, "Student enrolled!");
                openViewStudents();
            } catch (Exception ex) { ex.printStackTrace(); JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage()); }
        }
    }
    private void openRemoveStudentDialog() {
        JFrame f = new JFrame("Remove Student");
        f.setSize(500, 300);
        f.setLayout(new BorderLayout());
        f.setLocationRelativeTo(frame);
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "First", "Last", "Course"}, 0);
        JTable t = new JTable(model);
        refreshStudentTable(model);
        JButton btn = new JButton("Remove Selected");
        btn.addActionListener(e -> {
            int row = t.getSelectedRow();
            if (row != -1) {
                deleteStudent((int) model.getValueAt(row, 0));
                JOptionPane.showMessageDialog(f, "Student removed!");
                refreshStudentTable(model);
            } else {
                JOptionPane.showMessageDialog(f, "Select a student to remove");
            }
        });
        f.add(new JScrollPane(t), BorderLayout.CENTER);
        f.add(btn, BorderLayout.SOUTH);
        f.setVisible(true);
    }
    private void openViewStudents() {
        JFrame f = new JFrame("View Students");
        f.setSize(500, 300);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "First", "Last", "Course"}, 0);
        JTable t = new JTable(model);
        refreshStudentTable(model);
        f.add(new JScrollPane(t));
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
    private void refreshStudentTable(DefaultTableModel model) {
        model.setRowCount(0);
        try (Connection con = schoolmanagementsystem.JDBconnector.get_connection();
             PreparedStatement stmt = con.prepareStatement("SELECT studentId, first_name, last_name, courseid FROM student");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next())
                model.addRow(new Object[]{ rs.getInt("studentId"), rs.getString("first_name"),
                        rs.getString("last_name"), rs.getInt("courseid") });
        } catch (SQLException e) { JOptionPane.showMessageDialog(null, "Error fetching data"); e.printStackTrace(); }
    }
    public void addStudent(String first, String last, String sex, Date dob, String email,
                           int facultyId, int courseId, int age, int units) {
        String q = "INSERT INTO student(First_Name, Last_Name, sex, DOB, email, facultyId, courseId, no_of_units, Age) VALUES(?,?,?,?,?,?,?,?,?)";
        try (Connection con = schoolmanagementsystem.JDBconnector.get_connection();
             PreparedStatement stmt = con.prepareStatement(q)) {
            stmt.setString(1, first);
            stmt.setString(2, last);
            stmt.setString(3, sex);
            stmt.setDate(4, dob);
            stmt.setString(5, email);
            stmt.setInt(6, facultyId);
            stmt.setInt(7, courseId);
            stmt.setInt(8, units);
            stmt.setInt(9, age);
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
    public void deleteStudent(int id) {
        String q = "DELETE FROM student WHERE studentId = ?";
        try (Connection con = schoolmanagementsystem.JDBconnector.get_connection();
             PreparedStatement stmt = con.prepareStatement(q)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
    ////////////// Attendance Methods //////////////
    private void openMarkAttendanceDialog(){
        JTextField sid = new JTextField();
        JTextField date = new JTextField(LocalDate.now().toString());
        JComboBox<String> status = new JComboBox<>(new String[]{"Present", "Absent"});
        JPanel p = new JPanel(new GridLayout(3,2,5,5));
        p.add(new JLabel("Student ID:")); p.add(sid);
        p.add(new JLabel("Date (YYYY-MM-DD):")); p.add(date);
        p.add(new JLabel("Status:")); p.add(status);
        if (JOptionPane.showConfirmDialog(frame, p, "Mark Attendance", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION){
            try {
                markAttendance(Integer.parseInt(sid.getText()), Date.valueOf(date.getText()), (String)status.getSelectedItem());
                JOptionPane.showMessageDialog(frame,"Attendance marked!");
            } catch(Exception ex){ ex.printStackTrace(); JOptionPane.showMessageDialog(frame,"Error: " + ex.getMessage()); }
        }
    }
    private void markAttendance(int id, Date d, String s){
        String q = "INSERT INTO attendance(studentId, date, status) VALUES(?,?,?)";
        try (Connection con = schoolmanagementsystem.JDBconnector.get_connection();
             PreparedStatement stmt = con.prepareStatement(q)){
            stmt.setInt(1, id);
            stmt.setDate(2, d);
            stmt.setString(3, s);
            stmt.executeUpdate();
        } catch(SQLException e){ e.printStackTrace(); }
    }
    private void openViewAttendanceDialog(){
        JFrame f = new JFrame("View Attendance");
        f.setSize(600,300);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        DefaultTableModel model = new DefaultTableModel(new String[]{"Record ID","Student ID","Date","Status"},0);
        JTable t = new JTable(model);
        try (Connection con = schoolmanagementsystem.JDBconnector.get_connection();
             PreparedStatement stmt = con.prepareStatement("SELECT attendanceId, studentId, date, status FROM attendance");
             ResultSet rs = stmt.executeQuery()){
            while(rs.next()){
                model.addRow(new Object[]{ rs.getInt("attendanceId"), rs.getInt("studentId"), rs.getDate("date"), rs.getString("status") });
            }
        } catch(SQLException e){ JOptionPane.showMessageDialog(null,"Error fetching attendance data"); e.printStackTrace(); }
        f.add(new JScrollPane(t));
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
    private void openAttendanceReportDialog(){
        JFrame f = new JFrame("Attendance Report");
        f.setSize(600,300);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        DefaultTableModel model = new DefaultTableModel(new String[]{"Student ID","Days Present","Days Absent"},0);
        JTable t = new JTable(model);
        String q = "SELECT studentId, SUM(CASE WHEN status='Present' THEN 1 ELSE 0 END) AS presentCount, " +
                "SUM(CASE WHEN status='Absent' THEN 1 ELSE 0 END) AS absentCount FROM attendance GROUP BY studentId";
        try (Connection con = schoolmanagementsystem.JDBconnector.get_connection();
             PreparedStatement stmt = con.prepareStatement(q);
             ResultSet rs = stmt.executeQuery()){
            while(rs.next()){
                model.addRow(new Object[]{ rs.getInt("studentId"), rs.getInt("presentCount"), rs.getInt("absentCount") });
            }
        } catch(SQLException e){ JOptionPane.showMessageDialog(null,"Error fetching report data"); e.printStackTrace(); }
        f.add(new JScrollPane(t));
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new schoolmanagementsystem.LecturerDashboard());
    }
}

