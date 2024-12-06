package BusinessLogic;

import Utilities.Diagram.Diagram;
import Utilities.Project.Project;

import java.util.ArrayList;

public interface BusinessLogicInterface {
    public Project openProject(String projectFileName) throws Exception;
    public Project createProject(int id, String name);
    public void saveProject(Project project, String fileLocation) throws Exception;
    public void createDiagram(int projectId, String type) throws Exception;
    public ArrayList<Project> getProjects();
    public void generateCode(Diagram diagram, String fileLocation) throws Exception;
    public void savePng(Diagram diagram, String fileLocation) throws Exception;
    public void saveJpg(Diagram diagram, String fileLocation) throws Exception;
}
