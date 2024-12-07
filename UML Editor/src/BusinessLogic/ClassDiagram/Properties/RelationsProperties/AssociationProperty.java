package BusinessLogic.ClassDiagram.Properties.RelationsProperties;

import BusinessLogic.ClassDiagram.Components.AbstractClass;
import BusinessLogic.ClassDiagram.Components.Class;
import Utilities.Component.Component;
import Utilities.Property.Property;
import Utilities.Component.RelationComponent;

import java.util.Objects;

public class AssociationProperty extends Property {

    public AssociationProperty()
    {
        super();
    }

    public AssociationProperty(String type, String value, Component component) {
        super(type, value, component);
    }

    @Override
    public void validateInput(String type, String value) throws IllegalArgumentException {
        RelationComponent relationComponent = (RelationComponent) associatedComponent;

        if ((type.equals("Source") || type.equals("Target"))) {
            Component foundComponent = findComponentByName(value);
           if(type.equals("Source")) {
               if(!((foundComponent instanceof Class) || (foundComponent instanceof AbstractClass)))
               {
                   throw new IllegalArgumentException("Invalid component: " + value + " cannot be associated.");
               }
           }


            if (type.equals("Source")) {
                relationComponent.setSource(foundComponent);
                relationComponent.removePropertyType("Source");
            } else {
                relationComponent.setTarget(foundComponent);
                relationComponent.removePropertyType("Target");
            }

        }
        else if(type.equals("Source Multiplicity")) {
            if(!validateMultiplicity(value))
            {
                throw new IllegalArgumentException("Invalid multiplicity Format: " + value );
            }
            relationComponent.removePropertyType("Source Multiplicity");
        }

        else if(type.equals("Target Multiplicity")) {
            if(!validateMultiplicity(value))
            {
                throw new IllegalArgumentException("Invalid multiplicity Format: " + value );
            }
            relationComponent.removePropertyType("Target Multiplicity");
        }
    }

    private boolean validateMultiplicity(String value) throws IllegalArgumentException {
        // Regular expression for validating UML multiplicity formats
        String multiplicityRegex = "^(\\d+|\\*)(\\.\\.(\\d+|\\*))?$";
        return value.matches(multiplicityRegex);
    }


    private Component findComponentByName(String componentName) {
        return associatedComponent.getDiagram().getComponents().stream()
                .filter(component -> Objects.equals(component.getName(), componentName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void addValue(String value) {
        validateInput(this.type, value);
        this.value = value;
    }
}