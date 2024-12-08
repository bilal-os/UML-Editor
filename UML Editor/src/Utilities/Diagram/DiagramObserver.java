package Utilities.Diagram;
import java.io.Serializable;


public interface DiagramObserver extends Serializable {
    void updateFromDiagram();
}
