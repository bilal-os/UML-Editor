package BusinessLogic;

import Utilities.Component;

public class Package extends Component {

    public Package(String name) {
        super();

        // Define valid property types for a package
        propertiesTypes.add("Contained Component");

        // Add default name property
        properties.add(new PackageProperty("Package Name", name));
    }

    @Override
    public void addProperty(String type, String value) throws IllegalArgumentException {
        if (!propertiesTypes.contains(type)) {
            throw new IllegalArgumentException("Invalid property type for Package: " + type);
        }
        properties.add(new PackageProperty(type, value));
    }
}