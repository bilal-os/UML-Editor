package Utilities.Diagram;

import BusinessLogic.ClassDiagram.Diagram.ClassDiagram;
import Utilities.Component.Component;
import com.fasterxml.jackson.annotation.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ClassDiagram.class, name = "ClassDiagram")
})
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        scope = ClassDiagram.class
)
public abstract class Diagram {
    protected ArrayList<Component> components;
    protected List<String> componentNames;
    private ArrayList<DiagramObserver> observers;
    protected String name;
    protected int id;

    // Default Constructor
    public Diagram() {
        components = new ArrayList<>();
        componentNames = new ArrayList<>();
        observers = new ArrayList<>();
    }

    // Parameterized Constructor
    public Diagram(String name, int id) {
        this.id = id;
        this.name = name;
        components = new ArrayList<>();
        componentNames = new ArrayList<>();
        observers = new ArrayList<>();
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

    // Name Getters and Setters
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    // Components Getters and Setters
    @JsonProperty("components")
    public ArrayList<Component> getComponents() {
        return components;
    }

    @JsonProperty("components")
    public void setComponents(ArrayList<Component> components) {
        this.components = components;
        // If needed, update each component's associated diagram
        if (components != null) {
            for (Component component : components) {
                component.setDiagram(this);
            }
        }
    }

    // Component Names Getters and Setters
    @JsonProperty("componentNames")
    public List<String> getComponentNames() {
        return componentNames;
    }

    @JsonProperty("componentNames")
    public void setComponentNames(List<String> componentNames) {
        this.componentNames = componentNames;
    }

    // Observer-related Methods
    public void addObserver(DiagramObserver observer) {
        if (observers == null) {
            observers = new ArrayList<>();
        }
        observers.add(observer);
    }

    public void removeObserver(DiagramObserver observer) {
        if (observers != null) {
            observers.remove(observer);
        }
    }

    public void notifyObservers() {
        if (observers != null) {
            for (DiagramObserver observer : observers) {
                observer.updateFromDiagram();
            }
        }
    }

    // Abstract Methods
    public abstract Component createComponent(String componentName);

    public Component addComponent(String componentName) {
        Component component = createComponent(componentName);
        notifyObservers();
        return component;
    }

    // Abstract method to render diagram
    abstract public void renderDiagram(Graphics2D g);
}