package BusinessLogic;

import Utilities.Component;
import Utilities.Property;

import java.awt.*;

public class Class extends Component {


    public Class(String name) {

        super(name);

        propertiesTypes.add("Attribute");
        propertiesTypes.add("Method");

        // Add default property
        properties.add(new ClassProperty("Class Name", name));
    }


    @Override
    public void addProperty(String type, String value) throws IllegalArgumentException {
        // Ensure property type is valid
        if (!propertiesTypes.contains(type)) {
            throw new IllegalArgumentException("Invalid property type: " + type);
        }

        // Create a new ClassProperty object with validation
        ClassProperty property = new ClassProperty(type, value);

        // Add the validated property to the properties list
        properties.add(property);
    }

    @Override
    public void renderComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Enable antialiasing for smoother rendering
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Define dimensions and position of the class box
        x_coordinate = 50; // Example x-coordinate
        y_coordinate = 50; // Example y-coordinate
        int width = 150; // Width of the class box
        int headerHeight = 30; // Height for the class name header
        int sectionHeight = 80; // Height for attributes and methods

        // Draw the outer rectangle for the class
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x_coordinate, y_coordinate, width, headerHeight + 2 * sectionHeight);

        // Draw the header (class name section)
        g2d.setColor(new Color(200, 200, 255)); // Light blue background for header
        g2d.fillRect(x_coordinate, y_coordinate, width, headerHeight);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x_coordinate, y_coordinate, width, headerHeight);

        // Draw the class name
        String className = "";

        for (Property property : properties) {
            if (property.gettype().contains("Name")) {
                className = property.getValue();
            }
        }

        // Assuming first property is the class name
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawString(className, x_coordinate + 10, y_coordinate + 20);

        // Draw the attributes section
        int attributesY = y_coordinate + headerHeight;
        g2d.setColor(Color.WHITE);
        g2d.fillRect(x_coordinate, attributesY, width, sectionHeight);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x_coordinate, attributesY, width, sectionHeight);

        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        int textY = attributesY + 20; // Start y position for attributes text
        for (Property attribute : properties) {
            if (attribute.gettype().equals("Attribute")) {
                g2d.drawString(attribute.getValue(), x_coordinate + 10, textY);
                textY += 15; // Line spacing
            }
        }

        // Draw the methods section
        int methodsY = attributesY + sectionHeight;
        g2d.setColor(Color.WHITE);
        g2d.fillRect(x_coordinate, methodsY, width, sectionHeight);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x_coordinate, methodsY, width, sectionHeight);

        textY = methodsY + 20; // Start y position for methods text
        for (Property method : properties) {
            if (method.gettype().equals("Method")) {
                g2d.drawString(method.getValue(), x_coordinate + 10, textY);
                textY += 15; // Line spacing
            }
        }
    }

}
