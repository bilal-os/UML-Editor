package UILayer;

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
        if ("Attribute".equals(type)) {
            if (!isValidAttribute(value)) {
                throw new IllegalArgumentException("Invalid syntax for Attribute. Expected: visibility name : type (e.g., + age : int)");
            }
        } else if ("Method".equals(type)) {
            if (!isValidMethod(value)) {
                throw new IllegalArgumentException("Invalid syntax for Method. Expected: visibility name(parameters) : returnType (e.g., + getAge() : int)");
            }
        }

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
