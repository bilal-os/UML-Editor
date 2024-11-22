package Utilities;

import java.util.ArrayList;

public abstract class Component {

    protected ArrayList<Property> properties;

    protected ArrayList<String> propertiesTypes;

    public Component() {
        properties = new ArrayList<>();
        propertiesTypes = new ArrayList<>();
    }

    public ArrayList<String> getPropertiesTypes() {
        return propertiesTypes;
    }

    public void addProperty(Property property) {
        properties.add(property);
    }

    public ArrayList<Property> getProperties() {
        return properties;
    }

    public void setProperties(ArrayList<Property> properties) {
        this.properties = properties;
    }

    public void removeProperty(Property property) {
        properties.remove(property);
    }

}
