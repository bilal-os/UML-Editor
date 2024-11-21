package UILayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;

public class Canvas extends JPanel {
    // Section Header: Diagram Canvas
    private static final String CANVAS_HEADER = "Diagram Canvas";

    private float zoomLevel = 1.0f;
    private static final int GRID_SIZE = 20; // Size of grid squares
    private static final Color GRID_COLOR = new Color(240, 240, 240); // Light gray grid
    private static final Color GRID_LINE_COLOR = new Color(230, 230, 230); // Slightly darker lines

    public Canvas() {
        // Enhanced layout and appearance
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(800, 600));  // Increased default size

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(new Color(240, 240, 240));
        JLabel titleLabel = new JLabel("Diagram Canvas");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titlePanel.add(titleLabel);
        titlePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));


        add(titlePanel, BorderLayout.NORTH);

        // Add mouse listener for interaction
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Handle mouse click events for drawing
            }
        });

        // Set a more pronounced border
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2)
        ));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Enable antialiasing for smoother rendering
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw grid background
        drawGrid(g2d);

        // Apply zoom
        g2d.scale(zoomLevel, zoomLevel);

        // Logic to draw diagram components (e.g., UML classes)
        // Additional drawing logic can be added here
    }

    // Method to draw grid background
    private void drawGrid(Graphics2D g2d) {
        // Set grid line color and stroke
        g2d.setColor(GRID_LINE_COLOR);
        g2d.setStroke(new BasicStroke(1.0f));

        // Get component dimensions
        int width = getWidth();
        int height = getHeight();

        // Draw vertical grid lines
        for (int x = 0; x < width; x += GRID_SIZE) {
            g2d.draw(new Line2D.Double(x, 0, x, height));
        }

        // Draw horizontal grid lines
        for (int y = 0; y < height; y += GRID_SIZE) {
            g2d.draw(new Line2D.Double(0, y, width, y));
        }
    }

    // Method to zoom in on the canvas
    public void zoomIn() {
        zoomLevel = Math.min(zoomLevel + 0.1f, 2.0f); // Limit max zoom
        repaint();
    }

    // Method to zoom out on the canvas
    public void zoomOut() {
        zoomLevel = Math.max(zoomLevel - 0.1f, 0.5f); // Limit min zoom
        repaint();
    }

    // Method to clear the canvas
    public void clear() {
        // Reset zoom level
        zoomLevel = 1.0f;
        repaint();
    }
}