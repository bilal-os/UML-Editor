package UILayer;

import BusinessLogic.BusinessLogicInterface;
import UILayer.Canvas.Canvas;
import UILayer.ComponentPalette.ComponentPalette;
import UILayer.DiagramsPanel.DiagramsPanel;
import UILayer.MenuBar.MenuBar;
import UILayer.PropertiesPanel.PropertiesPanel;
import Utilities.CustomMessageDialog.CustomMessageDialog;
import Utilities.Diagram.Diagram;
import Utilities.Project.Project;
import Utilities.Component.Component;
import Utilities.Project.ProjectFileHandler;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Controller {
    private final BusinessLogicInterface businessLogic;
    protected MainWindow mainWindow;
    protected MenuBar menuBar;
    protected ComponentPalette componentPalette;
    protected DiagramsPanel diagramsPanel;
    protected PropertiesPanel propertiesPanel;
    protected Canvas canvas;

    public Controller(MainWindow mainWindow, BusinessLogicInterface businessLogic) {
        this.mainWindow = mainWindow;
        this.businessLogic = businessLogic;
        initializeComponents();
        setupActionListeners();
    }

    private void initializeComponents() {
        this.menuBar = mainWindow.getMenu();
        this.componentPalette = mainWindow.getComponentPalette();
        this.diagramsPanel = mainWindow.getDiagramsPanel();
        this.propertiesPanel = mainWindow.getPropertiesPanel();
        this.canvas = mainWindow.getCanvas();
    }

    private void setupActionListeners() {
        menuBar.addActionListeners(
                new FileActionListener(),
                new EditActionListener(),
                new ViewActionListener(),
                new DiagramActionListener(),
                new HelpActionListener()
        );
        diagramsPanel.addActionListeners(
                new ComponentPaletteListener(),
                new PropertiesPanelListener()
        );
        componentPalette.addActionListeners(new PropertiesPanelListener());
        canvas.addActionListeners(new PropertiesPanelListener());
    }

    // Utility methods for file selection
    private File selectDirectory(String title) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(title);
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int selection = fileChooser.showDialog(mainWindow, "Select");
        return selection == JFileChooser.APPROVE_OPTION
                ? fileChooser.getSelectedFile()
                : null;
    }

    private File selectJsonFile(String title) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(title);
        fileChooser.setFileFilter(new FileNameExtensionFilter("JSON Files", "json"));

        int selection = fileChooser.showOpenDialog(mainWindow);
        return selection == JFileChooser.APPROVE_OPTION
                ? fileChooser.getSelectedFile()
                : null;
    }

    // Listener classes
    private class PropertiesPanelListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Component component = (Component) actionEvent.getSource();
            propertiesPanel.displayProperties(component);
        }
    }

    private class ComponentPaletteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Diagram diagram = (Diagram) e.getSource();
            componentPalette.displayComponents(diagram);
            canvas.setDiagram(diagram);
        }
    }

    private class FileActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!(e.getSource() instanceof JMenuItem source)) return;

            try {
                switch (source.getText()) {
                    case "New Project" -> createNewProject();
                    case "Save Project" -> saveCurrentProject();
                    case "Open Project" -> openExistingProject();
                    case "Exit" -> exitApplication();
                }
            } catch (Exception ex) {
                handleActionException(ex, "File Operation Error");
            }
        }

        private void createNewProject() {
            String projectName = promptForProjectName();
            if (projectName == null) return;

            try {
                Project newProject = businessLogic.createProject(
                        businessLogic.getProjects().size(),
                        projectName.trim()
                );

                MainWindow newMainWindow = new MainWindow(newProject);
                new Controller(newMainWindow, businessLogic);
                newMainWindow.display();
            } catch (Exception ex) {
                handleActionException(ex, "New Project Creation Error");
            }
        }

        private String promptForProjectName() {
            String projectName = JOptionPane.showInputDialog(
                    mainWindow,
                    "Enter the name for the new project:",
                    "New Project",
                    JOptionPane.PLAIN_MESSAGE
            );

            if (projectName == null || projectName.trim().isEmpty()) {
                JOptionPane.showMessageDialog(
                        mainWindow,
                        "Project name cannot be empty!",
                        "Invalid Input",
                        JOptionPane.WARNING_MESSAGE
                );
                return null;
            }
            return projectName;
        }

        private void saveCurrentProject() {
            File saveDir = selectDirectory("Select Save Location");
            if (saveDir == null) {
                CustomMessageDialog.showInfo(mainWindow, "Save operation canceled.", "Info");
                return;
            }

            try {
                ProjectFileHandler.saveProject(mainWindow.getProject(), saveDir.getAbsolutePath());
                CustomMessageDialog.showSuccess(mainWindow, "Project saved successfully!", "Success");
            } catch (IOException ex) {
                CustomMessageDialog.showError(mainWindow, "Error saving project: " + ex.getMessage(), "Error");
            }
        }


        private void openExistingProject() {
            File jsonFile = selectJsonFile("Select a Project File");
            if (jsonFile == null) {
                CustomMessageDialog.showInfo(mainWindow, "Open operation canceled.", "Info");
                return;
            }

            try {
                Project openedProject = ProjectFileHandler.openProject(jsonFile.getAbsolutePath());
                MainWindow newMainWindow = new MainWindow(openedProject);
                new Controller(newMainWindow, businessLogic);
                newMainWindow.display();
            } catch (IOException ex) {
                CustomMessageDialog.showError(mainWindow, "Error opening project: " + ex.getMessage(), "Error");
            }
        }


        private void exitApplication() {
            int choice = JOptionPane.showConfirmDialog(
                    mainWindow,
                    "Do you want to save the project before exiting?",
                    "Exit Confirmation",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            switch (choice) {
                case JOptionPane.YES_OPTION -> saveAndExit();
                case JOptionPane.NO_OPTION -> System.exit(0);
                // Do nothing if user cancels
            }
        }

        private void saveAndExit() {
            File saveDir = selectDirectory("Select Save Directory");
            if (saveDir != null) {
                try {
                    businessLogic.saveProject(mainWindow.getProject(), saveDir.getAbsolutePath());
                    CustomMessageDialog.showSuccess(mainWindow, "Project saved successfully!", "Success");
                    System.exit(0);
                } catch (Exception ex) {
                    handleActionException(ex, "Project Exit Save Error");
                }
            } else {
                CustomMessageDialog.showInfo(mainWindow, "Save operation canceled. Application will not exit.", "Info");
            }
        }

        private void handleActionException(Exception ex, String context) {
            CustomMessageDialog.showError(mainWindow,
                    context + ": " + ex.getMessage(),
                    "Error"
            );
        }
    }

    private class EditActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!(e.getSource() instanceof JMenuItem source)) return;

            Diagram diagram = canvas.getDiagram();
            if (diagram == null) {
                CustomMessageDialog.showWarning(mainWindow, "No Diagram Selected!", "Warning");
                return;
            }

            try {
                switch (source.getText()) {
                    case "Generate Class Diagram Code" -> generateDiagramCode(diagram);
                    case "Save PNG" -> saveDiagramAsPng(diagram);
                    case "Save JPEG" -> saveDiagramAsJpeg(diagram);
                }
            } catch (Exception ex) {
                CustomMessageDialog.showError(mainWindow, ex.getMessage(), "Error");
            }
        }

        private void generateDiagramCode(Diagram diagram) {
            File saveDir = selectDirectory("Select Save Location for Generated Code");
            if (saveDir == null) {
                CustomMessageDialog.showInfo(mainWindow, "Operation canceled by the user.", "Info");
                return;
            }
            try {
                businessLogic.generateCode(diagram, saveDir.getAbsolutePath());
                CustomMessageDialog.showSuccess(mainWindow, "Diagram code generated successfully!", "Success");
            }
            catch (Exception ex) {
                CustomMessageDialog.showError(mainWindow, ex.getMessage(), "Code Generation Error");
            }
        }

        private void saveDiagramAsPng(Diagram diagram) {
            File saveDir = selectDirectory("Select Save Location for PNG");
            if (saveDir == null) {
                CustomMessageDialog.showInfo(mainWindow, "Operation canceled by the user.", "Info");
                return;
            }

            try {
                businessLogic.savePng(diagram, saveDir.getAbsolutePath());
                CustomMessageDialog.showSuccess(mainWindow, "Diagram saved as PNG successfully!", "Success");
            }
            catch (Exception ex) {
                CustomMessageDialog.showError(mainWindow, ex.getMessage(), "Save Png Error");
            }
        }

        private void saveDiagramAsJpeg(Diagram diagram) {
            File saveDir = selectDirectory("Select Save Location for JPG");
            if (saveDir == null) {
                CustomMessageDialog.showInfo(mainWindow, "Operation canceled by the user.", "Info");
                return;
            }

            try {
                businessLogic.saveJpg(diagram, saveDir.getAbsolutePath());
                CustomMessageDialog.showSuccess(mainWindow, "Diagram saved as JPG successfully!", "Success");

            } catch (Exception e) {
                CustomMessageDialog.showError(mainWindow, e.getMessage(), "Save JPG Error");
            }
        }
    }

    private class ViewActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!(e.getSource() instanceof JMenuItem source)) return;

            switch (source.getText()) {
                case "Zoom In" -> canvas.zoomIn();
                case "Zoom Out" -> canvas.zoomOut();
                case "Reset View" -> canvas.resetZoom();
            }
        }
    }

    private class DiagramActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!(e.getSource() instanceof JMenuItem source)) return;

            try {
                if ("Create Class Diagram".equals(source.getText())) {
                    businessLogic.createDiagram(mainWindow.getProject().getId(), "Class Diagram");
                }
            } catch (Exception ex) {
                CustomMessageDialog.showError(mainWindow, ex.getMessage(), "Error");
            }
        }
    }

    private class HelpActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Future implementation
        }
    }
}