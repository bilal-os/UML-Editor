package Utilities.Diagram;

import Utilities.Component.Component;
import java.io.Serializable;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Diagram implements Serializable {
    // Ensure compatibility with serialization
    private static final long serialVersionUID = 1L;

    protected ArrayList<Component> components;
    protected List<String> componentNames;
    private transient ArrayList<DiagramObserver> observers; // Mark observers as transient
    protected String name;

    public Diagram(String name) {
        this.name = name;
        components = new ArrayList<>();
        componentNames = new ArrayList<>();
        observers = new ArrayList<>();
    }

    public List<String> getComponentNames() {
        return componentNames;
    }

    public ArrayList<Component> getComponents() {
        return components;
    }

    public abstract Component createComponent(String componentName);

    public Component addComponent(String componentName) {
        Component component = createComponent(componentName);
        components.add(component);
        notifyObservers();
        return component;
    }

    public String getName() {
        return name;
    }

    // Abstract method for rendering the diagram
    public abstract void renderDiagram(Graphics2D g);

    // Methods for managing observers
    public void addObserver(DiagramObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(DiagramObserver observer) {
        observers.remove(observer);
    }

    // Notify all observers
    public void notifyObservers() {
        for (DiagramObserver observer : observers) {
            observer.updateFromDiagram();
        }
    }
}
