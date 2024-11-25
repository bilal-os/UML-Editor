package BusinessLogic;

import Utilities.Property;

public class ClassProperty extends Property {

    public ClassProperty(String type, String value) throws IllegalArgumentException {
            super(type, value);
    }

    @Override
    public void setValue(String value) throws IllegalArgumentException {

        try {
            validateInput(this.type, value);
            this.value = value; // Assign if valid
        }
        catch (IllegalArgumentException e)
        {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void validateInput(String type, String value) throws IllegalArgumentException {

        // Validate based on property type
        if("Class Name".equals(type))
        {
            if (!isValidName(value)) {
                throw new IllegalArgumentException("Invalid syntax for Name. Expected: A non-empty name starting with an uppercase letter, followed by letters, numbers, or underscores.");
            }
        }
        else if ("Attribute".equals(type)) {
            if (!isValidAttribute(value)) {
                throw new IllegalArgumentException("Invalid syntax for Attribute. Expected: visibility name : type (e.g., + age : int)");
            }
        } else if ("Method".equals(type)) {
            if (!isValidMethod(value)) {
                throw new IllegalArgumentException("Invalid syntax for Method. Expected: visibility name(parameters) : returnType (e.g., + getAge() : int)");
            }
        }

    }
    private boolean isValidName(String value) {
        // Check if the value is null or empty
        if (value == null || value.trim().isEmpty()) {
            return false;
        }

        // Regular Expression to match valid class names
        // Explanation:
        // ^[A-Z]: Starts with an uppercase letter
        // [A-Za-z0-9_]*$: Followed by letters, numbers, or underscores
        String regex = "^[A-Z][A-Za-z0-9_]*$";

        // Return true if value matches the regex, otherwise false
        return value.matches(regex);
    }

    // Validate Attribute syntax: visibility name : type
    private boolean isValidAttribute(String value) {
        return value.matches("^[+#\\-]\\s*\\w+\\s*:\\s*\\w+$");
    }

    // Validate Method syntax: visibility name(parameters) : returnType
    private boolean isValidMethod(String value) {
        return value.matches("^[+#\\-]\\s*\\w+\\s*\\([^)]*\\)\\s*:\\s*\\w+$");
    }
}
