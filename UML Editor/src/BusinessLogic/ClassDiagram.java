package BusinessLogic;

import Utilities.Component;
import Utilities.Diagram;

import java.awt.*;

public class ClassDiagram extends Diagram {

    public ClassDiagram()
    {
        super("Class Diagram");
        componentNames.add("Class");
        componentNames.add("Interface");
        componentNames.add("Enum");
        componentNames.add("Relationship");
    }

    public Component addComponent(String componentName)
    {
        Component component;
        switch (componentName) {
            case "Class" -> {
                component = new Class("NewClass");
                components.add(component);
                return component;
            }
            case "Interface" -> {
                component = new Interface("NewInterface");
                components.add(component);
                return component;
            }
            case "Enum" -> {
                component = new Enumeration("NewEnum");
                components.add(component);
                return component;
            }
            case "Relationship" -> {
                component = new Class("NewRelationship");
                components.add(component);
                return component;
            }
        }
        return null;
    }

}
