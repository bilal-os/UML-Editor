package BusinessLogic;

import Utilities.Component;

public class Interface extends Component {

    public Interface(String name) {
        super();

        // Define valid property types for an interface
        propertiesTypes.add("Method");

        // Add default name property
        properties.add(new InterfaceProperty("Interface Name", name));
    }

    @Override
    public void addProperty(String type, String value) throws IllegalArgumentException {
        if (!propertiesTypes.contains(type)) {
            throw new IllegalArgumentException("Invalid property type for Interface: " + type);
        }
        properties.add(new InterfaceProperty(type, value));
    }
}