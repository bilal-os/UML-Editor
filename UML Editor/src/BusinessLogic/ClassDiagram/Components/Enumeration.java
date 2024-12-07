package BusinessLogic.ClassDiagram.Components;

import BusinessLogic.ClassDiagram.Properties.EnumerationProperty;
import Utilities.Component.Component;
import Utilities.Diagram.Diagram;
import Utilities.Property.CoordinateProperty;
import Utilities.Property.Property;

import java.awt.*;

import static java.lang.Integer.parseInt;

public class Enumeration extends Component {
    private CoordinateProperty width;
    private CoordinateProperty height;

    public Enumeration()
    {
        super();
    }


    public Enumeration(String name, Diagram diagram) {
        super(diagram);

        // Define valid property types for an enumeration
        propertiesTypes.add("Value");

        // Add default name and dimension properties
        properties.add(new EnumerationProperty("Enumeration Name", name, this));
        width = new CoordinateProperty("Width", 180, this);
        height = new CoordinateProperty("Height", 215, this);
        properties.add(width);
        properties.add(height);
    }

    @Override
    public Property createProperty(String type, String value) throws IllegalArgumentException {
        if (!propertiesTypes.contains(type)) {
            throw new IllegalArgumentException("Invalid property type for Enumeration: " + type);
        }
        EnumerationProperty property = new EnumerationProperty(type, value, this);
        properties.add(property);
        return property;
    }

    @Override
    public void renderComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int x = parseInt(x_coordinate.getValue());
        int y = parseInt(y_coordinate.getValue());
        int width = parseInt(this.width.getValue());
        int height = parseInt(this.height.getValue());

        // Draw outer box
        g2d.setColor(new Color(245, 245, 245));
        g2d.fillRect(x, y, width, height);

        // Draw sections
        drawHeader(g2d, x, y, width);
        drawValues(g2d, x, y + 35, width, height - 35);

        // Draw border
        g2d.setColor(Color.GRAY);
        g2d.drawRect(x, y, width, height);
    }

    private void drawHeader(Graphics2D g2d, int x, int y, int width) {
        g2d.setColor(new Color(220, 220, 250));
        g2d.fillRect(x, y, width, 35);
        g2d.setColor(Color.BLACK);
        g2d.drawString(getName(), x + 10, y + 22);
    }

    private void drawValues(Graphics2D g2d, int x, int y, int width, int height) {
        g2d.setColor(Color.WHITE);
        g2d.fillRect(x, y, width, height);

        g2d.setColor(Color.BLACK);
        int textY = y + 15;
        for (Property value : properties) {
            if (value.gettype().equals("Value")) {
                g2d.drawString(value.getValue(), x + 10, textY);
                textY += 15;
            }
        }
    }

    public Property getWidth() {
        return width;
    }

    public Property getHeight() {
        return height;
    }
}