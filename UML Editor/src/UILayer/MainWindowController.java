package UILayer;

import BusinessLogic.BusinessLogicInterface;
import Utilities.CustomMessageDialog;
import Utilities.Diagram;
import Utilities.Project;
import Utilities.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainWindowController {
    private final BusinessLogicInterface businessLogic;
    protected MainWindow mainWindow;

    protected MenuBar menuBar;
    protected ComponentPalette componentPalette;
    protected DiagramsPanel diagramsPanel;
    protected PropertiesPanel propertiesPanel;


    public MainWindowController(MainWindow mainWindow, BusinessLogicInterface businessLogic) {

        this.mainWindow = mainWindow;
        this.businessLogic = businessLogic;

        this.menuBar=mainWindow.getMenu();
        this.componentPalette=mainWindow.getComponentPalette();
        this.diagramsPanel=mainWindow.getDiagramsPanel();
        this.propertiesPanel=mainWindow.getPropertiesPanel();



        initialize();
    }

    private void initialize() {
        menuBar.addActionListeners(new FileAction(), new EditAction(), new ViewAction(), new DiagramAction(), new HelpAction() );
        diagramsPanel.addActionListeners(new DiagramDisplayAction(), new DisplayPropertiesAction());
        componentPalette.addActionListeners(new DisplayPropertiesAction());
        propertiesPanel.addActionListeners(new RenderDiagramsPanel());
    }

    //ActionListener Classes for ComponentPalette

    private class RenderDiagramsPanel implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            diagramsPanel.displayDiagrams(mainWindow.getProject().getDiagrams());
        }
    }

    private class DisplayPropertiesAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Component component = (Component) actionEvent.getSource();
            propertiesPanel.displayProperties(component);
            diagramsPanel.displayDiagrams(mainWindow.getProject().getDiagrams());
        }
    }

    //ActionListener Classes for DiagramPanels
    private class DiagramDisplayAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Diagram diagram = (Diagram) e.getSource();
            componentPalette.displayComponents(diagram);
        }
    }

    // ActionListener classes for MenuBar
    private class FileAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof JMenuItem source) {
                if ("New Project".equals(source.getText())) {
                    try {
                        // Create a new project
                        Project newProject = businessLogic.createProject(businessLogic.getProjects().size(), "New Project");

                        // Create a new main window and controller for the project
                        MainWindow newMainWindow = new MainWindow(newProject);
                        MainWindowController newController = new MainWindowController(newMainWindow, businessLogic);

                        // Display the new main window
                        newMainWindow.display();
                    } catch (Exception ex) {
                        CustomMessageDialog.showError(mainWindow, ex.getMessage(), "Error");
                    }
                }
            }
        }
    }

    private class EditAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class ViewAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class DiagramAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Assuming the source is a JMenuItem
            if (e.getSource() instanceof JMenuItem source) {
                if ("Create Class Diagram".equals(source.getText())) {
                    System.out.println("Creating class diagram...");
                    try {
                        businessLogic.createDiagram(mainWindow.getProject().getId(),"Class Diagram");
                        diagramsPanel.displayDiagrams(mainWindow.getProject().getDiagrams());
                    } catch (Exception ex) {
                        CustomMessageDialog.showError(mainWindow,ex.getMessage(),"Error");
                    }
                }
            }
        }
    }

    private class HelpAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

}
