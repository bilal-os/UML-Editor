package BusinessLogic;

import Utilities.Property;

public class EnumerationProperty extends Property {

    public EnumerationProperty(String type, String value) throws IllegalArgumentException {
        super(type, value);
    }

    @Override
    public void validateInput(String type, String value) throws IllegalArgumentException {
        if ("Enumeration Name".equals(type)) {
            if (!isValidName(value)) {
                throw new IllegalArgumentException("Invalid Enumeration Name: Must start with an uppercase letter.");
            }
        } else if ("Value".equals(type)) {
            if (!isValidValue(value)) {
                throw new IllegalArgumentException("Invalid Enumeration Value: Must be uppercase and alphanumeric.");
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
        return value != null && value.matches("^[A-Z][A-Za-z0-9_]*$");
    }

    private boolean isValidValue(String value) {
        return value != null && value.matches("^[A-Z0-9_]+$");
    }
}
