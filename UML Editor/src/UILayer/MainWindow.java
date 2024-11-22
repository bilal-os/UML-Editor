package UILayer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import BusinessLogic.BusinessLogic;
import BusinessLogic.BusinessLogicInterface;
import Utilities.Diagram;
import Utilities.Project;

public class MainWindow extends JFrame {
    // UI Components
    private MenuBar menuBar;
    private Canvas canvas;
    private PropertiesPanel propertiesPanel;
    private ComponentPalette componentPalette;
    private DiagramsPanel diagramsPanel;
    private JPanel workspacePanel;
    private JPanel sidePanel;

    // Business Logic and Data
    private final BusinessLogicInterface businessLogic;
    private final ArrayList<Diagram> diagrams;
    private final Project project;

    public MainWindow(BusinessLogicInterface businessLogic) {
        // Initialize business logic and project
        this.businessLogic = businessLogic;
        this.project = new Project(1, "Project 101");
        this.diagrams = project.getDiagrams();

        // Set up the main window
        initializeFrame();

        // Initialize UI components
        initializePanels();
        initializeMenuBar();


        // Assemble the UI
        assembleWorkspace();
        addStatusBar(project.getName());
    }

    /**
     * Initializes the main window frame.
     */
    private void initializeFrame() {
        setTitle("UML Diagram Designer");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15)); // Layout with spacing
    }

    /**
     * Initializes the menu bar.
     */
    private void initializeMenuBar() {
        menuBar = new MenuBar(diagrams, businessLogic, diagramsPanel);
        setJMenuBar(menuBar);
    }

    /**
     * Initializes all panels and UI components.
     */
    private void initializePanels() {
        // Initialize core components
        componentPalette = new ComponentPalette();
        diagramsPanel = new DiagramsPanel(diagrams, componentPalette);
        canvas = new Canvas();
        propertiesPanel = new PropertiesPanel();

        // Set up side panel
        sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setPreferredSize(new Dimension(250, 600));
        sidePanel.add(diagramsPanel);
        sidePanel.add(Box.createVerticalStrut(10)); // Vertical spacing
        sidePanel.add(componentPalette);

        // Configure canvas
        canvas.setPreferredSize(new Dimension(700, 600));
        canvas.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        // Create workspace panel
        workspacePanel = new JPanel(new BorderLayout(10, 10));
        workspacePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    /**
     * Assembles the workspace by adding panels to the main frame.
     */
    private void assembleWorkspace() {
        workspacePanel.add(canvas, BorderLayout.CENTER);
        workspacePanel.add(propertiesPanel, BorderLayout.EAST);
        workspacePanel.add(sidePanel, BorderLayout.WEST);

        add(workspacePanel, BorderLayout.CENTER);
    }

    /**
     * Adds a status bar to the main window.
     */
    private void addStatusBar(String text) {
        JLabel statusBar = new JLabel(text, SwingConstants.LEFT);
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        add(statusBar, BorderLayout.SOUTH);
    }

    /**
     * Displays the main window.
     */
    public void display() {
        pack();
        setVisible(true);
    }

    /**
     * Refreshes the UI components.
     */
    public void refresh() {
        SwingUtilities.updateComponentTreeUI(this);
    }

    /**
     * Closes the main window.
     */
    public void close() {
        dispose();
    }

    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow(new BusinessLogic());
        mainWindow.display();
    }
}
