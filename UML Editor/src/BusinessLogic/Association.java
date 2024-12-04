package BusinessLogic;

import Utilities.*;
import Utilities.Component;
import jdk.jshell.Diag;

import java.awt.*;

    public class Association extends RelationComponent {

        public Association(String name, Diagram diagram)
        {
            super(diagram);

            properties.add(new AssociationProperty("Association Name",name, this));
        }

        @Override
        public Property createProperty(String type, String value) throws IllegalArgumentException {
            if (!propertiesTypes.contains(type)) {
                throw new IllegalArgumentException("Invalid property type: " + type);
            }

            AssociationProperty property = new AssociationProperty(type,value,this);
            properties.add(property);
            return property;
        }

        @Override
        public void renderComponent(Graphics g) {

        }
    }
