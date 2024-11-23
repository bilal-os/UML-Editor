package Utilities;

import java.util.ArrayList;

public abstract class Component {
    protected ArrayList<Property> properties;
    protected ArrayList<String> propertiesTypes;
    private String name;
    public Component(String name) {
        this.name = name;
        properties = new ArrayList<>();
        propertiesTypes = new ArrayList<>();
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
}
