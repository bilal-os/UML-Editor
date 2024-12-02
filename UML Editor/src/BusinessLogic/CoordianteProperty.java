package BusinessLogic;

import Utilities.Property;

public class CoordianteProperty extends Property {

    public CoordianteProperty(String type, int value)
    {
        super(type,String.valueOf(value));
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
