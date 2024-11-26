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
    private DiagramsPanel1 diagramsPanel;
    private JPanel workspacePanel;
    private JPanel sidePanel;

    // Business Logic and Data

    private final Project project;

    public MainWindow( Project project) {
        // Initialize business logic and project
        this.project = project;

        // Set up the main window
        initializeFrame();

        // Initialize UI components
        initializePanels();
        initializeMenuBar();

        // Assemble the UI
        assembleWorkspace();
        addStatusBar(project.getName());
        addCustomCloseOperation();
    }
    private void addCustomCloseOperation() {
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE); // Prevent default behavior
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                // Simply dispose of this window
                dispose();
            }
        });
    }

    public MenuBar getMenu()
    {
        return menuBar;
    }

    public Canvas getCanvas()
    {
        return canvas;
    }

    public PropertiesPanel getPropertiesPanel()
    {
        return propertiesPanel;
    }

    public ComponentPalette getComponentPalette()
    {
        return componentPalette;
    }

    public DiagramsPanel1 getDiagramsPanel()
    {
        return diagramsPanel;
    }

    public Project getProject()
    {
        return project;
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
        menuBar = new MenuBar();
        setJMenuBar(menuBar);
    }

    /**
     * Initializes all panels and UI components.
     */
    private void initializePanels() {
        // Initialize core components
        propertiesPanel = new PropertiesPanel();
        componentPalette = new ComponentPalette();
        diagramsPanel = new DiagramsPanel1(project);
        canvas = new Canvas();

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
    public void close   () {
        dispose();
    }

}
