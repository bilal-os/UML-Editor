package BusinessLogic;

import BusinessLogic.ClassDiagram.Components.AbstractClass;
import BusinessLogic.ClassDiagram.Components.Enumeration;
import BusinessLogic.ClassDiagram.Components.Interface;
import BusinessLogic.ClassDiagram.Components.Class;
import BusinessLogic.ClassDiagram.Diagram.ClassDiagram;
import BusinessLogic.CodeGeneration.DiagramCodeGenerator;
import BusinessLogic.CodeGeneration.DiagramCodeGeneratorFactory;
import DataLayer.DataLayer;
import Utilities.Component.Component;
import Utilities.Diagram.Diagram;
import Utilities.Project.Project;
import Utilities.Property.Property;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BusinessLogic implements BusinessLogicInterface {

     DataLayer dataLayerInterface;

    private ArrayList<Project> projects;
     public BusinessLogic(DataLayer dataLayerInterface) {
         this.dataLayerInterface = dataLayerInterface;
         projects = new ArrayList<>();
     }

    public ArrayList<Project> getProjects() {
        return projects;
    }

    public BusinessLogic()
    {
        projects = new ArrayList<Project>();
    }

    public Project openProject(String projectFilePath) throws Exception {
        //open the project from the file
        // add the open project to the projects list
        //throw exception if an error occurs
        System.out.println("Opening Project: " + projectFilePath);
        //dummy opening of a project
        return dataLayerInterface.loadProject(projectFilePath);
    }

    public Project createProject(int id, String name)   {
        Project project = new Project(id,name);
        projects.add(project);
        return project;
    }

    public void saveProject(Project project, String fileLocation) throws Exception {
        //save project logic here
        //throw exception if an error occurs
        System.out.println("Saving project" + project.getName() + " to the " + fileLocation);
        dataLayerInterface.saveProject(project,fileLocation);
    }

    public void createDiagram(int projectId, String type) throws Exception {
        boolean found =false;
        for (Project project : projects) {
            if (project.getId() == projectId) {
                found = true;
                if (type.equals("Class Diagram")) {
                    project.addDiagram(new ClassDiagram(project.getDiagrams().size()));
                }
                break;
            }
        }

        if(!found)
        {
            throw new Exception("No such Project Exists");
        }

    }

    public void generateCode(Diagram diagram, String fileLocation) throws Exception {

        System.out.println("Generating code for " + diagram.getName());
        DiagramCodeGenerator generator = DiagramCodeGeneratorFactory.getDiagramCodeGenerator(diagram);
        generator.generateDiagramCode(fileLocation,diagram);

        // logic for generating code for a diagram
    }

    @Override
    public void savePng(Diagram diagram, String fileLocation) throws Exception {
        System.out.println("Saving file png " + fileLocation);
        dataLayerInterface.savePng(diagram,fileLocation);
    }

    @Override
    public void saveJpg(Diagram diagram, String fileLocation) throws Exception {
    System.out.println("Saving file jpg " + fileLocation);
    dataLayerInterface.saveJpg(diagram,fileLocation);
    }


}