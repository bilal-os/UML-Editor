package UILayer;

import Utilities.*;
import Utilities.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Canvas extends JPanel implements DiagramObserver, ComponentObserver, PropertyObserver {
    private float zoomLevel = 1.0f;
    private static final int GRID_SIZE = 20;
    private static final Color GRID_COLOR = new Color(240, 240, 240);

    private Diagram diagram;
    private Point dragStart = null;
    private Component selectedComponent = null;
    private ActionListener propertiesPanelListener;


    public Canvas() {
        initializeCanvas();
        addEventListeners();
    }

    private void initializeCanvas() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(800, 600));
    }

    private void addEventListeners() {
        addMouseListener(createMouseAdapter());
        addMouseMotionListener(createMouseMotionAdapter());
    }

    private MouseAdapter createMouseAdapter() {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleMousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                dragStart = null;
            }
        };
    }

    private MouseMotionAdapter createMouseMotionAdapter() {
        return new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                handleMouseDragged(e);
            }
        };
    }

    private void handleMousePressed(MouseEvent e) {
        selectedComponent = findDiagramComponentAt(e.getPoint());
        if (selectedComponent != null) {
            notifyPropertiesPanel(selectedComponent);
            dragStart = e.getPoint();
        }
    }

    private void handleMouseDragged(MouseEvent e) {
        if (selectedComponent != null && dragStart != null) {
            updateComponentPosition(e);
            dragStart = e.getPoint();
            repaint();
        }
    }

    private void notifyPropertiesPanel(Component component) {
        if (propertiesPanelListener != null) {
            propertiesPanelListener.actionPerformed(
                    new ActionEvent(component, ActionEvent.ACTION_PERFORMED, "showProperties")
            );
        }
    }

    private void updateComponentPosition(MouseEvent e) {
        int dx = (int) ((e.getX() - dragStart.x) / zoomLevel);
        int dy = (int) ((e.getY() - dragStart.y) / zoomLevel);

        int newX = Integer.parseInt(selectedComponent.getProperties().get(0).getValue()) + dx;
        int newY = Integer.parseInt(selectedComponent.getProperties().get(1).getValue()) + dy;

        selectedComponent.getProperties().get(0).setValue(String.valueOf(newX));
        selectedComponent.getProperties().get(1).setValue(String.valueOf(newY));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        renderCanvas((Graphics2D) g);
    }

    private void renderCanvas(Graphics2D g2d) {
        enableAntialiasing(g2d);
        applyZoom(g2d);
        drawGrid(g2d);

        if (diagram != null) {
            diagram.renderDiagram(g2d);
        }
    }

    private void enableAntialiasing(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    private void applyZoom(Graphics2D g2d) {
        g2d.scale(zoomLevel, zoomLevel);
    }

    private void drawGrid(Graphics2D g2d) {
        g2d.setColor(GRID_COLOR);

        for (int x = 0; x < getWidth(); x += GRID_SIZE) {
            g2d.drawLine(x, 0, x, getHeight());
        }
        for (int y = 0; y < getHeight(); y += GRID_SIZE) {
            g2d.drawLine(0, y, getWidth(), y);
        }
    }

    public Component findDiagramComponentAt(Point point) {
        if (diagram == null) return null;

        for (Component component : diagram.getComponents()) {
            if (isPointWithinComponentBounds(point, component)) {
                return component;
            }
        }
        return null;
    }

    private boolean isPointWithinComponentBounds(Point point, Component component) {
        int x = Integer.parseInt(component.getXCoordinate().getValue());
        int y = Integer.parseInt(component.getYCoordinate().getValue());
        int width = 180;  // Assuming fixed width
        int height = 215; // Assuming fixed height

        return point.x >= x && point.x <= x + width &&
                point.y >= y && point.y <= y + height;
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
        if (this.diagram != null) {
            unregisterObservers();
        }
        this.diagram = diagram;
        registerObservers();
        repaint();
    }


    private void unregisterObservers() {
        this.diagram.removeObserver(this);
        for (Component component : diagram.getComponents()) {
            component.removeObserver(this);
            for(Property property: component.getProperties())
            {
                property.removeObserver(this);
            }
        }
    }

    private void registerObservers() {
        this.diagram.addObserver(this);
        for (Component component : diagram.getComponents()) {
            component.addObserver(this);
            for(Property property : component.getProperties()) {
                property.addObserver(this);
            }
        }
    }

    public void addActionListeners(ActionListener propertiesPanelListener) {
        this.propertiesPanelListener = propertiesPanelListener;
    }

    @Override
    public void updateFromComponent() {

        repaint();
    }

    @Override
    public void updateFromDiagram() {
        diagram.getComponents().getLast().addObserver(this);
        for(Property property: diagram.getComponents().getLast().getProperties())
        {
            property.addObserver(this);
        }
        repaint();
    }

    @Override
    public void updateFromProperty() {
        repaint();
    }
}
