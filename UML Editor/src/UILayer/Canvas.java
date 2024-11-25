package UILayer;

import Utilities.Diagram;
import Utilities.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Canvas extends JPanel {
    private float zoomLevel = 1.0f;
    private static final int GRID_SIZE = 20;
    private static final Color GRID_COLOR = new Color(240, 240, 240);
    private Diagram diagram; // Diagram object to manage components

    private Point dragStart = null;
    private Component selectedComponent = null;

    public Canvas() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(800, 600));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Check if a component is clicked
                selectedComponent = findDiagramComponentAt((e.getPoint()));
                if (selectedComponent != null) {
                    dragStart = e.getPoint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                dragStart = null;
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (selectedComponent != null && dragStart != null) {
                    int dx = (int) ((e.getX() - dragStart.x) / zoomLevel);
                    int dy = (int) ((e.getY() - dragStart.y) / zoomLevel);

                    // Update component's position
                    int newX = Integer.parseInt(selectedComponent.getProperties().get(0).getValue()) + dx;
                    int newY = Integer.parseInt(selectedComponent.getProperties().get(1).getValue()) + dy;

                    selectedComponent.getProperties().get(0).setValue(String.valueOf(newX));
                    selectedComponent.getProperties().get(1).setValue(String.valueOf(newY));

                    dragStart = e.getPoint();
                    repaint();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Enable antialiasing
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

    public Component findDiagramComponentAt(Point point) {
        if (diagram == null) return null;

        for (Component component : diagram.getComponents()) {
            int x = Integer.parseInt(component.getXCoordinate().getValue());
            int y = Integer.parseInt(component.getYCoordinate().getValue());
            int width = 180; // Assuming fixed width
            int height = 215; // Assuming fixed height

            if (point.x >= x && point.x <= x + width &&
                    point.y >= y && point.y <= y + height) {
                return component;
            }
        }
        return null;
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
