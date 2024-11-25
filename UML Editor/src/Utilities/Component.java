package Utilities;

import BusinessLogic.CoordianteProperty;

import java.awt.*;
import java.util.ArrayList;

public abstract class Component {
    protected ArrayList<Property> properties;
    protected ArrayList<String> propertiesTypes;
    protected int x_coordinate;
    protected int y_coordinate;
    protected String name;

    public Component(String name) {
        x_coordinate = 0;
        y_coordinate = 0;
        this.name = name;
        properties = new ArrayList<>();
        propertiesTypes = new ArrayList<>();
        properties.add(new CoordianteProperty("X Coordinate",x_coordinate));
        properties.add(new CoordianteProperty("Y Coordinate",y_coordinate));
        propertiesTypes.add("X Coordinate");
        propertiesTypes.add("Y Coordinate");
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getPropertiesTypes() {
        return propertiesTypes;
    }

    public abstract void addProperty(String type, String value) throws IllegalArgumentException;

    public ArrayList<Property> getProperties() {
        return properties;
    }

    public void removeProperty(Property property) {
        properties.remove(property);
    }

    public abstract void renderComponent(Graphics g);

}