package schoolmanagementsystem;

import javax.swing.*;
import java.awt.*;

public class FacultyMgt {
    public FacultyMgt() {
        JFrame frame = new JFrame("Faculty");
        frame.setSize(400, 350);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Faculty", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(0, 102, 204));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setPreferredSize(new Dimension(400, 50));

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBackground(new Color(230, 240, 255));

        JButton addFaculty = new JButton("Add Faculty");
        JButton deleteFaculty = new JButton("Delete Faculty");
        JButton viewFaculty = new JButton("View Faculty");
        JButton goBack = new JButton("Go Back");

        for (JButton button : new JButton[]{addFaculty, deleteFaculty, viewFaculty, goBack}) {
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
