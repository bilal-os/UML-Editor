package Utilities;

import BusinessLogic.CoordianteProperty;

import java.awt.*;
import java.awt.event.ActionListener;


public abstract class RelationComponent extends Component {

    protected CoordianteProperty x_coordinate_2;
    protected CoordianteProperty y_coordinate_2;

    protected Component source;
    protected Component target;

    public RelationComponent(Diagram diagram) {
        super(diagram);
        x_coordinate_2 = new CoordianteProperty("X Coordinate 2",0,this);
        y_coordinate_2 = new CoordianteProperty("Y Coordinate 2",0,this);

        properties.add(x_coordinate_2);
        properties.add(y_coordinate_2);
        propertiesTypes.add("Source");
        propertiesTypes.add("Target");
    }

    public abstract Property createProperty(String type, String value) throws IllegalArgumentException;

    public abstract void renderComponent(Graphics g);

    public void setSource(Component component) {
        this.source = component;
    }
    public void setTarget(Component component) {
        this.target = component;
    }
    public Component getSource() {
        return source;
    }

    public Component getTarget() {
        return target;
    }
}
