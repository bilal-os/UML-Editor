package UILayer.DiagramsPanel;

import Utilities.Component.Component;
import Utilities.Property.PropertyObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ComponentPanel extends JPanel implements PropertyObserver {
    // Component and UI state
    private Component component;
    private JLabel nameLabel;
    private JButton removeButton;
    private ActionListener propertiesPanelListener;

    // Consistent color scheme
    private static final Color DEFAULT_BACKGROUND = Color.WHITE;
    private static final Color HOVER_BACKGROUND = Color.LIGHT_GRAY;
    private static final Color DEFAULT_TEXT_COLOR = Color.BLACK;

    /**
     * Constructor for ComponentPanel
     * @param component The component to display
     * @param propertiesPanelListener Listener for properties panel actions
     */
    public ComponentPanel(Component component, ActionListener propertiesPanelListener) {
        // Validate inputs
        if (component == null) {
            throw new IllegalArgumentException("Component cannot be null");
        }

        // Initialize core data
        this.component = component;
        this.propertiesPanelListener = propertiesPanelListener;

        // Register as an observer to the component's name property
        if (component.getNameProperty() != null) {
            component.getNameProperty().addObserver(this);
        }

        // Setup UI
        initializeUI();
    }

    /**
     * Initialize the user interface for the component panel
     */
    private void initializeUI() {
        // Set layout and styling
        setLayout(new BorderLayout());
        setBackground(DEFAULT_BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));

        // Create name label
        createNameLabel();

        // Create remove button
        createRemoveButton();

        // Add mouse listeners for interaction
        addInteractionListeners();
    }

    /**
     * Create and configure the name label
     */
    private void createNameLabel() {
        // Determine the label text
        String displayName = component.getName() != null
                ? component.getName()
                : component.getClass().getSimpleName();

        // Create and style label
        nameLabel = new JLabel(displayName);
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        nameLabel.setForeground(DEFAULT_TEXT_COLOR);

        // Add to panel
        add(nameLabel, BorderLayout.CENTER);
    }

    /**
     * Create and configure the remove button
     */
    private void createRemoveButton() {
        removeButton = new JButton("Remove");
        removeButton.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Enhanced styling
        removeButton.setForeground(Color.RED);
        removeButton.setBorderPainted(false);
        removeButton.setContentAreaFilled(false);
        removeButton.setFocusPainted(false);
        removeButton.setPreferredSize(new Dimension(25, 25));

        // Add hover and press effects
        removeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                removeButton.setForeground(Color.DARK_GRAY);
                removeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                removeButton.setForeground(Color.RED);
                removeButton.setCursor(Cursor.getDefaultCursor());
            }

            @Override
            public void mousePressed(MouseEvent e) {
                removeButton.setForeground(Color.GRAY);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                removeButton.setForeground(Color.RED);
            }
        });

        // Confirmation dialog before removal
        removeButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to remove this component?",
                    "Confirm Removal",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                removeComponent();
            }
        });

        // Add tooltip for clarity
        removeButton.setToolTipText("Remove Component");

        // Add remove button to the east (right) side of the panel
        add(removeButton, BorderLayout.EAST);
    }

    /**
     * Remove the component from its associated diagram
     */
    private void removeComponent() {
        // Remove the component from its associated diagram
        if (component.getDiagram() != null) {
            // Remove the name property observer before removing the component
            if (component.getNameProperty() != null) {
                component.getNameProperty().removeObserver(this);
            }

            // Remove the component from the diagram
            component.getDiagram().removeComponent(component);

            // Optional: log removal (can be removed in production)
            System.out.println("Removed component: " +
                    (component.getName() != null ? component.getName() : component.getClass().getSimpleName()));
        }
    }

    /**
     * Add mouse listeners for panel interactions
     */
    private void addInteractionListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Only handle click if not on the remove button
                if (e.getSource() != removeButton) {
                    handleComponentClick(e);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(HOVER_BACKGROUND);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(DEFAULT_BACKGROUND);
            }
        });
    }

    /**
     * Handle component click event
     * @param e MouseEvent triggering the click
     */
    private void handleComponentClick(MouseEvent e) {
        // Log component click (can be removed in production)
        System.out.println("Component clicked: " +
                (component.getName() != null ? component.getName() : component.getClass().getSimpleName()));

        // Trigger properties panel if listener is set
        if (propertiesPanelListener != null) {
            propertiesPanelListener.actionPerformed(
                    new ActionEvent(component, ActionEvent.ACTION_PERFORMED, "showProperties")
            );
        }
    }

    /**
     * Update the component name when notified
     */
    @Override
    public void updateFromProperty() {
        nameLabel.setText(component.getName());
    }

    /**
     * Get the underlying component
     * @return The component associated with this panel
     */
    public Component getComponent() {
        return component;
    }
}