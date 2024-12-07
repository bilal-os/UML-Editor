package BusinessLogic.ClassDiagram.Properties;

import Utilities.Component.Component;

public class AbstractClassProperty extends ClassProperty {

    public AbstractClassProperty() {
        super();
    }

    public AbstractClassProperty(String type, String value, Component component) throws IllegalArgumentException {
        super(type, value, component);

        // Automatically add <<abstract>> for Abstract Method
        if ("Abstract Method".equals(type)) {
            this.value = value.contains("<<abstract>>") ? value : value + " <<abstract>>";
        }
    }

    @Override
    public void validateInput(String type, String value) throws IllegalArgumentException {
        if ("Abstract Method".equals(type)) {
            if (!isValidAbstractMethod(value)) {
                throw new IllegalArgumentException("Invalid Abstract Method syntax: expected 'visibility methodName(parameters) : returnType'");
            }
        } else {
            super.validateInput(type, value);
        }
    }

    @Override
    public void addValue(String value) throws IllegalArgumentException {
        validateInput(this.type, value);

        if ("Method".equals(this.type) || "Abstract Method".equals(this.type)) {
            this.value = value.contains("<<abstract>>") ? value : value + " <<abstract>>";
        } else {
            this.value = value;
        }
    }

    private boolean isValidAbstractMethod(String value) {
        return value.matches("^[+#\\-]\\s*\\w+\\s*\\([^)]*\\)\\s*:\\s*\\w+$");
    }
}