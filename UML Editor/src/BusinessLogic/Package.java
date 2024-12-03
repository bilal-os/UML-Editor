package BusinessLogic;

import Utilities.Component;
import Utilities.Property;

import java.awt.*;

public class Package extends Component {

    public Package(String name) {
        super();

        // Define valid property types for a package
        propertiesTypes.add("Contained Component");

        // Add default name property
        properties.add(new PackageProperty("Package Name", name));
    }

    @Override
    public Property createProperty(String type, String value) throws IllegalArgumentException {
        if (!propertiesTypes.contains(type)) {
            throw new IllegalArgumentException("Invalid property type for Package: " + type);
        }
        PackageProperty property = new PackageProperty(type, value);
        properties.add(property);
        return property;
    }
    public void renderComponent(Graphics g) {

    }
}
