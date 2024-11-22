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
            component = new Class("New Class");
            components.add(component);
            return component;
        }
        return null;
    }



}
