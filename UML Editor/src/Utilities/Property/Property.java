package Utilities.Property;

import BusinessLogic.ClassDiagram.Properties.*;
import BusinessLogic.ClassDiagram.Properties.RelationsProperties.*;
import Utilities.Component.Component;
import Utilities.GenerateId;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

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
        scope = Component.class
)
public abstract class Property {
    @JsonSerialize(using = ToStringSerializer.class)
    protected Integer id;

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

        if (associatedComponent == null) {
            throw new IllegalArgumentException("associatedComponent cannot be null");
        }

        try {
            this.associatedComponent = associatedComponent;
            validateInput(type, value);
            this.type = type;
            this.value = value;
            observers = new ArrayList<>();

            // Use Integer instead of primitive int
            this.id = GenerateId.generateId();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e);
        }
    }

    // ID Getters and Setters with explicit JSON handling
    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    // Rest of the implementation remains the same as in previous artifact

    // Type-related methods
    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    // Backwards compatibility method
    public String gettype() {
        return getType();
    }

    // Value-related methods
    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(String value) throws IllegalArgumentException {
        addValue(value);
        notifyObservers();
    }

    // Associated Component methods
    @JsonProperty("associatedComponent")
    public Component getAssociatedComponent() {
        return associatedComponent;
    }

    @JsonProperty("associatedComponent")
    public void setAssociatedComponent(Component associatedComponent) {
        this.associatedComponent = associatedComponent;
    }

    // Observer methods
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

    // Abstract methods
    public abstract void validateInput(String type, String value) throws IllegalArgumentException;

    public abstract void addValue(String value) throws IllegalArgumentException;
}