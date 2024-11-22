package UILayer;

import Utilities.Diagram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ComponentPalette extends JPanel {
    private JPanel contentPanel;  // Reference to content panel for updates

    public ComponentPalette() {
        // Use BorderLayout to have a title and scrollable content
        setLayout(new BorderLayout());

        // Title Panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(new Color(240, 240, 240));
        JLabel titleLabel = new JLabel("Component Palette");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titlePanel.add(titleLabel);
        titlePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        // Content Panel with GridBag Layout for more flexible arrangement
        contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        // Wrap content in a scroll pane
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Add components to main panel
        add(titlePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Overall panel styling
        setPreferredSize(new Dimension(250, 600));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
    }

    public void displayComponents(Diagram diagram) {
        // Clear existing components
        contentPanel.removeAll();

        // Get component names from diagram
        ArrayList<String> componentNames = (ArrayList<String>) diagram.getComponentNames();

        if (componentNames == null || componentNames.isEmpty()) {
            // Show empty state
            JLabel emptyLabel = new JLabel("No components available");
            emptyLabel.setForeground(Color.GRAY);
            contentPanel.add(emptyLabel);
        } else {
            // Setup constraints for grid
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1.0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(5, 5, 5, 5);

            // Add component items
            for (String componentName : componentNames) {
                JPanel itemPanel = createComponentItem(componentName);
                contentPanel.add(itemPanel, gbc);
                gbc.gridy++;
            }

            // Add vertical glue to push items to top
            gbc.weighty = 1.0;
            contentPanel.add(Box.createVerticalGlue(), gbc);
        }

        // Refresh the panel
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private JPanel createComponentItem(String componentName) {
        JPanel itemPanel = new JPanel(new BorderLayout());
        itemPanel.setBackground(Color.WHITE);
        itemPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JLabel nameLabel = new JLabel(componentName, SwingConstants.CENTER);
        nameLabel.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        itemPanel.add(nameLabel, BorderLayout.CENTER);

        // Add hover effect
        itemPanel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                itemPanel.setBackground(new Color(245, 245, 245));
            }

            public void mouseExited(MouseEvent e) {
                itemPanel.setBackground(Color.WHITE);
            }
        });

        return itemPanel;
    }
}