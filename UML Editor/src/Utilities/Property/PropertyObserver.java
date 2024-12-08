package Utilities.Property;

import java.io.Serializable;

public interface PropertyObserver extends Serializable {
    void updateFromProperty();
}
