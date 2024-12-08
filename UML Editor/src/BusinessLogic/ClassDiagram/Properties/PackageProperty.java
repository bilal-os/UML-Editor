package BusinessLogic.ClassDiagram.Properties;

import BusinessLogic.ClassDiagram.Components.AbstractClass;
import BusinessLogic.ClassDiagram.Components.Class;
import BusinessLogic.ClassDiagram.Components.Enumeration;
import BusinessLogic.ClassDiagram.Components.Interface;
import BusinessLogic.ClassDiagram.Components.Package;
import Utilities.Component.Component;
import Utilities.Property.Property;

import java.util.Objects;

public class PackageProperty extends Property {

    public PackageProperty(String type, String value, Component component) throws IllegalArgumentException {
        super(type, value, component);
    }

    @Override
    public void validateInput(String type, String value) throws IllegalArgumentException {
        if ("Package Name".equals(type)) {
            if (!isValidName(value)) {
                throw new IllegalArgumentException("Invalid Package Name: Must start with an uppercase letter and contain only alphanumeric characters or underscores.");
            }
        } else if ("Add Component".equals(type)) {
           isValidComponentName(value);

        } else {
            throw new IllegalArgumentException("Unknown property type for Package: " + type);
        }
    }

    @Override
    public void addValue(String value) throws IllegalArgumentException {
        validateInput(this.type, value);
        this.value = value;
    }

    private boolean isValidName(String value) {
        return value != null && !value.trim().isEmpty() && value.matches("^[A-Z][A-Za-z0-9_]*$");
    }

    private void isValidComponentName(String value) throws IllegalArgumentException {
        Component foundComponent = findComponentByName(value);
        if (foundComponent == null) {
           throw new IllegalArgumentException("Given Component Does not exist " + value);
        }

        if(!((foundComponent instanceof Class) || (foundComponent instanceof AbstractClass) || (foundComponent instanceof Interface) || (foundComponent instanceof Enumeration) ))
        {
            throw new IllegalArgumentException("Invalid component: " + value + " cannot be associated.");
        }
        Package assocaitedPackage = (Package) associatedComponent;
        assocaitedPackage.addContainedComponent(foundComponent);

    }

    private Component findComponentByName(String componentName) {
        return associatedComponent.getDiagram().getComponents().stream()
                .filter(component -> Objects.equals(component.getName(), componentName))
                .findFirst()
                .orElse(null);
    }
}