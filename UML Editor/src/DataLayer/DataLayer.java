package DataLayer;

import Utilities.Project.Project;

public interface DataLayer {
    void saveProject(Project project, String filepath) throws Exception;
    Project loadProject(String filepath) throws Exception;
}
