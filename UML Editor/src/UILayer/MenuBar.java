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
        // Set a slightly modern look
        UIManager.put("Menu.background", Color.WHITE);
        UIManager.put("MenuItem.background", Color.WHITE);

        // Initialize menus with mnemonics for keyboard accessibility
        fileMenu = createMenu("File", KeyEvent.VK_F);
        editMenu = createMenu("Edit", KeyEvent.VK_E);
        viewMenu = createMenu("View", KeyEvent.VK_V);
        diagramMenu = createMenu("Diagrams", KeyEvent.VK_D);
        helpMenu = createMenu("Help", KeyEvent.VK_H);

        // File Menu with icons and keyboard shortcuts
        addMenuItem(fileMenu, "New", KeyEvent.VK_N, KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK),
                e -> System.out.println("Creating new file"));
        addMenuItem(fileMenu, "Open", KeyEvent.VK_O, KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK),
                e -> System.out.println("Opening file"));
        addMenuItem(fileMenu, "Save", KeyEvent.VK_S, KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK),
                e -> System.out.println("Saving file"));
        fileMenu.addSeparator();
        addMenuItem(fileMenu, "Exit", KeyEvent.VK_X, KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK),
                e -> System.exit(0));

        // Edit Menu with undo/redo
        addMenuItem(editMenu, "Undo", KeyEvent.VK_U, KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK),
                e -> System.out.println("Undoing last action"));
        addMenuItem(editMenu, "Redo", KeyEvent.VK_R, KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK),
                e -> System.out.println("Redoing last action"));
        editMenu.addSeparator();
        addMenuItem(editMenu, "Cut", KeyEvent.VK_T, KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK),
                e -> System.out.println("Cutting selection"));
        addMenuItem(editMenu, "Copy", KeyEvent.VK_C, KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK),
                e -> System.out.println("Copying selection"));
        addMenuItem(editMenu, "Paste", KeyEvent.VK_P, KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK),
                e -> System.out.println("Pasting"));

        // View Menu with zoom functionality
        JMenuItem zoomInItem = addMenuItem(viewMenu, "Zoom In", KeyEvent.VK_I,
                KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, InputEvent.CTRL_DOWN_MASK),
                e -> System.out.println("Zooming In"));
        JMenuItem zoomOutItem = addMenuItem(viewMenu, "Zoom Out", KeyEvent.VK_O,
                KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, InputEvent.CTRL_DOWN_MASK),
                e -> System.out.println("Zooming Out"));
        viewMenu.addSeparator();
        addMenuItem(viewMenu, "Reset View", KeyEvent.VK_R,
                KeyStroke.getKeyStroke(KeyEvent.VK_0, InputEvent.CTRL_DOWN_MASK),
                e -> System.out.println("Resetting view"));

        // Diagram Menu
        addMenuItem(diagramMenu, "Create Class Diagram", KeyEvent.VK_C,
                KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK),
                e -> createClassDiagram());
        addMenuItem(diagramMenu, "Create Sequence Diagram", KeyEvent.VK_S, null,
                e -> System.out.println("Creating Sequence Diagram"));
        addMenuItem(diagramMenu, "Create Use Case Diagram", KeyEvent.VK_U, null,
                e -> System.out.println("Creating Use Case Diagram"));

        // Help Menu
        addMenuItem(helpMenu, "About", KeyEvent.VK_A, null,
                e -> showAboutDialog());
        addMenuItem(helpMenu, "Help Contents", KeyEvent.VK_H, KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0),
                e -> System.out.println("Opening Help"));

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

    // Utility method to add menu item with mnemonic and optional accelerator
    private JMenuItem addMenuItem(JMenu menu, String text, int mnemonic,
                                  KeyStroke accelerator, ActionListener action) {
        JMenuItem menuItem = new JMenuItem(text, mnemonic);
        if (accelerator != null) {
            menuItem.setAccelerator(accelerator);
        }
        menuItem.addActionListener(action);
        menu.add(menuItem);
        return menuItem;
    }

    // Method to create class diagram
    private void createClassDiagram() {
        System.out.println("Creating Class Diagram...");
        JOptionPane.showMessageDialog(this,
                "Create a new Class Diagram\nSelect components from the palette",
                "Create Class Diagram",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // About dialog method
    private void showAboutDialog() {
        JOptionPane.showMessageDialog(this,
                "UML Diagram Designer\nVersion 1.0\n\n" +
                        "© 2024 UML Design Tools\n" +
                        "Developed by Your Company",
                "About UML Diagram Designer",
                JOptionPane.INFORMATION_MESSAGE);
    }
}