package Utilities;

import java.util.ArrayList;

public abstract class Property implements Subject{
    protected String type;
    protected String value;
    protected ArrayList<Observer> observers;

    public Property(String type, String value) throws IllegalArgumentException{

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

    public void addObserver(Observer observer)
    {
        observers.add(observer);
    }
    public void removeObserver(Observer observer)
    {
        observers.remove(observer);
    }
    public void notifyObservers() {
        for(Observer observer : observers)
        {
            observer.update();
        }
    }

}
