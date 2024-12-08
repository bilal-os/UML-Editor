//package UILayer;
//
//import BusinessLogic.BusinessLogic;
//import BusinessLogic.BusinessLogicInterface;
//import Utilities.Project.Project;
//import Utilities.Project.ProjectFileHandler;
//
//import java.io.IOException;
//
//public class Main {
//    public static void main(String[] args) {
//
//        BusinessLogicInterface businessLogicInterface = new BusinessLogic();
//        Project project = businessLogicInterface.createProject(0, "Project 101");
//        MainWindow mainWindow = new MainWindow(project);
//        Controller controller = new Controller(mainWindow,businessLogicInterface);
//        mainWindow.display();
//
//
//    }
//}


package UILayer;

import BusinessLogic.BusinessLogic;
import BusinessLogic.BusinessLogicInterface;
import Utilities.Project.Project;
import Utilities.Project.ProjectFileHandler;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        BusinessLogicInterface businessLogicInterface = new BusinessLogic();

        // Define the file path for saving and loading the project
        String projectFilePath = "C:/projects/Project 101.json";  // You can adjust this path based on your needs

        Project project;

        // Try loading the project from a file if it exists
        try {
            project = ProjectFileHandler.openProject(projectFilePath);
            System.out.println("Project loaded from file: " + project.getName());
        } catch (IOException e) {
            System.out.println("No saved project found. Creating a new one.");
            // If no file is found, create a new project
            project = businessLogicInterface.createProject(0, "Project 101");
        }

        // Initialize the UI components
        MainWindow mainWindow = new MainWindow(project);
        Controller controller = new Controller(mainWindow, businessLogicInterface);

        // Display the main window
        mainWindow.display();

        // Save the project to a file when closing or exiting the application
        // This logic might be placed in the exit handler, but here is a basic demonstration:
        try {
            // Save the project to the file
            ProjectFileHandler.saveProject(project, "C:/projects");
            System.out.println("Project saved to file: " + project.getName());
        } catch (IOException e) {
            System.out.println("Failed to save the project: " + e.getMessage());
        }
    }
}
