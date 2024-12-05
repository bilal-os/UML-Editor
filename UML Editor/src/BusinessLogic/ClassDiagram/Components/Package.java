package BusinessLogic.ClassDiagram.Components;

import BusinessLogic.ClassDiagram.Properties.PackageProperty;
import Utilities.Component.Component;
import Utilities.Diagram.Diagram;
import Utilities.Property.CoordianteProperty;
import Utilities.Property.Property;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class Package extends Component {
    private List<Component> containedComponents;
    private CoordianteProperty width;
    private CoordianteProperty height;

    public Package(String name, Diagram diagram) {
        super(diagram);
        containedComponents = new ArrayList<>();
        propertiesTypes.add("Add Component");
        properties.add(new PackageProperty("Package Name", name, this));

        width = new CoordianteProperty("Width", 300, this);
        height = new CoordianteProperty("Height", 400, this);
        properties.add(width);
        properties.add(height);
    }


    @Override
    public Property createProperty(String type, String value) throws IllegalArgumentException {
        if (!propertiesTypes.contains(type)) {
            throw new IllegalArgumentException("Invalid property type for Package: " + type);
        }
        PackageProperty property = new PackageProperty(type, value, this);
        properties.add(property);
        return property;
    }

    @Override
    public void renderComponent(Graphics g) {
        if (containedComponents.isEmpty()) return;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Calculate package boundaries with padding
        int minX = containedComponents.stream()
                .mapToInt(comp -> parseInt(comp.getXCoordinate().getValue()))
                .min()
                .orElse(0) - 20;

        int minY = containedComponents.stream()
                .mapToInt(comp -> parseInt(comp.getYCoordinate().getValue()))
                .min()
                .orElse(0) - 20;

        int maxX = containedComponents.stream()
                .mapToInt(comp -> parseInt(comp.getXCoordinate().getValue()) + getComponentWidth(comp))
                .max()
                .orElse(0) + 20;

        int maxY = containedComponents.stream()
                .mapToInt(comp -> parseInt(comp.getYCoordinate().getValue()) + getComponentHeight(comp))
                .max()
                .orElse(0) + 20;

        // Create package tab
        int tabWidth = 80;
        int tabHeight = 20;

        // Draw white background for the entire package area
        g2d.setColor(Color.WHITE);
        g2d.fillRect(minX, minY, maxX - minX, maxY - minY);

        // Draw package tab
        g2d.setColor(Color.LIGHT_GRAY);
        int[] tabXPoints = {minX, minX + tabWidth, minX + tabWidth, minX};
        int[] tabYPoints = {minY, minY, minY - tabHeight, minY - tabHeight};
        g2d.fillPolygon(tabXPoints, tabYPoints, 4);

        // Draw package border
        g2d.setColor(Color.DARK_GRAY);
        g2d.drawRect(minX, minY, maxX - minX, maxY - minY);
        // Draw tab border
        g2d.drawPolygon(tabXPoints, tabYPoints, 4);

        // Draw package name in the tab
        g2d.setColor(Color.BLACK);
        g2d.drawString(getName(), minX + 5, minY - 5);
    }

    private int getComponentWidth(Component comp) {
        if (comp instanceof Class classComp) return parseInt(classComp.getWidth().getValue());
        if (comp instanceof Enumeration enumComp) return parseInt(enumComp.getWidth().getValue());
        if(comp instanceof AbstractClass abstractClassComp) return parseInt(abstractClassComp.getWidth().getValue());
        if(comp instanceof Interface interfaceComp) return  parseInt(interfaceComp.getWidth().getValue());
        return 180;
    }

    private int getComponentHeight(Component comp) {
        if (comp instanceof Class classComp) return parseInt(classComp.getHeight().getValue());
        if (comp instanceof Enumeration enumComp) return parseInt(enumComp.getHeight().getValue());
        if(comp instanceof AbstractClass abstractClassComp) return parseInt(abstractClassComp.getHeight().getValue());
        if(comp instanceof Interface interfaceComp) return parseInt(interfaceComp.getHeight().getValue());
        return 215;
    }

    public List<Component> getContainedComponents() {
        return containedComponents;
    }

    public void addContainedComponent(Component component) {
        containedComponents.add(component);
    }
}