package UILayer;

import Utilities.Diagram;
import Utilities.Observer;
import Utilities.Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DiagramsPanel1 extends JPanel implements Observer {
    private ArrayList<Diagram> diagrams;
    private JPanel contentPanel;
    private ActionListener componentPaletteListener;
    private ActionListener propertiesPanelListener;
    private final HashMap<String, Boolean> expandedStates;
    private Project currentProject;

    public DiagramsPanel1(Project currentProject) {
        this.diagrams = new ArrayList<>();
        this.currentProject = currentProject;
        diagrams = currentProject.getDiagrams();
        currentProject.addObserver(this);
        expandedStates = new HashMap<>();
        initializeUI();
        displayDiagrams();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel titlePanel = createTitlePanel();
        contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);

        add(titlePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setPreferredSize(new Dimension(280, 600));
    }

    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        JLabel titleLabel = new JLabel("Diagrams");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));

        titlePanel.add(titleLabel, BorderLayout.WEST);
        return titlePanel;
    }

    public void displayDiagrams() {
        contentPanel.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(4, 12, 0, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        for (Diagram diagram : diagrams) {
            DiagramPanel diagramPanel = new DiagramPanel(diagram,componentPaletteListener,propertiesPanelListener);

            // Check if the diagram is expanded or collapsed
            boolean isExpanded = expandedStates.getOrDefault(diagram.getName(), false);
            diagramPanel.setVisible(isExpanded);

            contentPanel.add(diagramPanel, gbc);
            gbc.gridy++;
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public void addActionListeners(ActionListener componentPaletteListener, ActionListener propertiesPanelListener) {
        this.componentPaletteListener = componentPaletteListener;
        this.propertiesPanelListener = propertiesPanelListener;
    }

    public void addDiagram(Diagram diagram) {
        // You can add a new diagram to the list and refresh
        diagrams.add(diagram);
        displayDiagrams();
    }

    @Override
    public void update() {
        addDiagram(currentProject.getDiagrams().getLast());
    }
}
