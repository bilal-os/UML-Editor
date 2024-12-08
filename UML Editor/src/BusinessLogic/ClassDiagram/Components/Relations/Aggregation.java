package BusinessLogic.ClassDiagram.Components.Relations;


import BusinessLogic.ClassDiagram.Components.AbstractClass;
import BusinessLogic.ClassDiagram.Components.Class;
import BusinessLogic.ClassDiagram.Components.Interface;
import BusinessLogic.ClassDiagram.Properties.RelationsProperties.AggregationProperty;
import Utilities.Component.Component;
import Utilities.Component.RelationComponent;
import Utilities.Diagram.Diagram;
import Utilities.Property.Property;

import java.awt.*;

public class Aggregation extends RelationComponent {

    public Aggregation()
    {
        super();
    }

    public Aggregation(String name, Diagram diagram) {
        super(diagram);
        properties.add(new AggregationProperty("Aggregation Name", name, this));
        propertiesTypes.add("Source Multiplicity");
        propertiesTypes.add("Target Multiplicity");
    }

    @Override
    public Property createProperty(String type, String value) throws IllegalArgumentException {
        if (!propertiesTypes.contains(type)) {
            throw new IllegalArgumentException("Invalid property type: " + type);
        }

        AggregationProperty property = new AggregationProperty(type, value, this);
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

        // Get target coordinates and dimensions, assuming target can be Class, AbstractClass or Interface
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

        // Draw aggregation diamond
        drawAggregationDiamond(g, start.x, start.y, end.x, end.y);

        // Calculate line angle
        double lineAngle = Math.atan2(end.y - start.y, end.x - start.x);

        // Render name and multiplicities
        renderName(g, start, end, lineAngle, getPropertyValue("Aggregation Name"));
        renderMultiplicity(g, start, lineAngle, getPropertyValue("Source Multiplicity"));
        renderMultiplicity(g, end, lineAngle, getPropertyValue("Target Multiplicity"));
    }

    private void drawAggregationDiamond(Graphics g, int x1, int y1, int x2, int y2) {
        int diamondSize = 12;
        double angle = Math.atan2(y2 - y1, x2 - x1);

        int[] xPoints = new int[4];
        int[] yPoints = new int[4];

        xPoints[0] = x1;
        yPoints[0] = y1;

        xPoints[1] = (int)(x1 - diamondSize * Math.cos(angle + Math.PI/2));
        yPoints[1] = (int)(y1 - diamondSize * Math.sin(angle + Math.PI/2));

        xPoints[2] = (int)(x1 + diamondSize * 2 * Math.cos(angle));
        yPoints[2] = (int)(y1 + diamondSize * 2 * Math.sin(angle));

        xPoints[3] = (int)(x1 - diamondSize * Math.cos(angle - Math.PI/2));
        yPoints[3] = (int)(y1 - diamondSize * Math.sin(angle - Math.PI/2));

        g.drawPolygon(xPoints, yPoints, 4);
    }
}