package DataLayer;

import Utilities.Diagram.Diagram;
import Utilities.Project.Project;

public interface DataLayer {
    void saveProject(Project project, String filepath) throws Exception;
    Project loadProject(String filepath) throws Exception;
    void savePng(Diagram diagram, String filepath) throws Exception;
    void saveJpg(Diagram diagram, String filepath) throws Exception;
}
