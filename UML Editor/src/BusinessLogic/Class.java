package BusinessLogic;

import UILayer.ClassProperty;
import Utilities.Component;
import Utilities.Property;

import java.util.ArrayList;

public class Class extends Component {


    public Class(String name) {

        super(name);

        propertiesTypes.add("Attribute");
        propertiesTypes.add("Method");

        // Add default property
        properties.add(new ClassProperty("Class Name", name));
    }

    @Override
    public void addProperty(String type, String value) throws IllegalArgumentException {
        // Ensure property type is valid
        if (!propertiesTypes.contains(type)) {
            throw new IllegalArgumentException("Invalid property type: " + type);
        }

        // Create a new ClassProperty object with validation
        ClassProperty property = new ClassProperty(type, value);

        // Add the validated property to the properties list
        properties.add(property);
    }



}
