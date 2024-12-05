package BusinessLogic.ClassDiagram.Properties;

import Utilities.Component.Component;
import Utilities.Property.Property;

public class InterfaceProperty extends Property {

    public InterfaceProperty(String type, String value, Component component) throws IllegalArgumentException {
        super(type, value, component);
        validateInput(type, value);
    }

    @Override
    public void validateInput(String type, String value) throws IllegalArgumentException {
        if ("Interface Name".equals(type)) {
            if (!isValidName(value)) {
                throw new IllegalArgumentException("Invalid Interface Name: Must start with an uppercase letter and contain only letters, numbers, or underscores.");
            }
        } else if ("Method".equals(type)) {
            if (!isValidMethod(value)) {
                throw new IllegalArgumentException("Invalid Interface Method: Expected format 'methodName(parameters) : returnType'.");
            }
        } else {
            throw new IllegalArgumentException("Unknown property type for Interface: " + type);
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

    private boolean isValidMethod(String value) {
        return value != null && value.matches("^\\w+\\s*\\([^)]*\\)\\s*:\\s*\\w+$");
    }
}