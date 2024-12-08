package BusinessLogic.ClassDiagram.Properties;

import Utilities.Component.Component;
import Utilities.Property.Property;

public class InterfaceProperty extends Property {

    public InterfaceProperty() {
        super();
    }

    public InterfaceProperty(String type, String value, Component component) throws IllegalArgumentException {
        super(type, value, component);
    }

    @Override
    public void validateInput(String type, String value) throws IllegalArgumentException {
        if ("Interface Name".equals(type)) {
            if (!isValidName(value)) {
                throw new IllegalArgumentException("Invalid Interface Name: Must start with an uppercase letter and contain only letters, numbers, or underscores.");
            }
        } else if ("Method".equals(type)) {
            if (!isValidMethod(value)) {
                throw new IllegalArgumentException("Invalid Interface Method: Expected format '+methodName(parameters) : returnType'. Method name must start with '+'.");
            }
        } else if ("Width".equals(type) || "Height".equals(type)) {
            try {
                Integer.parseInt(value); // Validate numeric input
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Width and Height must be numeric values.");
            }
        } else {
            throw new IllegalArgumentException("Unknown property type for Interface: " + type);
        }
    }

    @Override
    public void addValue(String value) throws IllegalArgumentException {
        if (this.type == null || this.type.isEmpty()) {
            throw new IllegalArgumentException("Property type must be set before adding a value.");
        }
        validateInput(this.type, value);
        this.value = value;
    }

    private boolean isValidName(String value) {
        return value != null && value.matches("^[A-Z][A-Za-z0-9_]*$");
    }

    private boolean isValidMethod(String value) {
        // Ensure method name starts with '+' and does not allow other access modifiers
        return value != null && value.matches("^\\+\\s*\\w+\\s*\\([^)]*\\)\\s*:\\s*\\w+(<\\w+(,\\s*\\w+)*>)?$");
    }
}
