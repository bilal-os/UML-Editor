package Utilities;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Diagram {
    protected List<Component> components;
    protected List<String> componentNames;
    protected String name;
    public Diagram(String name) {
        this.name = name;
        components = new ArrayList<Component>();
        componentNames = new ArrayList<String>();
    }

    public List<String> getComponentNames() {
        return componentNames;
    }

    public List<Component> getComponents()
    {
        return components;
    }

    public abstract Component addComponent(String componentName);

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


}
