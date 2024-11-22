package Utilities;

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

}
