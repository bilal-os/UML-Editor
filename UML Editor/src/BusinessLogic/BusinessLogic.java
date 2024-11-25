package BusinessLogic;

import Utilities.*;

import java.util.ArrayList;

public class BusinessLogic implements BusinessLogicInterface {

    private ArrayList<Project> projects;

    public ArrayList<Project> getProjects() {
        return projects;
    }

    public BusinessLogic()
    {
        projects = new ArrayList<Project>();
    }

    public Project openProject()
    {
        return new Project(1,"dummy");
    }

    public Project createProject(int id, String name)
    {
        Project project = new Project(id,name);
        projects.add(project);
        return project;
    }

    public boolean saveProject(Project project)
    {
        return false;
    }

    public void createDiagram(int projectId, String type) throws Exception {
        boolean found =false;
        for (Project project : projects) {
            if (project.getId() == projectId) {
                found = true;
                if (type.equals("Class Diagram")) {
                    project.addDiagram(new ClassDiagram());
                }
                break;
            }
        }

        if(!found)
        {
            throw new Exception("No such Project Exists");
        }

    }

}