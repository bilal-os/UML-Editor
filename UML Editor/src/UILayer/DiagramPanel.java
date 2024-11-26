package UILayer;

import Utilities.Diagram;
import Utilities.Component;
import Utilities.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.List;

public class DiagramPanel extends JPanel implements Observer {
    private Diagram diagram;
    private final HashMap<String, Boolean> expandedStates;
    private ActionListener componentPaletteListener;
    private ActionListener propertiesPanelListener;

    // UI Components
    private JPanel headerPanel;
    private JLabel arrowLabel;
    private JLabel nameLabel;
    private JLabel countLabel;
    private JPanel componentsPanel;

    private static final Color HOVER = new Color(249, 250, 251);
    private static final Color BACKGROUND = Color.WHITE;

    public DiagramPanel(Diagram diagram,
                        ActionListener componentPaletteListener,
                        ActionListener propertiesPanelListener) {
        this.diagram = diagram;
        this.componentPaletteListener = componentPaletteListener;
        this.propertiesPanelListener = propertiesPanelListener;

        // Ensure the diagram is observable
        diagram.addObserver(this);

        // Initialize expanded states
        this.expandedStates = new HashMap<>();

        // Default to false if not previously set
        expandedStates.putIfAbsent(diagram.getName(), Boolean.FALSE);

        // Initialize UI
        initializeUI();
    }

    private void initializeUI() {
        // Reset the panel
        removeAll();

        // Setup base panel
        setLayout(new BorderLayout());
        setBackground(BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));

        // Create header panel
        createHeaderPanel();

        // Add components panel if expanded
        if (isExpanded()) {
            createComponentsPanel();
        }

        // Ensure proper layout
        revalidate();
        repaint();
    }

    private void createHeaderPanel() {
        // Header Panel
        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BACKGROUND);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        // Arrow Label
        arrowLabel = new JLabel(isExpanded() ? "⌄" : "›");
        arrowLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        arrowLabel.setForeground(Color.GRAY);

        // Title Panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        titlePanel.setBackground(BACKGROUND);

        // Name Label
        nameLabel = new JLabel(diagram.getName());
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        nameLabel.setForeground(Color.BLACK);

        // Count Label
        countLabel = new JLabel(String.valueOf(diagram.getComponents().size()));
        countLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        countLabel.setForeground(Color.GRAY);

        // Assemble title panel
        titlePanel.add(arrowLabel);
        titlePanel.add(nameLabel);
        titlePanel.add(countLabel);

        headerPanel.add(titlePanel, BorderLayout.CENTER);

        // Add mouse listeners
        addHeaderMouseListeners();

        // Add header to panel
        add(headerPanel, BorderLayout.NORTH);
    }

    private void addHeaderMouseListeners() {
        headerPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Toggle expanded state
                toggleExpansion();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                headerPanel.setBackground(HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                headerPanel.setBackground(BACKGROUND);
            }
        });
    }

    private void toggleExpansion() {
        // Toggle expanded state
        boolean currentState = isExpanded();
        boolean newState = !currentState;

        // Update expanded state
        expandedStates.put(diagram.getName(), newState);

        // Update arrow label
        arrowLabel.setText(newState ? "⌄" : "›");

        // Recreate UI based on new state
        if (newState) {
            createComponentsPanel();
        } else {
            // Remove components panel if it exists
            if (getComponentCount() > 1) {
                remove(1);
            }
        }

        // Trigger listener if set
        if (componentPaletteListener != null) {
            componentPaletteListener.actionPerformed(
                    new ActionEvent(diagram, ActionEvent.ACTION_PERFORMED, diagram.getName())
            );
        }

        // Refresh UI
        revalidate();
        repaint();
    }

    private void createComponentsPanel() {
        // Create components panel
        componentsPanel = new JPanel();
        componentsPanel.setLayout(new BoxLayout(componentsPanel, BoxLayout.Y_AXIS));
        componentsPanel.setBackground(BACKGROUND);
        componentsPanel.setBorder(BorderFactory.createEmptyBorder(4, 28, 4, 4));

        // Add components
        List<Component> components = diagram.getComponents();
        for (Component component : components) {
            componentsPanel.add(new ComponentPanel(component, propertiesPanelListener));
            componentsPanel.add(Box.createVerticalStrut(4));
        }

        // Add to panel
        add(componentsPanel, BorderLayout.CENTER);
    }

    private boolean isExpanded() {
        return expandedStates.getOrDefault(diagram.getName(), false);
    }

    @Override
    public void update() {
        // Safely update the UI
        SwingUtilities.invokeLater(() -> {
            // Update component count
            countLabel.setText(String.valueOf(diagram.getComponents().size()));

            // Only add component if panel is expanded
            if (isExpanded() && !diagram.getComponents().isEmpty()) {
                addComponent(diagram.getComponents().get(diagram.getComponents().size() - 1));
            }
        });
    }

    private void addComponent(Component component) {
        // Safely add a new component to the panel
        if (componentsPanel != null) {
            componentsPanel.add(new ComponentPanel(component, propertiesPanelListener));
            componentsPanel.add(Box.createVerticalStrut(4));

            // Refresh UI
            revalidate();
            repaint();
        }
    }
}