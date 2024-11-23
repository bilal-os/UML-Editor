package BusinessLogic;

import Utilities.Property;

public class RelationshipProperty extends Property {

    public RelationshipProperty(String type, String value) throws IllegalArgumentException {
        super(type, value);
    }

    @Override
    public void validateInput(String type, String value) throws IllegalArgumentException {
        switch (type) {
            case "Source":
            case "Target":
                if (!isValidClassName(value)) {
                    throw new IllegalArgumentException(
                            type + " must be a valid class name (e.g., starts with an uppercase letter).");
                }
                break;
            case "Relationship Type":
                if (!isValidRelationshipType(value)) {
                    throw new IllegalArgumentException(
                            "Invalid relationship type. Expected: Association, Aggregation, Composition, or Inheritance.");
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown property type: " + type);
        }
    }

    @Override
    public void setValue(String value) throws IllegalArgumentException {
        validateInput(this.type, value);
        this.value = value;
    }

    private boolean isValidClassName(String value) {
        return value != null && value.matches("^[A-Z][A-Za-z0-9_]*$");
    }

    private boolean isValidRelationshipType(String value) {
        return value != null && (value.equals("Association") ||
                value.equals("Aggregation") ||
                value.equals("Composition") ||
                value.equals("Inheritance"));
    }
}
