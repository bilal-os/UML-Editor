package BusinessLogic;

import Utilities.Component;
import Utilities.Relation;
import Utilities.RelationType;

import java.awt.*;

public class Association implements Relation {
    private Component source;
    private Component destination;
    private RelationType type;

    public Association(Component source, Component destination) {
        this.source = source;
        this.destination = destination;
        this.type = RelationType.ASSOCIATION;
    }

    @Override
    public Component getSource() {
        return source;
    }

    @Override
    public Component getDestination() {
        return destination;
    }

    @Override
    public RelationType getType() {
        return type;
    }

    @Override
    public void render(Graphics2D g2d)
    {

    }

}
