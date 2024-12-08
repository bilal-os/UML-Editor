package BusinessLogic.ClassDiagram.Components.Relations;

import BusinessLogic.ClassDiagram.Components.AbstractClass;
import BusinessLogic.ClassDiagram.Components.Class;
import BusinessLogic.ClassDiagram.Components.Interface;
import BusinessLogic.ClassDiagram.Properties.RelationsProperties.CompositionProperty;
import Utilities.Component.Component;
import Utilities.Component.RelationComponent;
import Utilities.Diagram.Diagram;
import Utilities.Property.Property;

import java.awt.*;

public class Composition extends RelationComponent {
    public Composition(String name, Diagram diagram) {
        super(diagram);
        properties.add(new CompositionProperty("Composition Name", name, this));
        propertiesTypes.add("Source Multiplicity");
        propertiesTypes.add("Target Multiplicity");
    }

    @Override
    public Property createProperty(String type, String value) throws IllegalArgumentException {
        if (!propertiesTypes.contains(type)) {
            throw new IllegalArgumentException("Invalid property type: " + type);
        }

        CompositionProperty property = new CompositionProperty(type, value, this);
        properties.add(property);
        return property;
    }

    @Override
    public void renderComponent(Graphics g) {
        if (source == null || target == null) return;

        // Cast source to Class or AbstractClass
        Class sourceClass = (Class) source;

        // Get source coordinates and dimensions
        int sourceX = Integer.parseInt(sourceClass.getXCoordinate().getValue());
        int sourceY = Integer.parseInt(sourceClass.getYCoordinate().getValue());
        int sourceWidth = Integer.parseInt(sourceClass.getWidth().getValue());
        int sourceHeight = Integer.parseInt(sourceClass.getHeight().getValue());

        // Get target coordinates and dimensions
        Component targetComponent = target;

        int targetX = Integer.parseInt(
                targetComponent instanceof Class ?
                        ((Class)targetComponent).getXCoordinate().getValue() :
                        ((Interface)targetComponent).getXCoordinate().getValue()
        );
        int targetY = Integer.parseInt(
                targetComponent instanceof Class ?
                        ((Class)targetComponent).getYCoordinate().getValue() :
                        ((Interface)targetComponent).getYCoordinate().getValue()
        );
        int targetWidth = Integer.parseInt(
                targetComponent instanceof Class ?
                        ((Class)targetComponent).getWidth().getValue() :
                        ((Interface)targetComponent).getWidth().getValue()
        );
        int targetHeight = Integer.parseInt(
                targetComponent instanceof Class ?
                        ((Class)targetComponent).getHeight().getValue() :
                        ((Interface)targetComponent).getHeight().getValue()
        );

        // Refined border point calculation
        Point start = calculateIntersectionPoint(
                sourceX, sourceY, sourceWidth, sourceHeight,
                targetX, targetY, targetWidth, targetHeight
        );
        Point end = calculateIntersectionPoint(
                targetX, targetY, targetWidth, targetHeight,
                sourceX, sourceY, sourceWidth, sourceHeight
        );

        // Draw line
        g.setColor(Color.BLACK);
        g.drawLine(start.x, start.y, end.x, end.y);

        // Draw composition diamond
        drawCompositionDiamond(g, start.x, start.y, end.x, end.y);

        // Render name and multiplicities
        double lineAngle = Math.atan2(end.y - start.y, end.x - start.x);
        renderName(g, start, end, lineAngle, getPropertyValue("Composition Name"));
        renderMultiplicity(g, start, lineAngle, getPropertyValue("Source Multiplicity"));
        renderMultiplicity(g, end, lineAngle, getPropertyValue("Target Multiplicity"));
    }

    private void drawCompositionDiamond(Graphics g, int x1, int y1, int x2, int y2) {
        int diamondSize = 12;
        double angle = Math.atan2(y2 - y1, x2 - x1);

        // Calculate four points of the diamond
        int[] xPoints = new int[4];
        int[] yPoints = new int[4];

        // Base point at the start of the line (source side)
        xPoints[0] = x1;
        yPoints[0] = y1;

        // Left point of diamond
        xPoints[1] = (int)(x1 - diamondSize * Math.cos(angle + Math.PI/2));
        yPoints[1] = (int)(y1 - diamondSize * Math.sin(angle + Math.PI/2));

        // Far point of diamond (towards target)
        xPoints[2] = (int)(x1 + diamondSize * 2 * Math.cos(angle));
        yPoints[2] = (int)(y1 + diamondSize * 2 * Math.sin(angle));

        // Right point of diamond
        xPoints[3] = (int)(x1 - diamondSize * Math.cos(angle - Math.PI/2));
        yPoints[3] = (int)(y1 - diamondSize * Math.sin(angle - Math.PI/2));

        // Set color and draw (filled diamond for Composition)
        g.setColor(Color.BLACK);
        g.fillPolygon(xPoints, yPoints, 4);
    }
}