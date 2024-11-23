package UILayer;

import BusinessLogic.BusinessLogic;
import BusinessLogic.BusinessLogicInterface;
import Utilities.Project;

public class Main {
    public static void main(String[] args) {

        BusinessLogicInterface businessLogicInterface = new BusinessLogic();
        Project project = businessLogicInterface.createProject(1, "Project 101");
        MainWindow mainWindow = new MainWindow(businessLogicInterface,project);
        MainWindowController mainWindowController = new MainWindowController(mainWindow,businessLogicInterface);
        mainWindow.display();
    }
}
