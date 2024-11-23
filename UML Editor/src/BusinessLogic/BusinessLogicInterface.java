package BusinessLogic;

import Utilities.*;

public interface BusinessLogicInterface {
    public Project openProject();
    public Project createProject(int id, String name);
    public boolean saveProject(Project project);
    public void createDiagram(int projectId, String type) throws Exception;
}
