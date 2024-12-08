package BusinessLogic;

import BusinessLogic.ClassDiagram.Components.AbstractClass;
import BusinessLogic.ClassDiagram.Components.Enumeration;
import BusinessLogic.ClassDiagram.Components.Interface;
import BusinessLogic.ClassDiagram.Components.Class;
import BusinessLogic.ClassDiagram.Diagram.ClassDiagram;
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

        if(!(diagram instanceof ClassDiagram))
        {
            throw new Exception("Class Diagram not selected");
        }

        System.out.println("Generating code for " + diagram.getName());
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

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void generateCode(Diagram diagram) {
        // Ensure the diagram is a ClassDiagram
        if (!(diagram instanceof ClassDiagram)) {
            throw new IllegalArgumentException("Diagram must be a Class Diagram");
        }

        // Map to store generated class files
        Map<String, StringBuilder> generatedFiles = new HashMap<>();

        // Iterate through components to generate code
        for (Component component : diagram.getComponents()) {
            if (component instanceof Class) {
                generateClassCode((Class) component, generatedFiles);
            } else if (component instanceof Interface) {
                generateInterfaceCode((Interface) component, generatedFiles);
            } else if (component instanceof Enumeration) {
                generateEnumCode((Enumeration) component, generatedFiles);
            } else if (component instanceof AbstractClass) {
                generateAbstractClassCode((AbstractClass) component, generatedFiles);
            }
        }

        // Write generated files to disk
        for (Map.Entry<String, StringBuilder> entry : generatedFiles.entrySet()) {
            writeCodeToFile(entry.getKey(), entry.getValue().toString());
        }
    }

    private void generateClassCode(Class classComponent, Map<String, StringBuilder> generatedFiles) {
        String className = getPropertyValue(classComponent, "Class Name");
        StringBuilder classCode = new StringBuilder();

        // Package declaration (you might want to extract this from the diagram or component)
        classCode.append("package com.example;\n\n");

        // Class declaration
        classCode.append("public class ").append(className).append(" {\n");

        // Generate attributes
        for (Property prop : classComponent.getProperties()) {
            if (prop.gettype().equals("Attribute")) {
                classCode.append("    private ")
                        .append(extractDataType(prop.getValue()))
                        .append(" ")
                        .append(extractVariableName(prop.getValue()))
                        .append(";\n");
            }
        }

        // Generate constructor
        classCode.append("\n    public ").append(className).append("() {\n");
        classCode.append("        // Default constructor\n");
        classCode.append("    }\n");

        // Generate methods
        for (Property prop : classComponent.getProperties()) {
            if (prop.gettype().equals("Method")) {
                String methodSignature = prop.getValue();
                classCode.append("\n    ").append(methodSignature).append(" {\n");
                classCode.append("        // TODO: Implement method body\n");
                classCode.append("    }\n");
            }
        }

        classCode.append("}\n");

        // Store the generated code
        generatedFiles.put(className + ".java", classCode);
    }

    private void generateInterfaceCode(Interface interfaceComponent, Map<String, StringBuilder> generatedFiles) {
        String interfaceName = getPropertyValue(interfaceComponent, "Interface Name");
        StringBuilder interfaceCode = new StringBuilder();

        interfaceCode.append("package com.example;\n\n");
        interfaceCode.append("public interface ").append(interfaceName).append(" {\n");

        // Generate method signatures
        for (Property prop : interfaceComponent.getProperties()) {
            if (prop.gettype().equals("Method")) {
                interfaceCode.append("    ")
                        .append(prop.getValue())
                        .append(";\n");
            }
        }

        interfaceCode.append("}\n");

        generatedFiles.put(interfaceName + ".java", interfaceCode);
    }

    private void generateEnumCode(Enumeration enumComponent, Map<String, StringBuilder> generatedFiles) {
        String enumName = getPropertyValue(enumComponent, "Enum Name");
        StringBuilder enumCode = new StringBuilder();

        enumCode.append("package com.example;\n\n");
        enumCode.append("public enum ").append(enumName).append(" {\n");

        // Generate enum constants
        boolean first = true;
        for (Property prop : enumComponent.getProperties()) {
            if (prop.gettype().equals("Enum Constant")) {
                if (!first) {
                    enumCode.append(",\n");
                }
                enumCode.append("    ").append(prop.getValue());
                first = false;
            }
        }

        enumCode.append("\n}\n");

        generatedFiles.put(enumName + ".java", enumCode);
    }

    private void generateAbstractClassCode(AbstractClass abstractClassComponent, Map<String, StringBuilder> generatedFiles) {
        String className = getPropertyValue(abstractClassComponent, "Abstract Class Name");
        StringBuilder classCode = new StringBuilder();

        classCode.append("package com.example;\n\n");
        classCode.append("public abstract class ").append(className).append(" {\n");

        // Generate attributes
        for (Property prop : abstractClassComponent.getProperties()) {
            if (prop.gettype().equals("Attribute")) {
                classCode.append("    protected ")
                        .append(extractDataType(prop.getValue()))
                        .append(" ")
                        .append(extractVariableName(prop.getValue()))
                        .append(";\n");
            }
        }

        // Generate abstract methods
        for (Property prop : abstractClassComponent.getProperties()) {
            if (prop.gettype().equals("Method")) {
                classCode.append("\n    public abstract ")
                        .append(prop.getValue())
                        .append(";\n");
            }
        }

        classCode.append("}\n");

        generatedFiles.put(className + ".java", classCode);
    }

    private void writeCodeToFile(String fileName, String content) {
        try {
            Path directory = Paths.get("generated-code");
            Files.createDirectories(directory);
            Path filePath = directory.resolve(fileName);
            Files.write(filePath, content.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("Generated file: " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing file: " + fileName);
            e.printStackTrace();
        }
    }

    // Utility methods for parsing property values
    private String getPropertyValue(Component component, String propertyName) {
        for (Property prop : component.getProperties()) {
            if (prop.gettype().equals(propertyName)) {
                return prop.getValue();
            }
        }
        return "Unknown" + propertyName.replace(" ", "");
    }

    private String extractDataType(String attributeOrMethod) {
        // Simple parsing logic - you might want to enhance this
        String[] parts = attributeOrMethod.split(":");
        return parts.length > 1 ? parts[1].trim() : "Object";
    }

    private String extractVariableName(String attributeOrMethod) {
        // Simple parsing logic - you might want to enhance this
        String[] parts = attributeOrMethod.split(":");
        return parts[0].trim();
    }



}