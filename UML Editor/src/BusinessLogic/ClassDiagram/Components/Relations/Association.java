package BusinessLogic.ClassDiagram.Components.Relations;

import BusinessLogic.ClassDiagram.Components.Class;
import BusinessLogic.ClassDiagram.Properties.RelationsProperties.AssociationProperty;
import Utilities.Component.Component;
import Utilities.Component.RelationComponent;
import Utilities.Diagram.Diagram;
import Utilities.Property.Property;

import java.awt.*;

public class Association extends RelationComponent {

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

        Class sourceClass = (Class) source;
        Class targetClass = (Class) target;

        int sourceX = Integer.parseInt(sourceClass.getXCoordinate().getValue());
        int sourceY = Integer.parseInt(sourceClass.getYCoordinate().getValue());
        int sourceWidth = Integer.parseInt(sourceClass.getWidth().getValue());
        int sourceHeight = Integer.parseInt(sourceClass.getHeight().getValue());

        int targetX = Integer.parseInt(targetClass.getXCoordinate().getValue());
        int targetY = Integer.parseInt(targetClass.getYCoordinate().getValue());
        int targetWidth = Integer.parseInt(targetClass.getWidth().getValue());
        int targetHeight = Integer.parseInt(targetClass.getHeight().getValue());

        Point start = calculateIntersectionPoint(
                sourceX, sourceY, sourceWidth, sourceHeight,
                targetX, targetY, targetWidth, targetHeight
        );
        Point end = calculateIntersectionPoint(
                targetX, targetY, targetWidth, targetHeight,
                sourceX, sourceY, sourceWidth, sourceHeight
        );

        g.setColor(Color.BLACK);
        g.drawLine(start.x, start.y, end.x, end.y);

        drawPreciseArrow(g, start.x, start.y, end.x, end.y);

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