package BusinessLogic.ClassDiagram.Components;

import BusinessLogic.ClassDiagram.Properties.InterfaceProperty;
import Utilities.Component.Component;
import Utilities.Diagram.Diagram;
import Utilities.Property.Property;

import java.awt.*;

public class Interface extends Component {

    public Interface(String name, Diagram diagram) {
        super(diagram);

        // Define valid property types for an interface
        propertiesTypes.add("Method");

        // Add default name property
        properties.add(new InterfaceProperty("Interface Name", name,this));
    }

    @Override
    public Property createProperty(String type, String value) throws IllegalArgumentException {
        if (!propertiesTypes.contains(type)) {
            throw new IllegalArgumentException("Invalid property type for Interface: " + type);
        }
        InterfaceProperty property = new InterfaceProperty(type, value,this);
        properties.add(property);
        return property;
    }
    public void renderComponent(Graphics g) {

    }
}
