package UILayer.MenuBar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MenuBarTest {

    private MenuBar menuBar;

    @BeforeEach
    void setUp() {
        menuBar = new MenuBar();
    }

    @Test
    void testMenuBarInitialization() {
        // Check if the number of menus added to the menu bar is correct
        assertEquals(5, menuBar.getMenuCount(), "Menu bar should contain 5 menus (File, Edit, View, Diagrams, Help)");

        // Check if the menus exist and have the correct names
        assertEquals("File", menuBar.getMenu(0).getText(), "First menu should be 'File'");
        assertEquals("Edit", menuBar.getMenu(1).getText(), "Second menu should be 'Edit'");
        assertEquals("View", menuBar.getMenu(2).getText(), "Third menu should be 'View'");
        assertEquals("Diagrams", menuBar.getMenu(3).getText(), "Fourth menu should be 'Diagrams'");
        assertEquals("Help", menuBar.getMenu(4).getText(), "Fifth menu should be 'Help'");
    }

    @Test
    void testFileMenuItems() {
        JMenu fileMenu = menuBar.getMenu(0);
        assertNotNull(fileMenu, "File menu should not be null");

        assertEquals(4, fileMenu.getItemCount() - 1, "File menu should have 4 items (New, Open, Save, Exit) and 1 separator");
        assertEquals("New Project", fileMenu.getItem(0).getText(), "First item should be 'New Project'");
        assertEquals("Open Project", fileMenu.getItem(1).getText(), "Second item should be 'Open Project'");
        assertEquals("Save Project", fileMenu.getItem(2).getText(), "Third item should be 'Save Project'");
        assertEquals("Exit", fileMenu.getItem(4).getText(), "Fourth item should be 'Exit'");
    }

    @Test
    void testEditMenuItems() {
        JMenu editMenu = menuBar.getMenu(1);
        assertNotNull(editMenu, "Edit menu should not be null");

        assertEquals(3, editMenu.getItemCount() - 1, "Edit menu should have 3 items (Save PNG, Save JPEG, Generate Class Diagram Code) and 1 separator");
        assertEquals("Save PNG", editMenu.getItem(0).getText(), "First item should be 'Save PNG'");
        assertEquals("Save JPEG", editMenu.getItem(1).getText(), "Second item should be 'Save JPEG'");
        assertEquals("Generate Class Diagram Code", editMenu.getItem(3).getText(), "Third item should be 'Generate Class Diagram Code'");
    }

    @Test
    void testViewMenuItems() {
        JMenu viewMenu = menuBar.getMenu(2);
        assertNotNull(viewMenu, "View menu should not be null");

        assertEquals(3, viewMenu.getItemCount() - 1, "View menu should have 3 items (Zoom In, Zoom Out, Reset View) and 1 separator");
        assertEquals("Zoom In", viewMenu.getItem(0).getText(), "First item should be 'Zoom In'");
        assertEquals("Zoom Out", viewMenu.getItem(1).getText(), "Second item should be 'Zoom Out'");
        assertEquals("Reset View", viewMenu.getItem(3).getText(), "Third item should be 'Reset View'");
    }

    @Test
    void testDiagramMenuItems() {
        JMenu diagramMenu = menuBar.getMenu(3);
        assertNotNull(diagramMenu, "Diagram menu should not be null");

        assertEquals(3, diagramMenu.getItemCount(), "Diagram menu should have 3 items (Create Class Diagram, Create Sequence Diagram, Create Use Case Diagram)");
        assertEquals("Create Class Diagram", diagramMenu.getItem(0).getText(), "First item should be 'Create Class Diagram'");
        assertEquals("Create Sequence Diagram", diagramMenu.getItem(1).getText(), "Second item should be 'Create Sequence Diagram'");
        assertEquals("Create Use Case Diagram", diagramMenu.getItem(2).getText(), "Third item should be 'Create Use Case Diagram'");
    }

    @Test
    void testHelpMenuItems() {
        JMenu helpMenu = menuBar.getMenu(4);
        assertNotNull(helpMenu, "Help menu should not be null");

        assertEquals(2, helpMenu.getItemCount(), "Help menu should have 2 items (About, Help Contents)");
        assertEquals("About", helpMenu.getItem(0).getText(), "First item should be 'About'");
        assertEquals("Help Contents", helpMenu.getItem(1).getText(), "Second item should be 'Help Contents'");
    }

}
