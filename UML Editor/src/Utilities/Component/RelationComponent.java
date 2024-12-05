package Utilities.Component;

import Utilities.Diagram.Diagram;
import Utilities.Property.CoordianteProperty;
import Utilities.Property.Property;

import java.awt.*;
import java.util.ArrayList;


public abstract class RelationComponent extends Utilities.Component.Component {

    protected CoordianteProperty x_coordinate_2;
    protected CoordianteProperty y_coordinate_2;

    protected Utilities.Component.Component source;
    protected ArrayList<Utilities.Component.Component> targets;

    public RelationComponent(Diagram diagram) {
        super(diagram);
        targets = new ArrayList<>();
        x_coordinate_2 = new CoordianteProperty("X Coordinate 2",0,this);
        y_coordinate_2 = new CoordianteProperty("Y Coordinate 2",0,this);

        properties.add(x_coordinate_2);
        properties.add(y_coordinate_2);
        propertiesTypes.add("Source");
        propertiesTypes.add("Target");
    }

    public abstract Property createProperty(String type, String value) throws IllegalArgumentException;

    public abstract void renderComponent(Graphics g);

    public void setSource(Utilities.Component.Component component) {
        this.source = component;
    }

    public void setTarget(Utilities.Component.Component component) {
        targets.add(component);
    }

    public Utilities.Component.Component getSource() {
        return source;
    }

    public ArrayList<Component> getTargets() {
        return targets;
    }
}
