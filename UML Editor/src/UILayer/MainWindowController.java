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
    protected DiagramsPanel1 diagramsPanel;
    protected PropertiesPanel propertiesPanel;
    protected Canvas canvas;

    public MainWindowController(MainWindow mainWindow, BusinessLogicInterface businessLogic) {

        this.mainWindow = mainWindow;
        this.businessLogic = businessLogic;

        this.menuBar=mainWindow.getMenu();
        this.componentPalette=mainWindow.getComponentPalette();
        this.diagramsPanel=mainWindow.getDiagramsPanel();
        this.propertiesPanel=mainWindow.getPropertiesPanel();
        this.canvas=mainWindow.getCanvas();

        initialize();
    }

    private void initialize() {
        menuBar.addActionListeners(new FileAction(), new EditAction(), new ViewAction(), new DiagramAction(), new HelpAction() );
        diagramsPanel.addActionListeners(new componentPaletteListener(), new propertiesPanelListener());
        componentPalette.addActionListeners(new propertiesPanelListener());
        canvas.addActionListeners(new propertiesPanelListener());
    }

    //ActionListener Classes for ComponentPalette

    private class propertiesPanelListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Component component = (Component) actionEvent.getSource();
            propertiesPanel.displayProperties(component);
        }
    }

    //ActionListener Classes for DiagramPanels
    private class componentPaletteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Diagram diagram = (Diagram) e.getSource();
            componentPalette.displayComponents(diagram);
            canvas.setDiagram(diagram);
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
            if(e.getSource() instanceof JMenuItem source) {
                if ("Zoom In".equals(source.getText())) {
                    System.out.println("Zoom In");
                    canvas.zoomIn();
                } else if ("Zoom Out".equals(source.getText())) {
                    System.out.println("Zoom Out");
                    canvas.zoomOut();
                } else if ("Reset View".equals(source.getText())) {
                    System.out.println("Reset View");
                    canvas.resetZoom();
                }
            }
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
