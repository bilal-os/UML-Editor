package BusinessLogic.ClassDiagram.Components;

import BusinessLogic.ClassDiagram.Properties.AbstractClassProperty;
import Utilities.Component.Component;
import Utilities.Diagram.Diagram;
import Utilities.Property.CoordinateProperty;
import Utilities.Property.Property;

import java.awt.*;

import static java.lang.Integer.parseInt;

public class AbstractClass extends Component {
    private CoordinateProperty width;
    private CoordinateProperty height;

    public AbstractClass()
    {
        super();
    }

    public AbstractClass(String name, Diagram diagram) {
        super(diagram);

        propertiesTypes.add("Attribute");
        propertiesTypes.add("Method");
        propertiesTypes.add("Abstract Method");

        properties.add(new AbstractClassProperty("Class Name", name, this));
        width = new CoordinateProperty("Width", 180, this);
        height = new CoordinateProperty("Height", 215, this);
        properties.add(width);
        properties.add(height);
    }

    @Override
    public Property createProperty(String type, String value) throws IllegalArgumentException {
        if (!propertiesTypes.contains(type)) {
            throw new IllegalArgumentException("Invalid property type for Abstract Class: " + type);
        }

        AbstractClassProperty property = new AbstractClassProperty(type, value, this);
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

        g2d.setColor(new Color(245, 245, 245));
        g2d.fillRect(x, y, width, height);

        drawHeader(g2d, x, y, width);
        drawAttributes(g2d, x, y + 35, width, height / 3);
        drawMethods(g2d, x, y + height * 2 / 3, width, height / 3);

        g2d.setColor(Color.GRAY);
        g2d.drawRect(x, y, width, height);
    }

    private void drawHeader(Graphics2D g2d, int x, int y, int width) {
        g2d.setColor(new Color(220, 220, 250));
        g2d.fillRect(x, y, width, 35);
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font(g2d.getFont().getFontName(), Font.ITALIC, g2d.getFont().getSize()));
        g2d.drawString("«abstract»", x + 10, y + 20);
        g2d.setFont(new Font(g2d.getFont().getFontName(), Font.PLAIN, g2d.getFont().getSize()));
        g2d.drawString(getName(), x + 10, y + 35);
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
            if (method.gettype().equals("Method") || method.gettype().equals("Abstract Method")) {
                g2d.drawString(method.getValue(), x + 10, textY);
                textY += 15;
            }
        }
    }

    public CoordinateProperty getWidth() {
        return width;
    }
    public CoordinateProperty getHeight() {
        return height;
    }

}