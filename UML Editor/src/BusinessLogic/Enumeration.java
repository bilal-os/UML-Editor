package BusinessLogic;

import Utilities.Component;
import Utilities.Property;

import java.awt.*;

public class Enumeration extends Component {

    public Enumeration(String name) {
        super();

        // Define valid property types for an enumeration
        propertiesTypes.add("Value");

        // Add default name property
        properties.add(new EnumerationProperty("Enumeration Name", name));
    }

    @Override
    public Property createProperty(String type, String value) throws IllegalArgumentException {
        if (!propertiesTypes.contains(type)) {
            throw new IllegalArgumentException("Invalid property type for Enumeration: " + type);
        }
        EnumerationProperty property = new EnumerationProperty(type, value);
        properties.add(property);
        return property;
    }

    public void renderComponent(Graphics g) {

    }

}
