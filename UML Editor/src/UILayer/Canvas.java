package UILayer;

import Utilities.Diagram;

import javax.swing.*;
import java.awt.*;

public class Canvas extends JPanel {
    private float zoomLevel = 1.0f;
    private static final int GRID_SIZE = 20;
    private static final Color GRID_COLOR = new Color(240, 240, 240);
    private Diagram diagram; // Diagram object to manage components

    public Canvas() {
        // Pass diagram for rendering
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(800, 600));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Enable antialiasing for smoother rendering
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Apply zoom
        g2d.scale(zoomLevel, zoomLevel);

        // Draw grid
        drawGrid(g2d);

        // Render diagram and its components
        if (diagram != null) {
            diagram.renderDiagram(g2d);
        }
    }

    private void drawGrid(Graphics2D g2d) {
        int width = getWidth();
        int height = getHeight();

        g2d.setColor(GRID_COLOR);
        for (int x = 0; x < width; x += GRID_SIZE) {
            g2d.drawLine(x, 0, x, height);
        }
        for (int y = 0; y < height; y += GRID_SIZE) {
            g2d.drawLine(0, y, width, y);
        }
    }

    public void zoomIn() {
        zoomLevel = Math.min(zoomLevel + 0.1f, 2.0f);
        repaint();
    }

    public void zoomOut() {
        zoomLevel = Math.max(zoomLevel - 0.1f, 0.5f);
        repaint();
    }

    public void setDiagram(Diagram diagram) {
        this.diagram = diagram;
        repaint();
    }
}
