package BusinessLogic.ClassDiagram.Properties;

import Utilities.Component.Component;
import Utilities.Property.Property;

public class EnumerationProperty extends Property {

    public EnumerationProperty(String type, String value, Component component) throws IllegalArgumentException {
        super(type, value, component);
        validateInput(type, value);
    }

    @Override
    public void validateInput(String type, String value) throws IllegalArgumentException {
        if ("Enumeration Name".equals(type)) {
            if (!isValidName(value)) {
                throw new IllegalArgumentException("Invalid Enumeration Name: Must start with an uppercase letter and contain only alphanumeric characters or underscores.");
            }
        } else if ("Value".equals(type)) {
            if (!isValidValue(value)) {
                throw new IllegalArgumentException("Invalid Enumeration Value: Must be uppercase, alphanumeric, or underscore.");
            }
        } else {
            throw new IllegalArgumentException("Unknown property type for Enumeration: " + type);
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

    private boolean isValidValue(String value) {
        return value != null && !value.trim().isEmpty() && value.matches("^[A-Z0-9_]+$");
    }
}