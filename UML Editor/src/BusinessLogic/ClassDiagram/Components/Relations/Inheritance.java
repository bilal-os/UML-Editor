package BusinessLogic.ClassDiagram.Components.Relations;

import BusinessLogic.ClassDiagram.Components.Class;
import BusinessLogic.ClassDiagram.Components.Interface;
import BusinessLogic.ClassDiagram.Properties.RelationsProperties.InheritanceProperty;
import Utilities.Component.Component;
import Utilities.Component.RelationComponent;
import Utilities.Diagram.Diagram;
import Utilities.Property.Property;

import java.awt.*;

public class Inheritance extends RelationComponent {

    public Inheritance(String name, Diagram diagram) {
        super(diagram);
        properties.add(new InheritanceProperty("Inheritance Name", name, this));
    }

    @Override
    public Property createProperty(String type, String value) throws IllegalArgumentException {
        if (!propertiesTypes.contains(type)) {
            throw new IllegalArgumentException("Invalid property type: " + type);
        }

        InheritanceProperty property = new InheritanceProperty(type, value, this);
        properties.add(property);
        return property;
    }

    @Override
    public void renderComponent(Graphics g) {
        if (source == null || target == null) return;

        // Cast source to Class
        Class sourceClass = (Class) source;

        // Cast target to Interface
        Interface targetInterface = (Interface) target;

        // Get source coordinates and dimensions
        int sourceX = Integer.parseInt(sourceClass.getXCoordinate().getValue());
        int sourceY = Integer.parseInt(sourceClass.getYCoordinate().getValue());
        int sourceWidth = Integer.parseInt(sourceClass.getWidth().getValue());
        int sourceHeight = Integer.parseInt(sourceClass.getHeight().getValue());

        // Get target coordinates and dimensions
        int targetX = Integer.parseInt(targetInterface.getXCoordinate().getValue());
        int targetY = Integer.parseInt(targetInterface.getYCoordinate().getValue());
        int targetWidth = Integer.parseInt(targetInterface.getWidth().getValue());
        int targetHeight = Integer.parseInt(targetInterface.getHeight().getValue());

        // Refined border point calculation
        Point start = calculateIntersectionPoint(
                sourceX, sourceY, sourceWidth, sourceHeight,
                targetX, targetY, targetWidth, targetHeight
        );
        Point end = calculateIntersectionPoint(
                targetX, targetY, targetWidth, targetHeight,
                sourceX, sourceY, sourceWidth, sourceHeight
        );

        // Calculate line angle
        double lineAngle = Math.atan2(end.y - start.y, end.x - start.x);

        // Draw line
        g.setColor(Color.BLACK);
        g.drawLine(start.x, start.y, end.x, end.y);

        // Draw inheritance arrow (hollow triangle)
        drawInheritanceArrow(g, start.x, start.y, end.x, end.y);

        // Render name
        renderName(g, start, end, lineAngle, getPropertyValue("Inheritance Name"));
    }

    private void drawInheritanceArrow(Graphics g, int x1, int y1, int x2, int y2) {
        int arrowSize = 10;
        double angle = Math.atan2(y2 - y1, x2 - x1);

        int[] xPoints = new int[3];
        int[] yPoints = new int[3];

        // Arrowhead tip exactly at end point
        xPoints[0] = x2;
        yPoints[0] = y2;

        // Left and right arrow points
        xPoints[1] = (int)(x2 - arrowSize * Math.cos(angle + Math.PI / 6));
        yPoints[1] = (int)(y2 - arrowSize * Math.sin(angle + Math.PI / 6));
        xPoints[2] = (int)(x2 - arrowSize * Math.cos(angle - Math.PI / 6));
        yPoints[2] = (int)(y2 - arrowSize * Math.sin(angle - Math.PI / 6));

        // Draw hollow triangle (white fill with black border)
        g.setColor(Color.WHITE);
        g.fillPolygon(xPoints, yPoints, 3);
        g.setColor(Color.BLACK);
        g.drawPolygon(xPoints, yPoints, 3);
    }
}