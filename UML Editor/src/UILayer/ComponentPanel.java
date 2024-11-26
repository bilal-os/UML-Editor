package UILayer;

import Utilities.Component;
import Utilities.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ComponentPanel extends JPanel implements Observer {
    // Component and UI state
    private Component component;
    private JLabel nameLabel;
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
                : (component.getClass().getSimpleName() != null
                ? component.getClass().getSimpleName()
                : "Unnamed Component");

        // Create and style label
        nameLabel = new JLabel(displayName);
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        nameLabel.setForeground(DEFAULT_TEXT_COLOR);

        // Add to panel
        add(nameLabel, BorderLayout.CENTER);
    }

    /**
     * Add mouse listeners for panel interactions
     */
    private void addInteractionListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleComponentClick(e);
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
    public void update() {
        // Ensure UI updates happen on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            String newName = component.getName();
            if (newName != null && !newName.equals(nameLabel.getText())) {
                nameLabel.setText(newName);
                revalidate();
                repaint();
            }
        });
    }

    /**
     * Get the underlying component
     * @return The component associated with this panel
     */
    public Component getComponent() {
        return component;
    }
}