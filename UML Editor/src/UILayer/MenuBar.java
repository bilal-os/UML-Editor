package UILayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuBar extends JMenuBar {
    private JMenu fileMenu;
    private JMenu editMenu;
    private JMenu viewMenu;
    private JMenu diagramMenu;
    private JMenu helpMenu;

    public MenuBar() {
        // Initialize menus
        fileMenu = new JMenu("File");
        editMenu = new JMenu("Edit");
        viewMenu = new JMenu("View");
        diagramMenu = new JMenu("Diagrams");
        helpMenu = new JMenu("Help");

        // File Menu
        fileMenu.add(new JMenuItem("New"));
        fileMenu.add(new JMenuItem("Open"));
        fileMenu.add(new JMenuItem("Save"));

        // Edit Menu
        editMenu.add(new JMenuItem("Undo"));
        editMenu.add(new JMenuItem("Redo"));

        // View Menu
        JMenuItem zoomInItem = new JMenuItem("Zoom In");
        zoomInItem.addActionListener(e -> System.out.println("Zoom In"));
        viewMenu.add(zoomInItem);

        JMenuItem zoomOutItem = new JMenuItem("Zoom Out");
        zoomOutItem.addActionListener(e -> System.out.println("Zoom Out"));
        viewMenu.add(zoomOutItem);

        // Diagram Menu
        JMenuItem classDiagramItem = new JMenuItem("Create Class Diagram");
        classDiagramItem.addActionListener(e -> createClassDiagram());
        diagramMenu.add(classDiagramItem);

        // Help Menu
        helpMenu.add(new JMenuItem("About"));

        // Add menus to the menu bar
        add(fileMenu);
        add(editMenu);
        add(viewMenu);
        add(diagramMenu);
        add(helpMenu);
    }

    private void createClassDiagram() {
        System.out.println("Creating Class Diagram...");
    }
}
