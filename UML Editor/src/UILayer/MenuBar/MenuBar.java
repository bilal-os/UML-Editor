package UILayer.MenuBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.util.HashMap;
import java.util.Map;

public class MenuBar extends JMenuBar {
    private final Map<String, JMenuItem> menuItemsMap;

    public MenuBar() {
        menuItemsMap = new HashMap<>();

        // Apply modern look and feel for menus
        applyModernMenuStyle();

        // Initialize and add menus to the menu bar
        add(createFileMenu());
        add(createEditMenu());
        add(createViewMenu());
        add(createDiagramMenu());
        add(createHelpMenu());
    }

    private void applyModernMenuStyle() {
        UIManager.put("Menu.background", Color.WHITE);
        UIManager.put("MenuItem.background", Color.WHITE);
    }

    private JMenu createFileMenu() {
        JMenu fileMenu = createMenu("File", KeyEvent.VK_F);
        addMenuItem(fileMenu, "New Project", KeyEvent.VK_N, KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        addMenuItem(fileMenu, "Open Project", KeyEvent.VK_O, KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        addMenuItem(fileMenu, "Save Project", KeyEvent.VK_S, KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        fileMenu.addSeparator();
        addMenuItem(fileMenu, "Exit", KeyEvent.VK_X, KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK));
        return fileMenu;
    }

    private JMenu createEditMenu() {
        JMenu editMenu = createMenu("Edit", KeyEvent.VK_E);
        addMenuItem(editMenu, "Undo", KeyEvent.VK_U, KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
        addMenuItem(editMenu, "Redo", KeyEvent.VK_R, KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK));
        editMenu.addSeparator();
        addMenuItem(editMenu, "Cut", KeyEvent.VK_T, KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
        addMenuItem(editMenu, "Copy", KeyEvent.VK_C, KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        addMenuItem(editMenu, "Paste", KeyEvent.VK_P, KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
        return editMenu;
    }

    private JMenu createViewMenu() {
        JMenu viewMenu = createMenu("View", KeyEvent.VK_V);
        addMenuItem(viewMenu, "Zoom In", KeyEvent.VK_I, KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, InputEvent.CTRL_DOWN_MASK));
        addMenuItem(viewMenu, "Zoom Out", KeyEvent.VK_O, KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, InputEvent.CTRL_DOWN_MASK));
        viewMenu.addSeparator();
        addMenuItem(viewMenu, "Reset View", KeyEvent.VK_R, KeyStroke.getKeyStroke(KeyEvent.VK_0, InputEvent.CTRL_DOWN_MASK));
        return viewMenu;
    }

    private JMenu createDiagramMenu() {
        JMenu diagramMenu = createMenu("Diagrams", KeyEvent.VK_D);
        addMenuItem(diagramMenu, "Create Class Diagram", KeyEvent.VK_C, null);
        addMenuItem(diagramMenu, "Create Sequence Diagram", KeyEvent.VK_S, null);
        addMenuItem(diagramMenu, "Create Use Case Diagram", KeyEvent.VK_U, null);
        return diagramMenu;
    }

    private JMenu createHelpMenu() {
        JMenu helpMenu = createMenu("Help", KeyEvent.VK_H);
        addMenuItem(helpMenu, "About", KeyEvent.VK_A, null);
        addMenuItem(helpMenu, "Help Contents", KeyEvent.VK_H, KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
        return helpMenu;
    }

    private JMenu createMenu(String title, int mnemonic) {
        JMenu menu = new JMenu(title);
        menu.setMnemonic(mnemonic);
        return menu;
    }

    private void addMenuItem(JMenu menu, String title, int mnemonic, KeyStroke accelerator) {
        JMenuItem menuItem = new JMenuItem(title, mnemonic);
        if (accelerator != null) {
            menuItem.setAccelerator(accelerator);
        }
        menu.add(menuItem);
        menuItemsMap.put(title, menuItem);
    }

    public void addActionListeners(ActionListener fileAction, ActionListener editAction,
                                   ActionListener viewAction, ActionListener diagramAction,
                                   ActionListener helpAction) {
        System.out.println("Adding action listeners");

        // File menu actions
        addActionToMenuItem("New Project", fileAction);
        addActionToMenuItem("Open Project", fileAction);
        addActionToMenuItem("Save Project", fileAction);
        addActionToMenuItem("Exit", fileAction);

        // Edit menu actions
        addActionToMenuItem("Undo", editAction);
        addActionToMenuItem("Redo", editAction);
        addActionToMenuItem("Cut", editAction);
        addActionToMenuItem("Copy", editAction);
        addActionToMenuItem("Paste", editAction);

        // View menu actions
        addActionToMenuItem("Zoom In", viewAction);
        addActionToMenuItem("Zoom Out", viewAction);
        addActionToMenuItem("Reset View", viewAction);

        // Diagram menu actions
        addActionToMenuItem("Create Class Diagram", diagramAction);
        addActionToMenuItem("Create Sequence Diagram", diagramAction);
        addActionToMenuItem("Create Use Case Diagram", diagramAction);

        // Help menu actions
        addActionToMenuItem("About", helpAction);
        addActionToMenuItem("Help Contents", helpAction);
    }

    private void addActionToMenuItem(String menuItemName, ActionListener action) {
        System.out.println("Adding action " + menuItemName);
        JMenuItem menuItem = menuItemsMap.get(menuItemName);
        if (menuItem != null) {
            menuItem.addActionListener(action);
        }
    }
}