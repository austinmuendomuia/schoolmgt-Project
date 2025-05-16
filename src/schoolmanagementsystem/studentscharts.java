package schoolmanagementsystem;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class studentscharts extends JFrame {

    public static JPanel createChartPanel() {
        JFreeChart barChart = ChartFactory.createBarChart(
                "Student Enrollment per Course",
                "Courses",
                "Number of Students",
                fetchCourseEnrollmentData()
        );

        // Customizing the chart
        CategoryPlot plot = barChart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(70, 130, 180)); // Set bar color

        return new ChartPanel(barChart);
    }

    private static DefaultCategoryDataset fetchCourseEnrollmentData() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Updated SQL query using the new attributes.
        String sql = "SELECT course.coursename, COUNT(enrollments.studentid) AS student_count " +
                "FROM enrollments " +
                "JOIN course ON enrollments.courseid = course.courseid " +
                "GROUP BY course.coursename";

        try (Connection con = JDBconnector.get_connection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String courseName = rs.getString("coursename");
                int studentCount = rs.getInt("student_count");
                dataset.addValue(studentCount, "Students", courseName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching enrollment data.");
        }

        return dataset;
    }

    public static void main(String[] args) {
        // Test GUI to show the chart
        JFrame frame = new JFrame("Course Enrollment Chart");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(createChartPanel());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}


