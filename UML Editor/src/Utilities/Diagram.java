package Utilities;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Diagram {
    protected ArrayList<Component> components;
    protected ArrayList<Relation> relations;
    protected List<String> componentNames;
    private ArrayList<DiagramObserver> observers;
    protected String name;

    public Diagram(String name) {
        this.name = name;
        components = new ArrayList<>();
        componentNames = new ArrayList<>();
        relations = new ArrayList<>();
        observers = new ArrayList<>();
    }

    public ArrayList<Relation> getRelations() {
        return relations;
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

    public abstract Relation createRelation(String relationName, Component component1, Component component2);

    public Relation addRelation(String relationName, Component component1, Component component2) {
        Relation relation = createRelation(relationName,component1,component2);
        notifyObservers();
        return relation;
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
