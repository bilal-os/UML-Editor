package BusinessLogic;

import Utilities.Component;

import java.awt.*;

public class Relationship extends Component {

    public Relationship(String name,String type, String source, String target) {
        super();

        // Define valid property types
        propertiesTypes.add("Source");
        propertiesTypes.add("Target");
        propertiesTypes.add("Relationship Type");

        // Add default properties
        properties.add(new RelationshipProperty("RelationShip Name",name));
        properties.add(new RelationshipProperty("Source", source));
        properties.add(new RelationshipProperty("Target", target));
        properties.add(new RelationshipProperty("Relationship Type", type));
    }

    @Override
    public void addProperty(String type, String value) throws IllegalArgumentException {
        // Validate property type
        if (!propertiesTypes.contains(type)) {
            throw new IllegalArgumentException("Invalid property type: " + type);
        }
        // Add validated property
        properties.add(new RelationshipProperty(type, value));
    }
    public void renderComponent(Graphics g) {

    }
}
