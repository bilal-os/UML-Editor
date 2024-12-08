package BusinessLogic.ClassDiagram.Diagram;

import BusinessLogic.ClassDiagram.Components.*;
import BusinessLogic.ClassDiagram.Components.Class;
import BusinessLogic.ClassDiagram.Components.Package;
import BusinessLogic.ClassDiagram.Components.Relations.Aggregation;
import BusinessLogic.ClassDiagram.Components.Relations.Association;
import BusinessLogic.ClassDiagram.Components.Relations.Composition;
import BusinessLogic.ClassDiagram.Components.Relations.Inheritance;
import Utilities.Component.Component;
import Utilities.Diagram.Diagram;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.awt.*;

public class ClassDiagram extends Diagram {

public ClassDiagram() {
    super();
}

    public ClassDiagram(int id) {
        super("Class Diagram",id);
        componentNames.add("Class");
        componentNames.add("Interface");
        componentNames.add("Enum");
        componentNames.add("Association");
        componentNames.add("Abstract Class");
        componentNames.add("Package");
        componentNames.add("Inheritance");
        componentNames.add("Composition");
        componentNames.add("Aggregation");
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
            case "Inheritance" -> {
                component = new Inheritance("NewInheritance",this);
                components.add(component);
                return component;
            }
            case "Composition" -> {
                component = new Composition("NewComposition",this);
                components.add(component);
                return component;
            }
            case "Aggregation" -> {
                component = new Aggregation("NewAggregation",this);
                components.add(component);
                return component;
            }

        }
        return null;
    }

    @Override
    public void renderDiagram(Graphics2D g) {
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
