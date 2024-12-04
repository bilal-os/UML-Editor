package Utilities;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Diagram {
    protected ArrayList<Component> components;

    protected List<String> componentNames;
    private ArrayList<DiagramObserver> observers;
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

    public ArrayList<Component> getComponents()
    {
        return components;
    }

    public abstract Component createComponent(String componentName);

    public Component addComponent(String componentName) {
        Component component = createComponent(componentName);
        
        notifyObservers();
        return component;
    }

    public String getName()
    {
        return name;
    }

    public void renderDiagram(Graphics2D g) {
        for (Component component : components) {
            component.renderComponent(g);
        }
    }

    public void addObserver(DiagramObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(DiagramObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (DiagramObserver observer : observers) {
            observer.updateFromDiagram();
        }
    }
}
