package Utilities;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Diagram implements Subject{
    protected List<Component> components;
    protected List<String> componentNames;
    private ArrayList<Observer> observers;
    protected String name;

    public Diagram(String name) {
        this.name = name;
        components = new ArrayList<Component>();
        componentNames = new ArrayList<String>();
        observers = new ArrayList<>();

    }

    public List<String> getComponentNames() {
        return componentNames;
    }

    public List<Component> getComponents()
    {
        return components;
    }

    public abstract Component createComponent(String componentName);

    public Component addComponent(String componentName)
    {
        Component component = createComponent(componentName);
        notifyObservers();
        return component;
    }

    public String getName()
    {
        return name;
    }

    public void renderDiagram(Graphics2D g)
    {
        for (Component component : components) {
            component.renderComponent(g);
        }
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

}
