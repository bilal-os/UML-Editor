package UILayer;

import Utilities.Diagram;
import Utilities.Component;
import Utilities.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class DiagramPanel extends JPanel implements Observer {
    private Diagram diagram;
    private final HashMap<String, Boolean> expandedStates;
    private ActionListener componentPaletteListener;
    private ActionListener propertiesPanelListener;
    public DiagramPanel(Diagram diagram,ActionListener componentPaletteListener, ActionListener propertiesPanelListener) {
        this.diagram = diagram;
        this.componentPaletteListener = componentPaletteListener;
        this.propertiesPanelListener = propertiesPanelListener;
        diagram.addObserver(this);
        this.expandedStates = new HashMap<>();
        initializeUI();
    }
    private static final Color HOVER = new Color(249, 250, 251);
    private static final Color BACKGROUND = Color.WHITE;

    private void initializeUI() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JLabel arrowLabel = new JLabel(expandedStates.getOrDefault(diagram.getName(), false) ? "⌄" : "›");
        arrowLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        arrowLabel.setForeground(Color.GRAY);

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        titlePanel.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel(diagram.getName());
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        nameLabel.setForeground(Color.BLACK);

        JLabel countLabel = new JLabel(String.valueOf(diagram.getComponents().size()));
        countLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        countLabel.setForeground(Color.GRAY);

        titlePanel.add(arrowLabel);
        titlePanel.add(nameLabel);
        titlePanel.add(countLabel);

        headerPanel.add(titlePanel, BorderLayout.CENTER);

        // Click handling
        headerPanel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                // Toggle expanded state and update UI
                boolean expanded = !expandedStates.getOrDefault(diagram.getName(), false);
                expandedStates.put(diagram.getName(), expanded);
                arrowLabel.setText(expanded ? "⌄" : "›");
                revalidate();  // Ensure layout updates
                repaint();
                if (componentPaletteListener != null) {
                    componentPaletteListener.actionPerformed(
                            new ActionEvent(diagram, ActionEvent.ACTION_PERFORMED, diagram.getName())
                    );
                }
            }
            public void mouseEntered(MouseEvent e) {
                headerPanel.setBackground(HOVER);
                titlePanel.setBackground(HOVER);
            }

            public void mouseExited(MouseEvent e) {
                headerPanel.setBackground(BACKGROUND);
                titlePanel.setBackground(BACKGROUND);
            }
        });

        add(headerPanel, BorderLayout.NORTH);

        // Components section (initially hidden or shown based on expanded state)
        if (expandedStates.getOrDefault(diagram.getName(), false)) {
            JPanel componentsPanel = new JPanel();
            componentsPanel.setLayout(new BoxLayout(componentsPanel, BoxLayout.Y_AXIS));
            componentsPanel.setBackground(Color.WHITE);
            componentsPanel.setBorder(BorderFactory.createEmptyBorder(4, 28, 4, 4));

            for (Component component : diagram.getComponents()) {
                componentsPanel.add(new ComponentPanel(component,propertiesPanelListener));
                componentsPanel.add(Box.createVerticalStrut(4));
            }

            add(componentsPanel, BorderLayout.CENTER);
        }
    }

    @Override
    public void update() {
        // Dynamically add the latest component from the diagram
        addComponent(diagram.getComponents().getLast());
    }

    private void addComponent(Component component) {
        // Dynamically add a component to the panel
        JPanel componentsPanel = (JPanel) getComponent(1);  // Access the components panel (if visible)
        if (componentsPanel != null) {
            componentsPanel.add(new ComponentPanel(component,propertiesPanelListener));
            componentsPanel.add(Box.createVerticalStrut(4));
            revalidate();
            repaint();
        }
    }
}
