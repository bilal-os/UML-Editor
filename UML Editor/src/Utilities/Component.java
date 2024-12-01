package Utilities;

import BusinessLogic.CoordianteProperty;

import java.awt.*;
import java.util.ArrayList;

public abstract class Component implements Subject{

    protected ArrayList<Property> properties;
    protected ArrayList<String> propertiesTypes;
    protected CoordianteProperty x_coordinate;
    protected CoordianteProperty y_coordinate;
    protected ArrayList<Observer> observers;

    public Component() {
        x_coordinate = new CoordianteProperty("X Coordinate",50);
        y_coordinate = new CoordianteProperty("Y Coordinate",50);
        properties = new ArrayList<>();
        propertiesTypes = new ArrayList<>();
        observers = new ArrayList<>();
        properties.add(x_coordinate);
        properties.add(y_coordinate);
        propertiesTypes.add("X Coordinate");
        propertiesTypes.add("Y Coordinate");
    }

    public void addObserver(Observer observer){
        observers.add(observer);
    }

    public void removeObserver(Observer observer){
        observers.remove(observer);
    }

    public void notifyObservers(){
        for(Observer observer : observers){
            observer.update();
        }
    }

    public String getName() {
        for (Property property : properties) {
            if (property.gettype().contains("Name")) {
               return property.getValue();
            }
        }
        return "";
    }

    public Property getNameProperty(){
        for (Property property : properties) {
            if (property.gettype().contains("Name")) {
                return property;
            }
        }
        return null;
    }

    public ArrayList<String> getPropertiesTypes() {
        return propertiesTypes;
    }

    public abstract void createProperty(String type, String value) throws IllegalArgumentException ;

    public void addProperty(String type, String value) throws IllegalArgumentException
    {
        createProperty(type, value);
        notifyObservers();
    }


    public ArrayList<Property> getProperties() {
        return properties;
    }

    public void removeProperty(Property property) {
        properties.remove(property);
        notifyObservers();
    }

    public abstract void renderComponent(Graphics g);

    public Property getXCoordinate() {
        return x_coordinate;
    }

    public Property getYCoordinate() {
        return y_coordinate;
    }

}