package BusinessLogic;

import Utilities.Property;

public class InterfaceProperty extends Property {

    public InterfaceProperty(String type, String value) throws IllegalArgumentException {
        super(type, value);
    }

    @Override
    public void validateInput(String type, String value) throws IllegalArgumentException {
        if ("Interface Name".equals(type)) {
            if (!isValidName(value)) {
                throw new IllegalArgumentException("Invalid Interface Name: Must start with an uppercase letter.");
            }
        } else if ("Method".equals(type)) {
            if (!isValidMethod(value)) {
                throw new IllegalArgumentException("Invalid Interface Method: Expected syntax 'name(parameters) : returnType'.");
            }
        } else {
            throw new IllegalArgumentException("Unknown property type for Interface: " + type);
        }
    }

    @Override
    public void setValue(String value) throws IllegalArgumentException {
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
