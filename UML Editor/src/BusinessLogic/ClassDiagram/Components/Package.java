package BusinessLogic.ClassDiagram.Components;

import BusinessLogic.ClassDiagram.Properties.PackageProperty;
import Utilities.Component.Component;
import Utilities.Diagram.Diagram;
import Utilities.Property.Property;

import java.awt.*;

public class Package extends Component {

    public Package(String name, Diagram diagram) {
        super(diagram);

        // Define valid property types for a package
        propertiesTypes.add("Contained Component");

        // Add default name property
        properties.add(new PackageProperty("Package Name", name,this));
    }

    @Override
    public Property createProperty(String type, String value) throws IllegalArgumentException {
        if (!propertiesTypes.contains(type)) {
            throw new IllegalArgumentException("Invalid property type for Package: " + type);
        }
        PackageProperty property = new PackageProperty(type, value,this);
        properties.add(property);
        return property;
    }
    public void renderComponent(Graphics g) {

    }
}
