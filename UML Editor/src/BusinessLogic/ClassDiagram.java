package BusinessLogic;

import Utilities.Component;
import Utilities.Diagram;

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
        if(componentName.equals("Class"))
        {
            component = new Class("NewClass");
            components.add(component);
            return component;
        }
        else if(componentName.equals("Interface"))
        {
            component = new Class("NewInterface");
            components.add(component);
            return component;
        }
        else if(componentName.equals("Enum"))
        {
            component = new Class("NewEnum");
            components.add(component);
            return component;
        }
        else if(componentName.equals("Relationship"))
        {
            component = new Class("NewRelationship");
            components.add(component);
            return component;
        }
        return null;
    }



}
