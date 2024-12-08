package BusinessLogic.ClassDiagram.Properties.RelationsProperties;

import BusinessLogic.ClassDiagram.Components.AbstractClass;
import BusinessLogic.ClassDiagram.Components.Class;
import BusinessLogic.ClassDiagram.Components.Interface;
import Utilities.Component.Component;
import Utilities.Property.Property;
import Utilities.Component.RelationComponent;

import java.util.Objects;

public class AggregationProperty extends Property {

    public AggregationProperty() {
        super();
    }

    public AggregationProperty(String type, String value, Component component) {
        super(type, value, component);
    }

    @Override
    public void validateInput(String type, String value) throws IllegalArgumentException {
        RelationComponent relationComponent = (RelationComponent) associatedComponent;

        if ((type.equals("Source") || type.equals("Target"))) {
            Component foundComponent = findComponentByName(value);

            if (type.equals("Source")) {
                // Source CANNOT be an Interface
                if (foundComponent instanceof Interface) {
                    throw new IllegalArgumentException("Source cannot be an Interface: " + value);
                }

                // Source must be a concrete class (Class or AbstractClass)
                if (!(foundComponent instanceof Class) && !(foundComponent instanceof AbstractClass)) {
                    throw new IllegalArgumentException("Source must be a Class or Abstract Class: " + value);
                }

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