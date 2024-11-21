package UILayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Canvas extends JPanel {
    private float zoomLevel = 1.0f;

    public Canvas() {
        // Set background color and preferred size
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(800, 600));  // Fixed size for better layout

        // Add mouse listener for interaction
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Handle mouse click events for drawing
            }
        });

        // Set a border around the canvas to give it a boxed appearance
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));  // 2px gray border
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.scale(zoomLevel, zoomLevel);
        // Logic to draw diagram components (e.g., UML classes)
    }

    // Method to zoom in on the canvas
    public void zoomIn() {
        zoomLevel += 0.1;
        repaint();
    }

    // Method to zoom out on the canvas
    public void zoomOut() {
        zoomLevel -= 0.1;
        repaint();
    }

    // Method to clear the canvas
    public void clear() {
        // Logic to clear canvas
        repaint();
    }
}
