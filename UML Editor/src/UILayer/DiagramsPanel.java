package UILayer;

import Utilities.Diagram;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DiagramsPanel extends JPanel {
    private ArrayList<Diagram> diagrams;
    private JPanel contentPanel;  // Added field to access content panel
    private ComponentPalette componentPalette;

    public DiagramsPanel(ArrayList<Diagram> diagrams, ComponentPalette componentPalette) {

        this.componentPalette = componentPalette;

        this.diagrams = diagrams;

        // Set layout to BorderLayout for future use
        setLayout(new BorderLayout());

        // Title Panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(new Color(240, 240, 240));
        JLabel titleLabel = new JLabel("Diagrams Panel");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titlePanel.add(titleLabel);
        titlePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        // Content Panel with GridBag Layout for more flexible arrangement
        contentPanel = new JPanel(new GridBagLayout());  // Store reference to content panel
        contentPanel.setBackground(Color.WHITE);

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

    public void displayDiagrams() {
        // Clear existing content
        contentPanel.removeAll();

        // Configure GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Create and add diagram cards
        for (Diagram diagram : diagrams) {
            JPanel diagramCard = createDiagramCard(diagram);
            contentPanel.add(diagramCard, gbc);
            gbc.gridy++;  // Move to next row
        }

        // Add vertical glue to push cards to the top
        gbc.weighty = 1.0;
        contentPanel.add(Box.createVerticalGlue(), gbc);

        // Refresh the panel
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private JPanel createDiagramCard(Diagram diagram) {
        // Create card panel with white background and border
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(5, 5));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

        // Create title label
        JLabel titleLabel = new JLabel(diagram.getName(), SwingConstants.CENTER); // Center align text
        titleLabel.setFont(new Font("Arial", Font.BOLD, 12));

        // Add components to card
        card.add(titleLabel, BorderLayout.CENTER); // Center the label

        // Add hover effect
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                card.setBackground(new Color(245, 245, 245));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                card.setBackground(Color.WHITE);
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                // Send the clicked diagram to the componentPalette
                componentPalette.displayComponents(diagram); // Pass the diagram
            }
        });

        return card;
    }

}