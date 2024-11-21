package UILayer;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private MenuBar menuBar;
    private Canvas canvas;
    private PropertiesPanel propertiesPanel;
    private ComponentPalette componentPalette;

    public MainWindow() {
        // Setting up the main window
        setTitle("UML Diagram Designer");
        setSize(1000, 700);  // Larger window for better visual
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));  // Improved spacing

        // Initialize components
        menuBar = new MenuBar();
        canvas = new Canvas();
        propertiesPanel = new PropertiesPanel();
        componentPalette = new ComponentPalette();

        // Set the menu bar
        setJMenuBar(menuBar);

        // Add components with improved layout
        add(canvas, BorderLayout.CENTER);  // Add Canvas
        add(propertiesPanel, BorderLayout.EAST);  // Add Properties Panel
        add(componentPalette, BorderLayout.WEST);  // Add Component Palette

        // Adding borders for better separation
        propertiesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        componentPalette.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    public void display() {
        setVisible(true);
    }

    public void refresh() {
        // Refresh logic
    }

    public void close() {
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainWindow window = new MainWindow();
            window.display();
        });
    }
}
