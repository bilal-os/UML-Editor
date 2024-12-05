package BusinessLogic.ClassDiagram.Diagram;

import BusinessLogic.ClassDiagram.Components.*;
import BusinessLogic.ClassDiagram.Components.Class;
import BusinessLogic.ClassDiagram.Components.Package;
import Utilities.Component.Component;
import Utilities.Diagram.Diagram;

import java.awt.*;
import java.util.List;


public class ClassDiagram extends Diagram {

    public ClassDiagram()
    {
        super("Class Diagram");
        componentNames.add("Class");
        componentNames.add("Interface");
        componentNames.add("Enum");
        componentNames.add("Association");
        componentNames.add("Abstract Class");
        componentNames.add("Package");
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
            case "Abstract Class" -> {
                component = new AbstractClass("NewAbstractClass",this);
                components.add(component);
                return component;
            }
            case "Package" -> {
                component = new Package("NewPackage", this);
                components.add(component);

                return component;
            }

        }
        return null;
    }

    @Override
    public void renderDiagram(Graphics2D g)
    {
        for (Component component : components) {
            if(component instanceof Package)
            {
                component.renderComponent(g);
            }
        }

        for(Component component : components)
        {
            if(!(component instanceof Package))
            {
                component.renderComponent(g);
            }
        }
    }

}
