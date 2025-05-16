package schoolmanagementsystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class LecturerDashboard {
    private JFrame frame;
    private JPanel panel;
    private int studentId;

    public LecturerDashboard() {
        this.studentId = studentId;
        frame = new JFrame("Lecturer Dashboard");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Lecturer Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(153, 0, 51));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setPreferredSize(new Dimension(600, 50));


        panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 10, 10));
        panel.setBackground(new Color(255, 230, 230));

        JButton manageCourses = new JButton("Manage Students");
        JButton manageEnrollments = new JButton("Manage Enrollments");
        JButton manageAttendance = new JButton("Manage Attendance");
        JButton viewAnalytics = new JButton("View Analytics");
        JButton logout = new JButton("Logout");

        JButton[] buttons = {manageCourses, manageEnrollments, manageAttendance, viewAnalytics, logout};
        for (JButton button : buttons) {
            button.setFont(new Font("Arial", Font.BOLD, 16));
            button.setBackground(new Color(204, 0, 51));
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            panel.add(button);
        }

        manageCourses.addActionListener(e -> new schoolmanagementsystem.LecCourseMgt());
        manageEnrollments.addActionListener(e -> new schoolmanagementsystem.LecEnrollMgt());
        manageAttendance.addActionListener(e -> new schoolmanagementsystem.LecAttendanceMgt());
        viewAnalytics.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Analytics Page (To be implemented)"));
        logout.addActionListener(e -> {
            frame.dispose();
            new schoolmanagementsystem.LoginGui(); // Navigate back to login
        });

        frame.add(titleLabel, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
//    private Object[][] fetchCourseData() {
//        try {
//            //Connection to db
//            Connection connect = null;
//            Statement stmt = null;
//            JDBconnector stdConn = new JDBconnector();
//            connect = stdConn.get_connection();
//            PreparedStatement pstmt = null;
//
//            String query = "select * from lecturer";
//            pstmt = connect.prepareStatement(query);
//            ResultSet rs = pstmt.executeQuery();
//
//            //Helps set number of rows
//            rs.last();
//            int rowCount = rs.getRow();
//            rs.beforeFirst();
//
//            Object[][] data = new Object[rowCount][4];
//            int row = 0;
//
//            while (rs.next()) {
//                data[row][0] = rs.getString("lecId");
//                data[row][1] = rs.getString("courseId");
//                data[row][2] = rs.getDouble("facultyId");
//                data[row][3] = rs.getString("unitId");
//                data[row][4] = rs.getString("First_Name");
//                data[row][5] = rs.getString("Last_Name");
//                data[row][6] = rs.getString("email");
//                data[row][7] = rs.getString("dob");
//                data[row][8] = rs.getString("sex");
//                data[row][9] = rs.getString("salary");
//                row++;
//            }
//            return data;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new Object[0][0];
//        }

    public void deletestudents(int studentId){
        String query="DELETE FROM student WHERE studentId=?";

        try{
            Connection conn = schoolmanagementsystem.JDBconnector.get_connection();
            PreparedStatement statemnt=conn.prepareStatement(query);

            statemnt.setInt(1,studentId);
            statemnt.executeUpdate();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void addstudents(String First_Name,String Last_Name,String sex,Date DOB,String email,int facultyId,int courseId,int age,int no_of_units){
        String query="INSERT INTO student(First_Name,Last_Name,sex,DOB,email,facultyId,courseId,no_of_units,Age) VALUES(?,?,?,?,?,?,?,?,?)";

        try{
            Connection conn = schoolmanagementsystem.JDBconnector.get_connection();
            PreparedStatement statemnt=conn.prepareStatement(query);


            statemnt.setString(1,First_Name);
            statemnt.setString(2,Last_Name);
            statemnt.setInt(3,facultyId);
            statemnt.setString(4,email);
            statemnt.setString(5,sex);
            statemnt.setDate(6,DOB);
            statemnt.setInt(7,courseId);
            statemnt.setInt(8,age);
            statemnt.setInt(9,no_of_units);

            statemnt.executeUpdate();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    private void showEnrolledStudents(){
        JFrame studentsFrame=new JFrame("View Enrolled Students");
        studentsFrame.setSize(500, 300);
        studentsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        DefaultTableModel model = new DefaultTableModel(new String[]{"Student ID","First Name","Last Name", "course ID"}, 0);
        JTable table = new JTable(model);
        try{
            Connection con = schoolmanagementsystem.JDBconnector.get_connection();
            PreparedStatement stmt = con.prepareStatement("SELECT studentId,first_name,last_name,courseid FROM student");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{rs.getInt("studentId"), rs.getString("first_name"), rs.getString("last_name"),rs.getInt("courseid")});
            }
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching students data!");
            e.printStackTrace();
        }
        studentsFrame.add(new JScrollPane(table));
        studentsFrame.setLocationRelativeTo(null);
        studentsFrame.setVisible(true);

    }
    public static void main(String[] args) {
        new LecturerDashboard();
    }
}

