package Utilities.Component;

import BusinessLogic.ClassDiagram.Components.Relations.Aggregation;
import BusinessLogic.ClassDiagram.Components.Relations.Association;
import BusinessLogic.ClassDiagram.Components.Relations.Composition;
import BusinessLogic.ClassDiagram.Components.Relations.Inheritance;
import Utilities.Diagram.Diagram;
import Utilities.Property.CoordinateProperty;
import Utilities.Property.Property;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "relationComponentType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Aggregation.class, name = "Aggregation"),
        @JsonSubTypes.Type(value = Association.class, name = "Association"),
        @JsonSubTypes.Type(value = Composition.class, name = "Composition"),
        @JsonSubTypes.Type(value = Inheritance.class, name = "Inheritance")
})
public abstract class RelationComponent extends Utilities.Component.Component {

    protected CoordinateProperty x_coordinate_2;
    protected CoordinateProperty y_coordinate_2;

    protected Utilities.Component.Component source;
    protected Component target;

    public RelationComponent()
    {
        super();
    }


    public RelationComponent(Diagram diagram) {
        super(diagram);
        x_coordinate_2 = new CoordinateProperty("X Coordinate 2",0,this);
        properties.add(x_coordinate_2);
        y_coordinate_2 = new CoordinateProperty("Y Coordinate 2",0,this);
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

    public void setSource(Utilities.Component.Component component) {
        this.source = component;
    }

    public void setTarget(Utilities.Component.Component component) {
        this.target = component;
    }

    public Utilities.Component.Component getSource() {
        return source;
    }

    public Component getTarget() {
        return target;
    }
}