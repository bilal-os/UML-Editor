package UILayer;

import Utilities.Diagram;
import Utilities.Component;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ComponentPalette extends JPanel {
    private JPanel contentPanel;
    private Diagram diagram;

    private ActionListener actionListener;

    public ComponentPalette() {

        setLayout(new BorderLayout());

        // Initialize and add the title panel
        add(createTitlePanel(), BorderLayout.NORTH);

        // Initialize the content panel and wrap it in a scroll pane
        contentPanel = createContentPanel();
        add(createScrollPane(contentPanel), BorderLayout.CENTER);

        // Overall panel styling
        setPreferredSize(new Dimension(250, 600));
        setBorder(createPanelBorder());
    }

    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(new Color(240, 240, 240));
        JLabel titleLabel = new JLabel("Component Palette");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titlePanel.add(titleLabel);
        titlePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        return titlePanel;
    }

    private JPanel createContentPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        return panel;
    }

    private JScrollPane createScrollPane(JPanel panel) {
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        return scrollPane;
    }

    private CompoundBorder createPanelBorder() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        );
    }

    public void displayComponents(Diagram diagram) {

        this.diagram = diagram;

        contentPanel.removeAll(); // Clear existing content

        List<String> componentNames = diagram.getComponentNames();

        if (componentNames == null || componentNames.isEmpty()) {
            displayEmptyState();
        } else {
            displayComponentItems(componentNames);
        }

        // Refresh the panel to show updates
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void displayEmptyState() {
        JLabel emptyLabel = new JLabel("No components available", SwingConstants.CENTER);
        emptyLabel.setForeground(Color.GRAY);
        contentPanel.add(emptyLabel, new GridBagConstraints());
    }

    private void displayComponentItems(List<String> componentNames) {
        GridBagConstraints gbc = createGridBagConstraints();

        for (String componentName : componentNames) {
            contentPanel.add(createComponentItem(componentName), gbc);
            gbc.gridy++; // Move to the next row
        }

        // Add vertical glue to push items to the top
        gbc.weighty = 1.0;
        contentPanel.add(Box.createVerticalGlue(), gbc);
    }

    private GridBagConstraints createGridBagConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        return gbc;
    }

    private JPanel createComponentItem(String componentName) {
        JPanel itemPanel = new JPanel(new BorderLayout());
        itemPanel.setBackground(Color.WHITE);
        itemPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JLabel nameLabel = new JLabel(componentName, SwingConstants.CENTER);
        nameLabel.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        itemPanel.add(nameLabel, BorderLayout.CENTER);

        addHoverEffect(itemPanel, componentName);
        return itemPanel;
    }

    private void addHoverEffect(JPanel itemPanel, String componentName) {
        itemPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                itemPanel.setBackground(new Color(245, 245, 245));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                itemPanel.setBackground(Color.WHITE);
            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                Component component = diagram.addComponent(componentName);;

                if(actionListener != null)
                {
                    actionListener.actionPerformed(new ActionEvent(component,ActionEvent.ACTION_PERFORMED,component.getName()));

                }
            }

        });
    }

    public void addActionListeners(ActionListener actionListener)
    {
        this.actionListener = actionListener;
    }

}