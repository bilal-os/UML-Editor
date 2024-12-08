package UILayer;

import BusinessLogic.BusinessLogic;
import BusinessLogic.BusinessLogicInterface;
import DataLayer.JSONDataLayer;
import Utilities.Project.Project;
import DataLayer.*;
import javax.xml.crypto.Data;

public class Main {
    public static void main(String[] args) {

        DataLayer dataLayer = new JSONDataLayer();
        BusinessLogicInterface businessLogicInterface = new BusinessLogic(dataLayer);
        Project project = businessLogicInterface.createProject(0, "Project 101");
        MainWindow mainWindow = new MainWindow(project);
        Controller controller = new Controller(mainWindow,businessLogicInterface);
        mainWindow.display();
    }
}
