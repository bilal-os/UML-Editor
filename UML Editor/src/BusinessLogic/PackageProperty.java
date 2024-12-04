package BusinessLogic;

import Utilities.Component;
import Utilities.Property;

public class PackageProperty extends Property {

    public PackageProperty(String type, String value, Component component) throws IllegalArgumentException {
        super(type, value, component);
    }

    @Override
    public void validateInput(String type, String value) throws IllegalArgumentException {
        if ("Package Name".equals(type)) {
            if (!isValidName(value)) {
                throw new IllegalArgumentException("Invalid Package Name: Must start with an uppercase letter.");
            }
        } else if ("Contained Component".equals(type)) {
            if (!isValidComponentName(value)) {
                throw new IllegalArgumentException("Invalid Contained Component Name: Must be a valid class or interface name.");
            }
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
        return value != null && value.matches("^[A-Z][A-Za-z0-9_]*$");
    }

    private boolean isValidComponentName(String value) {
        return value != null && value.matches("^[A-Z][A-Za-z0-9_]*$");
    }
}
