package UILayer;

import BusinessLogic.BusinessLogicInterface;
import Utilities.Diagram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MenuBar extends JMenuBar {

    BusinessLogicInterface businessLogic;
    ArrayList<Diagram> diagrams;
    DiagramsPanel diagramsPanel;

    public MenuBar(ArrayList<Diagram> diagrams, BusinessLogicInterface businessLogic, DiagramsPanel diagramsPanel) {
        this.diagramsPanel = diagramsPanel;
        this.businessLogic = businessLogic;
        this.diagrams = diagrams;

        // Set a modern look for the menus
        UIManager.put("Menu.background", Color.WHITE);
        UIManager.put("MenuItem.background", Color.WHITE);

        // Initialize menus
        JMenu fileMenu = createMenu("File", KeyEvent.VK_F);
        JMenu editMenu = createMenu("Edit", KeyEvent.VK_E);
        JMenu viewMenu = createMenu("View", KeyEvent.VK_V);
        JMenu diagramMenu = createMenu("Diagrams", KeyEvent.VK_D);
        JMenu helpMenu = createMenu("Help", KeyEvent.VK_H);

        // File menu items
        addMenuItem(fileMenu, "New Project", KeyEvent.VK_N, KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK), e -> createNewFile());
        addMenuItem(fileMenu, "Open Project", KeyEvent.VK_O, KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK), e -> openFile());
        addMenuItem(fileMenu, "Save Project", KeyEvent.VK_S, KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK), e -> saveFile());
        fileMenu.addSeparator();
        addMenuItem(fileMenu, "Exit", KeyEvent.VK_X, KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK), e -> exitApplication());

        // Edit menu items
        addMenuItem(editMenu, "Undo", KeyEvent.VK_U, KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK), e -> undoAction());
        addMenuItem(editMenu, "Redo", KeyEvent.VK_R, KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK), e -> redoAction());
        editMenu.addSeparator();
        addMenuItem(editMenu, "Cut", KeyEvent.VK_T, KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK), e -> cutAction());
        addMenuItem(editMenu, "Copy", KeyEvent.VK_C, KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK), e -> copyAction());
        addMenuItem(editMenu, "Paste", KeyEvent.VK_P, KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK), e -> pasteAction());

        // View menu items
        addMenuItem(viewMenu, "Zoom In", KeyEvent.VK_I, KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, InputEvent.CTRL_DOWN_MASK), e -> zoomIn());
        addMenuItem(viewMenu, "Zoom Out", KeyEvent.VK_O, KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, InputEvent.CTRL_DOWN_MASK), e -> zoomOut());
        viewMenu.addSeparator();
        addMenuItem(viewMenu, "Reset View", KeyEvent.VK_R, KeyStroke.getKeyStroke(KeyEvent.VK_0, InputEvent.CTRL_DOWN_MASK), e -> resetView());

        // Diagram menu items
        addMenuItem(diagramMenu, "Create Class Diagram", KeyEvent.VK_C, KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK), e -> createClassDiagram());
        addMenuItem(diagramMenu, "Create Sequence Diagram", KeyEvent.VK_S, null, e -> createSequenceDiagram());
        addMenuItem(diagramMenu, "Create Use Case Diagram", KeyEvent.VK_U, null, e -> createUseCaseDiagram());

        // Help menu items
        addMenuItem(helpMenu, "About", KeyEvent.VK_A, null, e -> showAboutDialog());
        addMenuItem(helpMenu, "Help Contents", KeyEvent.VK_H, KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), e -> openHelp());

        // Add menus to the menu bar
        add(fileMenu);
        add(editMenu);
        add(viewMenu);
        add(diagramMenu);
        add(helpMenu);
    }

    // Utility method to create menu with mnemonic
    private JMenu createMenu(String text, int mnemonic) {
        JMenu menu = new JMenu(text);
        menu.setMnemonic(mnemonic);
        return menu;
    }

    // Utility method to add menu item with action
    private void addMenuItem(JMenu menu, String text, int mnemonic, KeyStroke accelerator, ActionListener action) {
        JMenuItem menuItem = new JMenuItem(text, mnemonic);
        if (accelerator != null) menuItem.setAccelerator(accelerator);
        menuItem.addActionListener(action);
        menu.add(menuItem);
    }

    // File menu actions
    private void createNewFile() {
        System.out.println("Creating new file");
    }

    private void openFile() {
        System.out.println("Opening file");
    }

    private void saveFile() {
        System.out.println("Saving file");
    }

    private void exitApplication() {
        System.exit(0);
    }

    // Edit menu actions
    private void undoAction() {
        System.out.println("Undoing last action");
    }

    private void redoAction() {
        System.out.println("Redoing last action");
    }

    private void cutAction() {
        System.out.println("Cutting selection");
    }

    private void copyAction() {
        System.out.println("Copying selection");
    }

    private void pasteAction() {
        System.out.println("Pasting");
    }

    // View menu actions
    private void zoomIn() {
        System.out.println("Zooming In");
    }

    private void zoomOut() {
        System.out.println("Zooming Out");
    }

    private void resetView() {
        System.out.println("Resetting view");
    }

    // Diagram menu actions
    private void createClassDiagram() {
        System.out.println("Creating Class Diagram...");
        diagrams.add(businessLogic.createDiagram("Class Diagram"));
        diagramsPanel.displayDiagrams();
        JOptionPane.showMessageDialog(this,
                "Create a new Class Diagram\nSelect components from the palette",
                "Create Class Diagram",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void createSequenceDiagram() {
        System.out.println("Creating Sequence Diagram");
    }

    private void createUseCaseDiagram() {
        System.out.println("Creating Use Case Diagram");
    }

    // Help menu actions
    private void showAboutDialog() {
        JOptionPane.showMessageDialog(this,
                "UML Diagram Designer\nVersion 1.0\n\n" +
                        "© 2024 UML Design Tools\n" +
                        "Developed by Your Company",
                "About UML Diagram Designer",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void openHelp() {
        System.out.println("Opening Help");
    }
}
