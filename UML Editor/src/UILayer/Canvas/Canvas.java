package UILayer.Canvas;

import Utilities.Component.Component;
import Utilities.Component.ComponentObserver;
import Utilities.Diagram.Diagram;
import Utilities.Diagram.DiagramObserver;
import Utilities.Property.Property;
import Utilities.Property.PropertyObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.Optional;

public class Canvas extends JPanel implements DiagramObserver, ComponentObserver, PropertyObserver {
    // Configuration constants
    private static final ZoomSettings ZOOM_SETTINGS = new ZoomSettings(
            1.0f,   // Default zoom
            2.0f,   // Max zoom
            0.5f,   // Min zoom
            0.1f    // Zoom step
    );
    private static final CanvasSettings CANVAS_SETTINGS = new CanvasSettings(
            Color.WHITE,     // Background color
            new Dimension(800, 600),  // Preferred size
            20,              // Grid size
            new Color(240, 240, 240)  // Grid color
    );
    private static final ComponentRenderSettings COMPONENT_SETTINGS = new ComponentRenderSettings(
            180,    // Component width
            215     // Component height
    );

    // State variables
    private float zoomLevel;
    private Diagram diagram;
    private Point dragStart;
    private Component selectedComponent;
    private ActionListener propertiesPanelListener;

    // Constructor
    public Canvas() {
        initializeCanvas();
        setupInteractionListeners();
    }

    // Canvas initialization
    private void initializeCanvas() {
        setBackground(CANVAS_SETTINGS.backgroundColor);
        setPreferredSize(CANVAS_SETTINGS.preferredSize);
        setLayout(null);
        zoomLevel = ZOOM_SETTINGS.defaultZoom;
    }

    public Diagram getDiagram()
    {
        return diagram;
    }

    // Interaction listeners setup
    private void setupInteractionListeners() {
        addMouseListener(new ComponentDragHandler());
        addMouseMotionListener(new ComponentDragMotionHandler());
    }

    // Mouse interaction handlers
    private class ComponentDragHandler extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            Point adjustedPoint = adjustPointForZoom(e.getPoint());
            selectedComponent = findComponentAtPoint(adjustedPoint);

            if (selectedComponent != null) {
                notifyPropertiesPanel(selectedComponent);
                dragStart = e.getPoint();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            dragStart = null;
        }
    }

    private class ComponentDragMotionHandler extends MouseMotionAdapter {
        @Override
        public void mouseDragged(MouseEvent e) {
            if (selectedComponent != null && dragStart != null) {
                updateComponentPosition(e);
                dragStart = e.getPoint();
                repaint();
            }
        }
    }

    // Point adjustment for zoom
    private Point adjustPointForZoom(Point point) {
        return new Point(
                (int)(point.x / zoomLevel),
                (int)(point.y / zoomLevel)
        );
    }

    // Component finding and positioning
    private Component findComponentAtPoint(Point point) {
        return Optional.ofNullable(diagram)
                .map(d -> d.getComponents().stream()
                        .filter(component -> isPointWithinComponentBounds(point, component))
                        .findFirst()
                        .orElse(null))
                .orElse(null);
    }

    private boolean isPointWithinComponentBounds(Point point, Component component) {
        int x = Integer.parseInt(component.getXCoordinate().getValue());
        int y = Integer.parseInt(component.getYCoordinate().getValue());

        return point.x >= x && point.x <= x + COMPONENT_SETTINGS.width &&
                point.y >= y && point.y <= y + COMPONENT_SETTINGS.height;
    }

    private void updateComponentPosition(MouseEvent e) {
        if (diagram == null || selectedComponent == null) return;

        int dx = (int) ((e.getX() - dragStart.x) / zoomLevel);
        int dy = (int) ((e.getY() - dragStart.y) / zoomLevel);

        int currentX = Integer.parseInt(selectedComponent.getXCoordinate().getValue());
        int currentY = Integer.parseInt(selectedComponent.getYCoordinate().getValue());

        int newX = constrainCoordinate(
                currentX + dx,
                0,
                getWidth() / (int)zoomLevel - COMPONENT_SETTINGS.width
        );
        int newY = constrainCoordinate(
                currentY + dy,
                0,
                getHeight() / (int)zoomLevel - COMPONENT_SETTINGS.height
        );

        selectedComponent.getXCoordinate().setValue(String.valueOf(newX));
        selectedComponent.getYCoordinate().setValue(String.valueOf(newY));
    }

    private int constrainCoordinate(int value, int min, int max) {
        return Math.max(min, Math.min(value, max));
    }

    // Rendering methods
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        renderCanvasWithZoom((Graphics2D) g);
    }

    private void renderCanvasWithZoom(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        AffineTransform originalTransform = g2d.getTransform();
        g2d.scale(zoomLevel, zoomLevel);

        drawGrid(g2d);
        renderDiagramComponents(g2d);

        g2d.setTransform(originalTransform);
    }

    private void drawGrid(Graphics2D g2d) {
        g2d.setColor(CANVAS_SETTINGS.gridColor);
        int scaledGridSize = (int)(CANVAS_SETTINGS.gridSize * zoomLevel);

        for (int x = 0; x < getWidth(); x += scaledGridSize) {
            g2d.drawLine(x, 0, x, getHeight());
        }
        for (int y = 0; y < getHeight(); y += scaledGridSize) {
            g2d.drawLine(0, y, getWidth(), y);
        }
    }

    private void renderDiagramComponents(Graphics2D g2d) {
        if (diagram != null) {
            diagram.renderDiagram(g2d);
        }
    }

    // Zoom management
    public void zoomIn() {
        adjustZoom(ZOOM_SETTINGS.zoomStep);
    }

    public void zoomOut() {
        adjustZoom(-ZOOM_SETTINGS.zoomStep);
    }

    public void resetZoom() {
        zoomLevel = ZOOM_SETTINGS.defaultZoom;
        repaint();
    }

    private void adjustZoom(float delta) {
        zoomLevel = constrainZoom(zoomLevel + delta);
        revalidate();
        repaint();
    }

    private float constrainZoom(float zoom) {
        return Math.max(ZOOM_SETTINGS.minZoom,
                Math.min(zoom, ZOOM_SETTINGS.maxZoom));
    }

    // Diagram and Observer Management
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
            for(Property property: component.getProperties()) {
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

    // Properties Panel Notification
    public void addActionListeners(ActionListener propertiesPanelListener) {
        this.propertiesPanelListener = propertiesPanelListener;
    }

    private void notifyPropertiesPanel(Component component) {
        if (propertiesPanelListener != null) {
            propertiesPanelListener.actionPerformed(
                    new ActionEvent(component, ActionEvent.ACTION_PERFORMED, "showProperties")
            );
        }
    }

    // Observer Update Methods
    @Override
    public void updateFromComponent(Component component) {
        component.getProperties().getLast().addObserver(this);
        repaint();
    }

    @Override
    public void updateFromDiagram() {
        diagram.getComponents().getLast().addObserver(this);
        for(Property property: diagram.getComponents().getLast().getProperties()) {
            property.addObserver(this);
        }
        repaint();
    }

    @Override
    public void updateFromProperty() {
        repaint();
    }

    // Configuration Record Classes
    private record ZoomSettings(
            float defaultZoom,
            float maxZoom,
            float minZoom,
            float zoomStep
    ) {}

    private record CanvasSettings(
            Color backgroundColor,
            Dimension preferredSize,
            int gridSize,
            Color gridColor
    ) {}

    private record ComponentRenderSettings(
            int width,
            int height
    ) {}
}