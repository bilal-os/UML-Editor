package BusinessLogic;

import Utilities.*;
import Utilities.Component;
import jdk.jshell.Diag;

import java.awt.*;

    public class Association extends RelationComponent {

        public Association(String name, Diagram diagram) {
            super(diagram);

            properties.add(new AssociationProperty("Association Name",name, this));
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

            // Cast to Class instances
            Class sourceClass = (Class) source;
            Class targetClass = (Class) target;

            // Get coordinates and dimensions
            int sourceX = Integer.parseInt(sourceClass.getXCoordinate().getValue());
            int sourceY = Integer.parseInt(sourceClass.getYCoordinate().getValue());
            int sourceWidth = Integer.parseInt(sourceClass.getWidth().getValue());
            int sourceHeight = Integer.parseInt(sourceClass.getHeight().getValue());

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

            // Refined arrow drawing
            drawPreciseArrow(g, start.x, start.y, end.x, end.y);
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


        private double pointToLineDistance(int x, int y, int x1, int y1, int x2, int y2) {
            double numerator = Math.abs((y2 - y1) * x - (x2 - x1) * y + x2 * y1 - y2 * x1);
            double denominator = Math.sqrt(Math.pow(y2 - y1, 2) + Math.pow(x2 - x1, 2));
            return numerator / denominator;
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
