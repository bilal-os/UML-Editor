package Utilities;

public abstract class Property {
    protected String type;
    protected String value;

    public  Property(String type, String value) throws IllegalArgumentException{

        try{
            validateInput(type, value);
            this.type = type;
            this.value = value;
        }
       catch (IllegalArgumentException e)
       {
           throw new IllegalArgumentException(e);
       }

    }

    public String gettype() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public abstract void validateInput(String type, String value) throws IllegalArgumentException;

    public abstract void setValue(String value) throws IllegalArgumentException;
}
