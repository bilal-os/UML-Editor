package BusinessLogic.ClassDiagram.Components;

import BusinessLogic.ClassDiagram.Properties.InterfaceProperty;
import Utilities.Component.Component;
import Utilities.Diagram.Diagram;
import Utilities.Property.Property;
import Utilities.Property.CoordianteProperty;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class Interface extends Component {
    private CoordianteProperty width;
    private CoordianteProperty height;
    private List<String> methods;

    public Interface(String name, Diagram diagram) {
        super(diagram);

        // Initialize method list
        methods = new ArrayList<>();

        // Add property types
        propertiesTypes.add("Method");

        // Add properties
        properties.add(new InterfaceProperty("Interface Name", name, this));
        width = new CoordianteProperty("Width", 180, this);
        height = new CoordianteProperty("Height", 215, this);
        properties.add(width);
        properties.add(height);
    }

    @Override
    public Property createProperty(String type, String value) throws IllegalArgumentException {
        if (!propertiesTypes.contains(type)) {
            throw new IllegalArgumentException("Invalid property type for Interface: " + type);
        }

        // For Method type, add to methods list
        if ("Method".equals(type)) {
            methods.add(value);
        }

        InterfaceProperty property = new InterfaceProperty(type, value, this);
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

        // Background
        g2d.setColor(new Color(240, 240, 255));
        g2d.fillRect(x, y, width, height);

        // Header
        drawHeader(g2d, x, y, width);

        // Methods
        drawMethods(g2d, x, y + 35, width, height);

        // Border
        g2d.setColor(Color.GRAY);
        g2d.drawRect(x, y, width, height);
    }

    private void drawHeader(Graphics2D g2d, int x, int y, int width) {
        g2d.setColor(new Color(220, 220, 250));
        g2d.fillRect(x, y, width, 35);
        g2d.setColor(Color.BLACK);

        // Centered text
        String interfaceText = "«interface»";
        String name = getName();
        FontRenderContext frc = g2d.getFontRenderContext();

        Font currentFont = g2d.getFont();
        Font boldFont = new Font(currentFont.getName(), Font.BOLD, currentFont.getSize());
        g2d.setFont(boldFont);

        Rectangle2D interfaceBounds = boldFont.getStringBounds(interfaceText, frc);
        Rectangle2D nameBounds = boldFont.getStringBounds(name, frc);

        int interfaceTextX = x + (width - (int)interfaceBounds.getWidth()) / 2;
        int nameTextX = x + (width - (int)nameBounds.getWidth()) / 2;

        g2d.drawString(interfaceText, interfaceTextX, y + 20);
        g2d.drawString(name, nameTextX, y + 35);
    }

    private void drawMethods(Graphics2D g2d, int x, int y, int width, int height) {
        g2d.setColor(Color.WHITE);
        g2d.fillRect(x, y, width, height - 35);

        g2d.setColor(Color.BLACK);
        int textY = y + 15;

        // Retrieve methods from the methods list
        for (String method : methods) {
            g2d.drawString(method, x + 10, textY);
            textY += 15;
        }
    }

    // Getters remain the same
    public CoordianteProperty getWidth() {
        return width;
    }

    public CoordianteProperty getHeight() {
        return height;
    }
}