package Utilities.Component;

import java.io.Serializable;

public interface ComponentObserver extends Serializable {
    void updateFromComponent(Component component);
}
