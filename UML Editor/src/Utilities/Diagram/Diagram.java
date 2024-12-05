package Utilities.Diagram;

import Utilities.Component.Component;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Diagram {
    protected ArrayList<Utilities.Component.Component> components;

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

    public ArrayList<Utilities.Component.Component> getComponents()
    {
        return components;
    }

    public abstract Utilities.Component.Component createComponent(String componentName);

    public Utilities.Component.Component addComponent(String componentName) {
        Utilities.Component.Component component = createComponent(componentName);
        
        notifyObservers();
        return component;
    }

    public String getName()
    {
        return name;
    }

    abstract public void renderDiagram(Graphics2D g);


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
