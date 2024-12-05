package BusinessLogic.ClassDiagram.Diagram;

import BusinessLogic.ClassDiagram.Components.Association;
import BusinessLogic.ClassDiagram.Components.Class;
import BusinessLogic.ClassDiagram.Components.Enumeration;
import BusinessLogic.ClassDiagram.Components.Interface;
import Utilities.Component.Component;
import Utilities.Diagram.Diagram;

public class ClassDiagram extends Diagram {

    public ClassDiagram()
    {
        super("Class Diagram");
        componentNames.add("Class");
        componentNames.add("Interface");
        componentNames.add("Enum");
        componentNames.add("Association");
    }

    public Component createComponent(String componentName) {
        Component component;
        switch (componentName) {
            case "Class" -> {
                component = new Class("NewClass",this);
                components.add(component);
                return component;
            }
            case "Interface" -> {
                component = new Interface("NewInterface",this);
                components.add(component);
                return component;
            }
            case "Enum" -> {
                component = new Enumeration("NewEnum",this);
                components.add(component);
                return component;
            }
            case "Association" -> {
                component = new Association("New Association",this);
                components.add(component);
                return component;
            }
        }
        return null;
    }


}
