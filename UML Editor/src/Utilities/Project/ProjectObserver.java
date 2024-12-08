package Utilities.Project;

import java.io.Serializable;

public interface ProjectObserver extends Serializable {
    void updateFromProject();
}
