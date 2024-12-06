package BusinessLogic.ClassDiagram.Properties.RelationsProperties;

import BusinessLogic.ClassDiagram.Components.AbstractClass;
import BusinessLogic.ClassDiagram.Components.Interface;
import BusinessLogic.ClassDiagram.Components.Class;
import Utilities.Component.Component;
import Utilities.Property.Property;
import Utilities.Component.RelationComponent;

import java.util.Objects;

public class InheritanceProperty extends Property {
    public InheritanceProperty(String type, String value, Component component) {
        super(type, value, component);
    }

    @Override
    public void validateInput(String type, String value) throws IllegalArgumentException {
        RelationComponent relationComponent = (RelationComponent) associatedComponent;

        if ((type.equals("Source") || type.equals("Target"))) {
            Component foundComponent = findComponentByName(value);

            if (type.equals("Source")) {
                if (!(foundComponent instanceof Class)) {
                    throw new IllegalArgumentException("Source must be a Class: " + value);
                }
                relationComponent.setSource(foundComponent);
                relationComponent.removePropertyType("Source");
            } else {
                if (!((foundComponent instanceof Interface) || (foundComponent instanceof AbstractClass))) {
                    throw new IllegalArgumentException("Target must be an Interface or an Abstract Class: " + value);
                }
                relationComponent.setTarget(foundComponent);
                relationComponent.removePropertyType("Target");
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