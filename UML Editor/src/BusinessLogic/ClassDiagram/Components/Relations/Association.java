package BusinessLogic.ClassDiagram.Components.Relations;

import BusinessLogic.ClassDiagram.Components.AbstractClass;
import BusinessLogic.ClassDiagram.Components.Class;
import BusinessLogic.ClassDiagram.Components.Interface;
import BusinessLogic.ClassDiagram.Properties.RelationsProperties.AssociationProperty;
import Utilities.Component.RelationComponent;
import Utilities.Diagram.Diagram;
import Utilities.Property.Property;

import java.awt.*;

public class Association extends RelationComponent {

    public Association()
    {
        super();
    }

    public Association(String name, Diagram diagram) {
        super(diagram);
        properties.add(new AssociationProperty("Association Name", name, this));
        propertiesTypes.add("Source Multiplicity");
        propertiesTypes.add("Target Multiplicity");
    }

    @Override
    public Property createProperty(String type, String value) throws IllegalArgumentException {
        if (!propertiesTypes.contains(type)) {
            throw new IllegalArgumentException("Invalid property type: " + type);
        }

        AssociationProperty property = new AssociationProperty(type, value, this);
        properties.add(property);
        return property;
    }

    @Override
    public void renderComponent(Graphics g) {
        if (source == null || target == null) return;

        // Get source coordinates and dimensions, assuming source is either Class or AbstractClass
        int sourceX = 0, sourceY = 0, sourceWidth = 0, sourceHeight = 0;

        if (source instanceof Class) {
            sourceX = Integer.parseInt(((Class) source).getXCoordinate().getValue());
            sourceY = Integer.parseInt(((Class) source).getYCoordinate().getValue());
            sourceWidth = Integer.parseInt(((Class) source).getWidth().getValue());
            sourceHeight = Integer.parseInt(((Class) source).getHeight().getValue());
        } else if (source instanceof AbstractClass) {
            sourceX = Integer.parseInt(((AbstractClass) source).getXCoordinate().getValue());
            sourceY = Integer.parseInt(((AbstractClass) source).getYCoordinate().getValue());
            sourceWidth = Integer.parseInt(((AbstractClass) source).getWidth().getValue());
            sourceHeight = Integer.parseInt(((AbstractClass) source).getHeight().getValue());
        }

        // Get target coordinates and dimensions, assuming target is Class, AbstractClass or Interface
        int targetX = 0, targetY = 0, targetWidth = 0, targetHeight = 0;

        if (target instanceof Class) {
            targetX = Integer.parseInt(((Class) target).getXCoordinate().getValue());
            targetY = Integer.parseInt(((Class) target).getYCoordinate().getValue());
            targetWidth = Integer.parseInt(((Class) target).getWidth().getValue());
            targetHeight = Integer.parseInt(((Class) target).getHeight().getValue());
        } else if (target instanceof AbstractClass) {
            targetX = Integer.parseInt(((AbstractClass) target).getXCoordinate().getValue());
            targetY = Integer.parseInt(((AbstractClass) target).getYCoordinate().getValue());
            targetWidth = Integer.parseInt(((AbstractClass) target).getWidth().getValue());
            targetHeight = Integer.parseInt(((AbstractClass) target).getHeight().getValue());
        } else if (target instanceof Interface) {
            targetX = Integer.parseInt(((Interface) target).getXCoordinate().getValue());
            targetY = Integer.parseInt(((Interface) target).getYCoordinate().getValue());
            targetWidth = Integer.parseInt(((Interface) target).getWidth().getValue());
            targetHeight = Integer.parseInt(((Interface) target).getHeight().getValue());
        }

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

        // Draw precise arrow
        drawPreciseArrow(g, start.x, start.y, end.x, end.y);

        // Render name and multiplicities
        double lineAngle = Math.atan2(end.y - start.y, end.x - start.x);
        renderName(g, start, end, lineAngle, getPropertyValue("Association Name"));
        renderMultiplicity(g, start, lineAngle, getPropertyValue("Source Multiplicity"));
        renderMultiplicity(g, end, lineAngle, getPropertyValue("Target Multiplicity"));
    }

    private void drawPreciseArrow(Graphics g, int x1, int y1, int x2, int y2) {
        int arrowSize = 10;
        double angle = Math.atan2(y2 - y1, x2 - x1);

        int[] xPoints = new int[3];
        int[] yPoints = new int[3];

        xPoints[0] = x2;
        yPoints[0] = y2;

        xPoints[1] = (int)(x2 - arrowSize * Math.cos(angle + Math.PI / 6));
        yPoints[1] = (int)(y2 - arrowSize * Math.sin(angle + Math.PI / 6));
        xPoints[2] = (int)(x2 - arrowSize * Math.cos(angle - Math.PI / 6));
        yPoints[2] = (int)(y2 - arrowSize * Math.sin(angle - Math.PI / 6));

        g.fillPolygon(xPoints, yPoints, 3);
    }
}