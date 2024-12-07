package Utilities.Property;

import Utilities.Component.Component;

public class CoordinateProperty extends Property {

    public CoordinateProperty()
    {
        super();
    }

    public CoordinateProperty(String type, int value, Component component) {
        super(type,String.valueOf(value),component);
    }

    @Override
    public void validateInput(String type, String value) throws IllegalArgumentException {

                if(Double.parseDouble(value)<0)
                {
                    throw new IllegalArgumentException("Enter a valid coordinate");
                }
    }

    @Override
    public void addValue(String value) throws IllegalArgumentException {
        try {
            validateInput(this.type, value);
            this.value = value;
            notifyObservers();// Assign if valid
        }
        catch (IllegalArgumentException e)
        {
            throw new IllegalArgumentException(e);
        }
    }
}
