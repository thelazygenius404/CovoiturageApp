import controller.AppController;

import javax.swing.*;
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("CarPool");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);

            new AppController(frame); // Initialiser le contrôleur

            frame.setVisible(true);
        });
    }
}
