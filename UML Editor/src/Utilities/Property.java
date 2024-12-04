package Utilities;

import java.util.ArrayList;

public abstract class Property {
    protected String type;
    protected String value;
    protected ArrayList<PropertyObserver> observers;
    protected Component associatedComponent;

    public Property(String type, String value, Component associatedComponent) throws IllegalArgumentException{

        try{
            this.associatedComponent = associatedComponent;
            validateInput(type, value);
            this.type = type;
            this.value = value;
            observers = new ArrayList<>();
        }
       catch (IllegalArgumentException e)
       {
           throw new IllegalArgumentException(e);
       }

    }

    public Component getAssociatedComponent() {
        return associatedComponent;
    }

    public void setAssociatedComponent(Component associatedComponent) {
        this.associatedComponent = associatedComponent;
    }

    public String gettype() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public abstract void validateInput(String type, String value) throws IllegalArgumentException;

    public abstract void addValue(String value) throws IllegalArgumentException;

    public void setValue(String value) throws IllegalArgumentException {
        addValue(value);
        notifyObservers();
    }

    public void addObserver(PropertyObserver observer)
    {
        observers.add(observer);
    }

    public void removeObserver(PropertyObserver observer)
    {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for(PropertyObserver observer : observers)
        {
            observer.updateFromProperty();
        }
    }
}
