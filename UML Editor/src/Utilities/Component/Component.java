package Utilities.Component;

import BusinessLogic.ClassDiagram.Components.AbstractClass;
import BusinessLogic.ClassDiagram.Components.Enumeration;
import BusinessLogic.ClassDiagram.Components.Interface;
import Utilities.Property.CoordinateProperty;
import Utilities.Property.Property;
import Utilities.Diagram.Diagram;
import com.fasterxml.jackson.annotation.*;

import java.awt.*;
import java.util.ArrayList;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "componentType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AbstractClass.class, name = "AbstractClass"),
        @JsonSubTypes.Type(value = BusinessLogic.ClassDiagram.Components.Class.class, name = "Class"),
        @JsonSubTypes.Type(value = Enumeration.class, name = "Enumeration"),
        @JsonSubTypes.Type(value = Interface.class, name = "Interface"),
        @JsonSubTypes.Type(value = Package.class, name = "Package")
})
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        scope = Component.class
)
public abstract class Component {
    protected ArrayList<Property> properties;
    protected ArrayList<String> propertiesTypes;
    protected CoordinateProperty x_coordinate;
    protected CoordinateProperty y_coordinate;
    protected ArrayList<ComponentObserver> observers;
    protected int id;
    protected Diagram associatedDiagram;

    // Default Constructor
    public Component() {
        properties = new ArrayList<>();
        propertiesTypes = new ArrayList<>();
        observers = new ArrayList<>();
    }

    // Constructor with Diagram
    public Component(Diagram diagram) {
        associatedDiagram = diagram;
        properties = new ArrayList<>();
        x_coordinate = new CoordinateProperty("X Coordinate", 50, this);
        properties.add(x_coordinate);
        y_coordinate = new CoordinateProperty("Y Coordinate", 50, this);
        properties.add(y_coordinate);

        propertiesTypes = new ArrayList<>();
        observers = new ArrayList<>();

        this.id = associatedDiagram.getComponents().size();
    }

    // Getters and Setters with Jackson annotations
    @JsonProperty("id")
    public int getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty("diagram")
    public Diagram getDiagram() {
        return associatedDiagram;
    }

    @JsonProperty("diagram")
    public void setDiagram(Diagram diagram) {
        associatedDiagram = diagram;
    }

    // Observer Methods
    public void addObserver(ComponentObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ComponentObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (ComponentObserver observer : observers) {
            observer.updateFromComponent(this);
        }
    }

    // Name-related Methods
    public String getName() {
        for (Property property : properties) {
            if (property.gettype().contains("Name")) {
                return property.getValue();
            }
        }
        return "";
    }

    public void setName(String name) {
        for (Property property : properties) {
            if (property.gettype().contains("Name")) {
                property.setValue(name);
                return;
            }
        }
    }

    @JsonProperty("name")
    public String getJsonName() {
        return getName();
    }

    @JsonProperty("name")
    public void setJsonName(String name) {
        setName(name);
    }

    public Property getNameProperty() {
        for (Property property : properties) {
            if (property.gettype().contains("Name")) {
                return property;
            }
        }
        return null;
    }

    @JsonProperty("nameProperty")
    public void setNameProperty(Property nameProperty) {
        for (int i = 0; i < properties.size(); i++) {
            if (properties.get(i).gettype().contains("Name")) {
                properties.set(i, nameProperty);
                break;
            }
        }
    }

    // Properties Management
    @JsonProperty("properties")
    public ArrayList<Property> getProperties() {
        return properties;
    }

    @JsonProperty("properties")
    public void setProperties(ArrayList<Property> properties) {
        this.properties = properties;

        // Update coordinate properties from the properties list
        for (Property prop : properties) {
            if (prop.gettype().equals("X Coordinate")) {
                x_coordinate = (CoordinateProperty) prop;
            } else if (prop.gettype().equals("Y Coordinate")) {
                y_coordinate = (CoordinateProperty) prop;
            }
        }
    }

    @JsonProperty("propertiesTypes")
    public ArrayList<String> getPropertiesTypes() {
        return propertiesTypes;
    }

    @JsonProperty("propertiesTypes")
    public void setPropertiesTypes(ArrayList<String> propertiesTypes) {
        this.propertiesTypes = propertiesTypes;
    }

    public abstract Property createProperty(String type, String value) throws IllegalArgumentException;

    public Property addProperty(String type, String value) throws IllegalArgumentException {
        Property property = createProperty(type, value);
        notifyObservers();
        return property;
    }

    public void removeProperty(Property property) {
        properties.remove(property);
        notifyObservers();
    }

    public void removePropertyType(String type) {
        if (propertiesTypes.contains(type)) {
            propertiesTypes.remove(type);
            notifyObservers();
        }
    }

    // Coordinate-related Methods
    public Property getXCoordinate() {
        return x_coordinate;
    }

    @JsonProperty("xcoordinate")
    public int getXCoordinateValue() {
        return x_coordinate != null ? Integer.parseInt(x_coordinate.getValue()) : 0;
    }

    @JsonProperty("xcoordinate")
    public void setXCoordinateValue(int x) {
        setXCoordinate(x);
    }

    public void setXCoordinate(int x) {
        if (x_coordinate == null) {
            x_coordinate = new CoordinateProperty("X Coordinate", x, this);
            properties.add(x_coordinate);
        } else {
            x_coordinate.setValue(String.valueOf(x));
        }
    }

    public Property getYCoordinate() {
        return y_coordinate;
    }

    @JsonProperty("ycoordinate")
    public int getYCoordinateValue() {
        return y_coordinate != null ? Integer.parseInt(y_coordinate.getValue()) : 0;
    }

    @JsonProperty("ycoordinate")
    public void setYCoordinateValue(int y) {
        setYCoordinate(y);
    }

    public void setYCoordinate(int x) {
        if (y_coordinate == null) {
            y_coordinate = new CoordinateProperty("Y Coordinate", x, this);
            properties.add(y_coordinate);
        } else {
            y_coordinate.setValue(String.valueOf(x));
        }
    }

    // Abstract Method to be implemented by subclasses
    public abstract void renderComponent(Graphics g);
}