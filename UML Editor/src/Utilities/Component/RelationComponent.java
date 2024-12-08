package Utilities.Component;

import Utilities.Diagram.Diagram;
import Utilities.Property.CoordianteProperty;
import Utilities.Property.Property;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

public abstract class RelationComponent extends Component implements Serializable {

    private static final long serialVersionUID = 1L;

    protected CoordianteProperty x_coordinate_2;
    protected CoordianteProperty y_coordinate_2;

    protected Component source;
    protected Component target;

    public RelationComponent(Diagram diagram) {
        super(diagram);
        x_coordinate_2 = new CoordianteProperty("X Coordinate 2", 0, this);
        y_coordinate_2 = new CoordianteProperty("Y Coordinate 2", 0, this);

        properties.add(x_coordinate_2);
        properties.add(y_coordinate_2);
        propertiesTypes.add("Source");
        propertiesTypes.add("Target");
    }

    public abstract Property createProperty(String type, String value) throws IllegalArgumentException;

    public abstract void renderComponent(Graphics g);

    // Common implementation for getting property values
    protected String getPropertyValue(String propertyName) {
        for (Property prop : properties) {
            if (prop.gettype().equals(propertyName)) {
                return prop.getValue();
            }
        }
        return null;
    }

    // Common method for calculating intersection point
    protected Point calculateIntersectionPoint(
            int fromX, int fromY, int fromWidth, int fromHeight,
            int toX, int toY, int toWidth, int toHeight
    ) {
        int fromCenterX = fromX + fromWidth / 2;
        int fromCenterY = fromY + fromHeight / 2;
        int toCenterX = toX + toWidth / 2;
        int toCenterY = toY + toHeight / 2;

        double angle = Math.atan2(toCenterY - fromCenterY, toCenterX - fromCenterX);

        Point selectedPoint = null;
        if (angle >= -Math.PI/4 && angle <= Math.PI/4) {
            selectedPoint = new Point(fromX + fromWidth, fromCenterY);
        } else if (angle > Math.PI/4 && angle < 3*Math.PI/4) {
            selectedPoint = new Point(fromCenterX, fromY + fromHeight);
        } else if (angle <= -3*Math.PI/4 || angle >= 3*Math.PI/4) {
            selectedPoint = new Point(fromX, fromCenterY);
        } else {
            selectedPoint = new Point(fromCenterX, fromY);
        }

        return selectedPoint;
    }

    // Common method for rendering name
    protected void renderName(Graphics g, Point start, Point end, double lineAngle, String name) {
        if (name == null || name.isEmpty()) return;

        Graphics2D g2d = (Graphics2D) g;
        FontRenderContext frc = g2d.getFontRenderContext();

        int centerX = (start.x + end.x) / 2;
        int centerY = (start.y + end.y) / 2;

        Rectangle2D textBounds = g.getFont().getStringBounds(name, frc);
        int textWidth = (int) textBounds.getWidth();
        int textHeight = (int) textBounds.getHeight();

        int nameOffsetX = (int)(Math.cos(lineAngle + Math.PI/2) * (textHeight + 5));
        int nameOffsetY = (int)(Math.sin(lineAngle + Math.PI/2) * (textHeight + 5));

        g.drawString(name,
                centerX - textWidth/2 + nameOffsetX,
                centerY + nameOffsetY
        );
    }

    // Common method for rendering multiplicity
    protected void renderMultiplicity(Graphics g, Point point, double lineAngle, String multiplicity) {
        if (multiplicity == null || multiplicity.isEmpty()) return;

        int offsetX = (int)(Math.cos(lineAngle + Math.PI/2) * 10);
        int offsetY = (int)(Math.sin(lineAngle + Math.PI/2) * 10);

        g.drawString(multiplicity,
                point.x + offsetX,
                point.y + offsetY
        );
    }

    public void setSource(Component component) {
        this.source = component;
    }

    public void setTarget(Component component) {
        this.target = component;
    }

    public Component getSource() {
        return source;
    }

    public Component getTarget() {
        return target;
    }
}
