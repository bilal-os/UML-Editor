package BusinessLogic;

import Utilities.Component;
import Utilities.Diagram;
import Utilities.Property;

import java.awt.*;

public class Enumeration extends Component {

    public Enumeration(String name, Diagram diagram) {
        super(diagram);

        // Define valid property types for an enumeration
        propertiesTypes.add("Value");

        // Add default name property
        properties.add(new EnumerationProperty("Enumeration Name", name,this));
    }

    @Override
    public Property createProperty(String type, String value) throws IllegalArgumentException {
        if (!propertiesTypes.contains(type)) {
            throw new IllegalArgumentException("Invalid property type for Enumeration: " + type);
        }
        EnumerationProperty property = new EnumerationProperty(type, value,this);
        properties.add(property);
        return property;
    }

    public void renderComponent(Graphics g) {

    }

}
