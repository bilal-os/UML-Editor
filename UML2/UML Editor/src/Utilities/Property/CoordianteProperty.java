package Utilities.Property;

import Utilities.Component.Component;
import java.io.Serializable;

public class CoordianteProperty extends Property implements Serializable {

    // Mark Component as transient if it's not serializable
    private transient Component component;

    public CoordianteProperty(String type, int value, Component component) {
        super(type, String.valueOf(value), component);
        this.component = component; // Save the component instance
    }

    @Override
    public void validateInput(String type, String value) throws IllegalArgumentException {
        try {
            if (Double.parseDouble(value) < 0) {
                throw new IllegalArgumentException("Enter a valid coordinate");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Value is not a valid number");
        }
    }

    @Override
    public void addValue(String value) throws IllegalArgumentException {
        try {
            validateInput(this.type, value);
            this.value = value;
            notifyObservers(); // Assign if valid
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e);
        }
    }

    // Optionally, create getter/setter for `component` if needed to access or modify it
    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }
}
