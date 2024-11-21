package UILayer;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private MenuBar menuBar;
    private Canvas canvas;
    private PropertiesPanel propertiesPanel;
    private ComponentPalette componentPalette;
    private DiagramsPanel diagramsPanel;
    JPanel workspacePanel;
    JPanel sidePanel;

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
        diagramsPanel = new DiagramsPanel();

        // Set the menu bar
        setJMenuBar(menuBar);

        // Create a custom panel for the main workspace with padding
        workspacePanel = new JPanel(new BorderLayout(10, 10));
        workspacePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        // Create side panel with vertical layout
        sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));

        // Set preferred sizes with more flexible dimensions
        canvas.setPreferredSize(new Dimension(700, 600));
        componentPalette.setPreferredSize(new Dimension(250, 600));

        // Add properties and diagrams panels to side panel
        sidePanel.add(diagramsPanel);
        sidePanel.add(Box.createVerticalStrut(10)); // Add some vertical spacing
        sidePanel.add(componentPalette);

        // Enhance visual separation with subtle borders
        canvas.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        // Add components to the workspace panel
        workspacePanel.add(propertiesPanel, BorderLayout.EAST);
        workspacePanel.add(canvas, BorderLayout.CENTER);
        workspacePanel.add(sidePanel, BorderLayout.WEST);

        // Add workspace to main frame
        add(workspacePanel, BorderLayout.CENTER);

        // Optional: Add status bar at the bottom
        JLabel statusBar = new JLabel("Ready", SwingConstants.LEFT);
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        add(statusBar, BorderLayout.SOUTH);
    }

    public void display() {
        pack();
        setVisible(true);
    }

    public void refresh() {
        SwingUtilities.updateComponentTreeUI(this);
    }

    public void close() {
        dispose();
    }

    public static void main(String[] args) {
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