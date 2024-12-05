package BusinessLogic.ClassDiagram.Properties;

import BusinessLogic.ClassDiagram.Components.Class;
import Utilities.Component.Component;
import Utilities.Property.Property;
import Utilities.Component.RelationComponent;

import java.util.Objects;

public class AssociationProperty extends Property {
    public AssociationProperty(String type, String value, Component component) {
        super(type, value, component);
    }

    @Override
    public void validateInput(String type, String value) throws IllegalArgumentException {
        RelationComponent relationComponent = (RelationComponent) associatedComponent;

        if ((type.equals("Source") || type.equals("Target"))) {
            Component foundComponent = findComponentByName(value);
            if(!(foundComponent instanceof Class))
            {
                throw new IllegalArgumentException("Invalid component: " + value + " cannot be associated.");
            }

            if (type.equals("Source")) {
                relationComponent.setSource(foundComponent);
                relationComponent.removePropertyType("Source");
            } else {
                relationComponent.setTarget(foundComponent);
            }

        }
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