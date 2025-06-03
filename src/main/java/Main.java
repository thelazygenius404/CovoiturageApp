import controllers.AppController;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // Set system look and feel for better appearance
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // If system L&F is not available, use default
            System.err.println("Could not set system look and feel: " + e.getMessage());
        }

        // Enable font anti-aliasing for better text rendering
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("CarPool");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 700); // Slightly larger for homepage
            frame.setLocationRelativeTo(null);
            frame.setMinimumSize(new Dimension(800, 600));

            // Set application icon (optional)
            try {
                // You can add an icon here if you have one
                // ImageIcon icon = new ImageIcon(Main.class.getResource("/icon.png"));
                // frame.setIconImage(icon.getImage());
            } catch (Exception e) {
                // Icon not found, continue without it
            }

            // Initialize the controller (starts with homepage)
            new AppController(frame);

            frame.setVisible(true);
        });
    }
}