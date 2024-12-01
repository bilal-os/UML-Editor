package BusinessLogic;

import Utilities.Component;

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
    public void createProperty(String type, String value) throws IllegalArgumentException {
        if (!propertiesTypes.contains(type)) {
            throw new IllegalArgumentException("Invalid property type for Enumeration: " + type);
        }
        properties.add(new EnumerationProperty(type, value));
    }

    public void renderComponent(Graphics g) {

    }

}
