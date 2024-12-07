package Utilities.Property;

import BusinessLogic.ClassDiagram.Properties.*;
import BusinessLogic.ClassDiagram.Properties.RelationsProperties.*;
import Utilities.Component.Component;
import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "propertyType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AggregationProperty.class, name = "AggregationProperty"),
        @JsonSubTypes.Type(value = AssociationProperty.class, name = "AssociationProperty"),
        @JsonSubTypes.Type(value = CompositionProperty.class, name = "CompositionProperty"),
        @JsonSubTypes.Type(value = InheritanceProperty.class, name = "InheritanceProperty"),
        @JsonSubTypes.Type(value = AbstractClassProperty.class, name = "AbstractClassProperty"),
        @JsonSubTypes.Type(value = ClassProperty.class, name = "ClassProperty"),
        @JsonSubTypes.Type(value = EnumerationProperty.class, name = "EnumerationProperty"),
        @JsonSubTypes.Type(value = InterfaceProperty.class, name = "InterfaceProperty"),
        @JsonSubTypes.Type(value = PackageProperty.class, name = "PackageProperty"),
        @JsonSubTypes.Type(value = CoordinateProperty.class, name = "CoordinateProperty")
})
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        scope = Property.class
)
public abstract class Property {
    protected int id;
    protected String type;
    protected String value;
    protected ArrayList<PropertyObserver> observers;
    protected Component associatedComponent;

    // Default Constructor
    public Property() {
        observers = new ArrayList<>();
    }

    // Parameterized Constructor
    public Property(String type, String value, Component associatedComponent) throws IllegalArgumentException {
        try {
            this.associatedComponent = associatedComponent;
            validateInput(type, value);
            this.type = type;
            this.value = value;
            observers = new ArrayList<>();
            id = associatedComponent.getProperties().size();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e);
        }
    }

    // ID Getters and Setters
    @JsonProperty("id")
    public int getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(int id) {
        this.id = id;
    }

    // Type Getters and Setters
    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    // Backwards compatibility with existing method
    public String gettype() {
        return getType();
    }

    // Value Getters and Setters
    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(String value) throws IllegalArgumentException {
        addValue(value);
        notifyObservers();
    }

    // Associated Component Getters and Setters
    @JsonProperty("associatedComponent")
    public Component getAssociatedComponent() {
        return associatedComponent;
    }

    @JsonProperty("associatedComponent")
    public void setAssociatedComponent(Component associatedComponent) {
        this.associatedComponent = associatedComponent;
    }

    // Observer Management
    public void addObserver(PropertyObserver observer) {
        if (observers == null) {
            observers = new ArrayList<>();
        }
        observers.add(observer);
    }

    public void removeObserver(PropertyObserver observer) {
        if (observers != null) {
            observers.remove(observer);
        }
    }

    public void notifyObservers() {
        if (observers != null) {
            for (PropertyObserver observer : observers) {
                observer.updateFromProperty();
            }
        }
    }

    // Abstract Methods to be implemented by subclasses
    public abstract void validateInput(String type, String value) throws IllegalArgumentException;

    public abstract void addValue(String value) throws IllegalArgumentException;
}