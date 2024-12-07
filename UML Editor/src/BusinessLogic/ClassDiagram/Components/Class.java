package BusinessLogic.ClassDiagram.Components;

import BusinessLogic.ClassDiagram.Properties.ClassProperty;
import Utilities.Component.Component;
import Utilities.Property.CoordinateProperty;
import Utilities.Diagram.Diagram;
import Utilities.Property.Property;

import java.awt.*;

import static java.lang.Integer.parseInt;

public class Class extends Component {
    private CoordinateProperty width;
    private CoordinateProperty height;

    public Class()
    {
        super();
    }

    public Class(String name, Diagram diagram) {

        super(diagram);

        propertiesTypes.add("Attribute");
        propertiesTypes.add("Method");

        // Add default property
        properties.add(new ClassProperty("Class Name", name,this));
        width = new CoordinateProperty("Width", 180, this);  // Default width
        properties.add(width);
        height = new CoordinateProperty("Height", 215, this);  // Default height

        properties.add(height);
    }

    @Override
    public Property createProperty(String type, String value) throws IllegalArgumentException {
        // Ensure property type is valid
        if (!propertiesTypes.contains(type)) {
            throw new IllegalArgumentException("Invalid property type: " + type);
        }

        // Create a new ClassProperty object with validation
        ClassProperty property = new ClassProperty(type, value,this);

        // Add the validated property to the properties list
        properties.add(property);
        return property;
    }

    @Override
    public void renderComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Enable antialiasing
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
        drawAttributes(g2d, x, y + 35, width, height / 3);
        drawMethods(g2d, x, y + height * 2 / 3, width, height / 3);

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

    private void drawAttributes(Graphics2D g2d, int x, int y, int width, int height) {
        g2d.setColor(Color.WHITE);
        g2d.fillRect(x, y, width, height);

        g2d.setColor(Color.BLACK);
        int textY = y + 15;
        for (Property attribute : properties) {
            if (attribute.gettype().equals("Attribute")) {
                g2d.drawString(attribute.getValue(), x + 10, textY);
                textY += 15;
            }
        }
    }

    private void drawMethods(Graphics2D g2d, int x, int y, int width, int height) {
        g2d.setColor(Color.WHITE);
        g2d.fillRect(x, y, width, height);

        g2d.setColor(Color.BLACK);
        int textY = y + 15;
        for (Property method : properties) {
            if (method.gettype().equals("Method")) {
                g2d.drawString(method.getValue(), x + 10, textY);
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
