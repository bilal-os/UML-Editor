package BusinessLogic;

import Utilities.Diagram;

public class BusinessLogic implements BusinessLogicInterface {
    public Project openProject()
    {
        return null;
    }

    public Project createProject(String name)
    {
        return null;
    }

    public boolean saveProject(Project project)
    {
        return false;
    }

    public Diagram createDiagram(String type)
    {
        if(type.equals("Class Diagram"))
        {
            return new ClassDiagram();
        }

        return null;
    }

}
