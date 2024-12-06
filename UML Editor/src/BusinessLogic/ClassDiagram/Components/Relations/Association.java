package BusinessLogic.ClassDiagram.Components.Relations;

import BusinessLogic.ClassDiagram.Components.Class;
import BusinessLogic.ClassDiagram.Properties.RelationsProperties.AssociationProperty;
import Utilities.Component.Component;
import Utilities.Component.RelationComponent;
import Utilities.Diagram.Diagram;
import Utilities.Property.Property;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

public class Association extends RelationComponent {

        public Association(String name, Diagram diagram) {
            super(diagram);

            properties.add(new AssociationProperty("Association Name",name, this));
            propertiesTypes.add("Source Multiplicity");
            propertiesTypes.add("Target Multiplicity");

        }

        @Override
        public Property createProperty(String type, String value) throws IllegalArgumentException {
            if (!propertiesTypes.contains(type)) {
                throw new IllegalArgumentException("Invalid property type: " + type);
            }

            AssociationProperty property = new AssociationProperty(type,value,this);
            properties.add(property);
            return property;
        }

    @Override
    public void renderComponent(Graphics g) {
        if (source == null || target == null) return;

        // Cast source and target to Class
        BusinessLogic.ClassDiagram.Components.Class sourceClass =
                (BusinessLogic.ClassDiagram.Components.Class) source;
        BusinessLogic.ClassDiagram.Components.Class targetClass =
                (Class) target;

        // Get source coordinates and dimensions
        int sourceX = Integer.parseInt(sourceClass.getXCoordinate().getValue());
        int sourceY = Integer.parseInt(sourceClass.getYCoordinate().getValue());
        int sourceWidth = Integer.parseInt(sourceClass.getWidth().getValue());
        int sourceHeight = Integer.parseInt(sourceClass.getHeight().getValue());

        // Get target coordinates and dimensions
        int targetX = Integer.parseInt(targetClass.getXCoordinate().getValue());
        int targetY = Integer.parseInt(targetClass.getYCoordinate().getValue());
        int targetWidth = Integer.parseInt(targetClass.getWidth().getValue());
        int targetHeight = Integer.parseInt(targetClass.getHeight().getValue());

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

        // Calculate line parameters
        double lineAngle = Math.atan2(end.y - start.y, end.x - start.x);
        double lineLength = Math.sqrt(
                Math.pow(end.x - start.x, 2) + Math.pow(end.y - start.y, 2)
        );

        // Prepare for text rendering
        Graphics2D g2d = (Graphics2D) g;
        FontRenderContext frc = g2d.getFontRenderContext();

        // Render Association Name
        String associationName = getPropertyValue("Association Name");
        if (associationName != null && !associationName.isEmpty()) {
            // Calculate center point
            int centerX = (start.x + end.x) / 2;
            int centerY = (start.y + end.y) / 2;

            // Measure text width to center it precisely
            Rectangle2D textBounds = g.getFont().getStringBounds(associationName, frc);
            int textWidth = (int) textBounds.getWidth();
            int textHeight = (int) textBounds.getHeight();

            // Offset text perpendicular to line
            int nameOffsetX = (int)(Math.cos(lineAngle + Math.PI/2) * (textHeight + 5));
            int nameOffsetY = (int)(Math.sin(lineAngle + Math.PI/2) * (textHeight + 5));

            // Draw centered text
            g.drawString(associationName,
                    centerX - textWidth/2 + nameOffsetX,
                    centerY + nameOffsetY
            );
        }

        // Render Source Multiplicity
        String sourceMultiplicity = getPropertyValue("Source Multiplicity");
        if (sourceMultiplicity != null && !sourceMultiplicity.isEmpty()) {
            // Calculate point exactly at source diagram border
            int sourceMultiX = start.x;
            int sourceMultiY = start.y;

            // Offset perpendicular to line to avoid overlapping with line or diagram
            int offsetX = (int)(Math.cos(lineAngle + Math.PI/2) * 10);
            int offsetY = (int)(Math.sin(lineAngle + Math.PI/2) * 10);

            g.drawString(sourceMultiplicity,
                    sourceMultiX + offsetX,
                    sourceMultiY + offsetY
            );
        }

        // Render Target Multiplicity
        String targetMultiplicity = getPropertyValue("Target Multiplicity");
        if (targetMultiplicity != null && !targetMultiplicity.isEmpty()) {
            // Calculate point exactly before target diagram border
            int targetMultiX = end.x;
            int targetMultiY = end.y;

            // Offset perpendicular to line to avoid overlapping with line or diagram
            int offsetX = (int)(Math.cos(lineAngle + Math.PI/2) * 10);
            int offsetY = (int)(Math.sin(lineAngle + Math.PI/2) * 10);

            g.drawString(targetMultiplicity,
                    targetMultiX + offsetX,
                    targetMultiY + offsetY
            );
        }
    }


    // Helper method to get property value by name
        private String getPropertyValue(String propertyName) {
            for (Property prop : properties) {
                if (prop.gettype().equals(propertyName)) {
                    return prop.getValue();
                }
            }
            return null;
        }

        private Point calculateIntersectionPoint(
                int fromX, int fromY, int fromWidth, int fromHeight,
                int toX, int toY, int toWidth, int toHeight
        ) {
            // Calculate component centers
            int fromCenterX = fromX + fromWidth / 2;
            int fromCenterY = fromY + fromHeight / 2;
            int toCenterX = toX + toWidth / 2;
            int toCenterY = toY + toHeight / 2;

            // Calculate line angle
            double angle = Math.atan2(toCenterY - fromCenterY, toCenterX - fromCenterX);

            // Potential intersection points on rectangle borders
            Point[] intersections = {
                    new Point(fromX, fromCenterY),               // Left side
                    new Point(fromX + fromWidth, fromCenterY),   // Right side
                    new Point(fromCenterX, fromY),               // Top side
                    new Point(fromCenterX, fromY + fromHeight)   // Bottom side
            };

            // Refine intersection point selection based on angle
            Point selectedPoint = null;
            if (angle >= -Math.PI/4 && angle <= Math.PI/4) {
                // Moving right: use right border
                selectedPoint = new Point(fromX + fromWidth, fromCenterY);
            } else if (angle > Math.PI/4 && angle < 3*Math.PI/4) {
                // Moving down: use bottom border
                selectedPoint = new Point(fromCenterX, fromY + fromHeight);
            } else if (angle <= -3*Math.PI/4 || angle >= 3*Math.PI/4) {
                // Moving left: use left border
                selectedPoint = new Point(fromX, fromCenterY);
            } else {
                // Moving up: use top border
                selectedPoint = new Point(fromCenterX, fromY);
            }

            return selectedPoint;
        }

        private void drawPreciseArrow(Graphics g, int x1, int y1, int x2, int y2) {
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

            // Draw filled arrow
            g.fillPolygon(xPoints, yPoints, 3);
        }

    }
