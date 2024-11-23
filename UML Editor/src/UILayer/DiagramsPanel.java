package UILayer;

import Utilities.Diagram;
import Utilities.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DiagramsPanel extends JPanel {
    private ArrayList<Diagram> diagrams;
    private JPanel contentPanel;
    private ActionListener diagramDisplayAction;
    private ActionListener propertiesDisplayAction;

    public DiagramsPanel() {
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
        contentPanel = new JPanel(new GridBagLayout());
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

    public void displayDiagrams(ArrayList<Diagram> diagrams) {
        // Clear existing content
        this.diagrams = diagrams;
        contentPanel.removeAll();

        // Configure GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Create and add diagram cards with their components
        for (Diagram diagram : diagrams) {
            // Create diagram section
            JPanel hierarchyPanel = createHierarchyPanel(diagram);
            contentPanel.add(hierarchyPanel, gbc);
            gbc.gridy++;  // Move to next row
        }

        // Add vertical glue to push cards to the top
        gbc.weighty = 1.0;
        contentPanel.add(Box.createVerticalGlue(), gbc);

        // Refresh the panel
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private JPanel createHierarchyPanel(Diagram diagram) {
        // Create main panel with vertical BoxLayout
        JPanel hierarchyPanel = new JPanel();
        hierarchyPanel.setLayout(new BoxLayout(hierarchyPanel, BoxLayout.Y_AXIS));
        hierarchyPanel.setBackground(Color.WHITE);
        hierarchyPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

        // Create diagram header
        JPanel diagramHeader = createDiagramHeader(diagram);
        hierarchyPanel.add(diagramHeader);

        // Add components section
        JPanel componentsPanel = new JPanel();
        componentsPanel.setLayout(new BoxLayout(componentsPanel, BoxLayout.Y_AXIS));
        componentsPanel.setBackground(Color.WHITE);
        componentsPanel.setBorder(BorderFactory.createEmptyBorder(5, 15, 0, 0)); // Left padding for hierarchy

        // Add components
        for (Component component : diagram.getComponents()) {
            JPanel componentCard = createComponentCard(component);
            componentsPanel.add(componentCard);
            componentsPanel.add(Box.createVerticalStrut(5)); // Add spacing between components
        }

        hierarchyPanel.add(componentsPanel);

        // Add hover effect for the entire panel
        hierarchyPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                hierarchyPanel.setBackground(new Color(245, 245, 245));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                hierarchyPanel.setBackground(Color.WHITE);
            }
        });

        return hierarchyPanel;
    }

    private JPanel createDiagramHeader(Diagram diagram) {
        JPanel headerPanel = new JPanel(new BorderLayout(5, 5));
        headerPanel.setBackground(Color.WHITE);

        // Create title with icon indicating expandable section
        JLabel titleLabel = new JLabel("▼ " + diagram.getName());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 12));

        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Add click listener for diagram
        headerPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                if (diagramDisplayAction != null) {
                    diagramDisplayAction.actionPerformed(new ActionEvent(diagram, ActionEvent.ACTION_PERFORMED, diagram.getName()));
                }
            }
        });

        return headerPanel;
    }

    private JPanel createComponentCard(Component component) {
        JPanel card = new JPanel(new BorderLayout(5, 5));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(240, 240, 240)));

        // Create label with component name or class if name is null
        String componentText = (component.getName() != null) ? component.getName() : component.getClass().getSimpleName();
        JLabel componentLabel = new JLabel("○ " + componentText);
        componentLabel.setFont(new Font("Arial", Font.PLAIN, 11));

        card.add(componentLabel, BorderLayout.CENTER);

        // Add hover effect and click listener
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
                if (propertiesDisplayAction != null) {
                    propertiesDisplayAction.actionPerformed(new ActionEvent(component, ActionEvent.ACTION_PERFORMED, "showProperties"));
                }
            }
        });

        return card;
    }

    public void addActionListeners(ActionListener diagramListener, ActionListener propertiesListener) {
        this.diagramDisplayAction = diagramListener;
        this.propertiesDisplayAction = propertiesListener;
    }
}