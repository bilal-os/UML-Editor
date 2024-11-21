package UILayer;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private MenuBar menuBar;
    private Canvas canvas;
    private PropertiesPanel propertiesPanel;
    private ComponentPalette componentPalette;

    public MainWindow() {
        // Set up the main window with more modern look
        setTitle("UML Diagram Designer");
        setSize(1200, 800);  // Increased window size for better workspace
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Use BorderLayout with increased horizontal and vertical gaps
        setLayout(new BorderLayout(15, 15));

        // Initialize components
        menuBar = new MenuBar();
        canvas = new Canvas();
        propertiesPanel = new PropertiesPanel();
        componentPalette = new ComponentPalette();

        // Set the menu bar
        setJMenuBar(menuBar);

        // Create a custom panel for the main workspace with padding
        JPanel workspacePanel = new JPanel(new BorderLayout(10, 10));
        workspacePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        // Set preferred sizes with more flexible dimensions
        canvas.setPreferredSize(new Dimension(700, 600));
        componentPalette.setPreferredSize(new Dimension(250, 600));
        propertiesPanel.setPreferredSize(new Dimension(350, 600));

        // Enhance visual separation with subtle borders
        canvas.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        // Add components to the workspace panel
        workspacePanel.add(componentPalette, BorderLayout.WEST);
        workspacePanel.add(canvas, BorderLayout.CENTER);
        workspacePanel.add(propertiesPanel, BorderLayout.EAST);

        // Add workspace to main frame
        add(workspacePanel, BorderLayout.CENTER);

        // Optional: Add status bar at the bottom
        JLabel statusBar = new JLabel("Ready", SwingConstants.LEFT);
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        add(statusBar, BorderLayout.SOUTH);
    }

    public void display() {
        // Pack the frame to respect preferred sizes
        pack();
        setVisible(true);
    }

    public void refresh() {
        // Refresh all components
        SwingUtilities.updateComponentTreeUI(this);
    }

    public void close() {
        dispose();
    }

    public static void main(String[] args) {
        // Use system look and feel for a more native appearance
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            MainWindow window = new MainWindow();
            window.display();
        });
    }
}