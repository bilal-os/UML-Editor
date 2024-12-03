package Utilities;

import java.awt.*;

public interface Relation {
    Component getSource();
    Component getDestination();
    RelationType getType();
    void render(Graphics2D g2d);
}
