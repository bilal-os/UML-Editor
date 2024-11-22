package BusinessLogic;

import Utilities.Diagram;

public interface BusinessLogicInterface {
    public Project openProject();
    public Project createProject(String name);
    public boolean saveProject(Project project);
    public Diagram createDiagram(String type);
}
