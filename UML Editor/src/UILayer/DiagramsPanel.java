package UILayer;

import Utilities.Diagram;
import Utilities.Component;
import Utilities.Project;
import Utilities.ProjectObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DiagramsPanel extends JPanel implements ProjectObserver {
    private ArrayList<Diagram> diagrams;
    private Project project;
    private JPanel contentPanel;
    private ActionListener diagramDisplayAction;
    private ActionListener propertiesDisplayAction;
    private final HashMap<String, Boolean> expandedStates;

    // Simplified color scheme
    private static final Color BACKGROUND = Color.WHITE;
    private static final Color TEXT_PRIMARY = new Color(33, 37, 41);
    private static final Color TEXT_SECONDARY = new Color(108, 117, 125);
    private static final Color BORDER = new Color(229, 231, 235);
    private static final Color HOVER = new Color(249, 250, 251);

    public DiagramsPanel() {
        expandedStates = new HashMap<>();
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setBackground(BACKGROUND);

        JPanel titlePanel = createTitlePanel();
        contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(BACKGROUND);

        JScrollPane scrollPane = createScrollPane();

        add(titlePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setPreferredSize(new Dimension(280, 600));
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, BORDER));
    }

    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(BACKGROUND);
        titlePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)
        ));

        JLabel titleLabel = new JLabel("Diagrams");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        titleLabel.setForeground(TEXT_PRIMARY);

        titlePanel.add(titleLabel, BorderLayout.WEST);
        return titlePanel;
    }

    private JScrollPane createScrollPane() {
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(8, 0));
        return scrollPane;
    }

    public void displayDiagrams(Project project) {
        this.project = project;
        this.diagrams = project.getDiagrams();
        contentPanel.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(4, 12, 0, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        for (Diagram diagram : diagrams) {
            if (!expandedStates.containsKey(diagram.getName())) {
                expandedStates.put(diagram.getName(), true);
            }

            contentPanel.add(createDiagramPanel(diagram), gbc);
            gbc.gridy++;
        }

        gbc.weighty = 1.0;
        contentPanel.add(Box.createVerticalGlue(), gbc);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private JPanel createDiagramPanel(Diagram diagram) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BACKGROUND);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        // Simplified arrow indicator
        JLabel arrowLabel = new JLabel(expandedStates.get(diagram.getName()) ? "⌄" : "›");
        arrowLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        arrowLabel.setForeground(TEXT_SECONDARY);

        // Title and count
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        titlePanel.setBackground(BACKGROUND);

        JLabel nameLabel = new JLabel(diagram.getName());
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        nameLabel.setForeground(TEXT_PRIMARY);

        JLabel countLabel = new JLabel(String.valueOf(diagram.getComponents().size()));
        countLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        countLabel.setForeground(TEXT_SECONDARY);

        titlePanel.add(arrowLabel);
        titlePanel.add(nameLabel);
        titlePanel.add(countLabel);

        headerPanel.add(titlePanel, BorderLayout.CENTER);

        // Click handling
        headerPanel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                expandedStates.put(diagram.getName(), !expandedStates.get(diagram.getName()));
                displayDiagrams(project);
                if (diagramDisplayAction != null) {
                    diagramDisplayAction.actionPerformed(
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

        panel.add(headerPanel, BorderLayout.NORTH);

        // Components section
        if (expandedStates.get(diagram.getName())) {
            JPanel componentsPanel = new JPanel();
            componentsPanel.setLayout(new BoxLayout(componentsPanel, BoxLayout.Y_AXIS));
            componentsPanel.setBackground(BACKGROUND);
            componentsPanel.setBorder(BorderFactory.createEmptyBorder(4, 28, 4, 4));

            for (Component component : diagram.getComponents()) {
                componentsPanel.add(createComponentPanel(component));
                componentsPanel.add(Box.createVerticalStrut(4));
            }

            panel.add(componentsPanel, BorderLayout.CENTER);
        }

        return panel;
    }

    private JPanel createComponentPanel(Component component) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));

        String componentName = (component.getName() != null) ?
                component.getName() : component.getClass().getSimpleName();

        JLabel nameLabel = new JLabel(componentName);
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        nameLabel.setForeground(TEXT_PRIMARY);

        panel.add(nameLabel, BorderLayout.CENTER);

        panel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (propertiesDisplayAction != null) {
                    propertiesDisplayAction.actionPerformed(
                            new ActionEvent(component, ActionEvent.ACTION_PERFORMED, "showProperties")
                    );
                }
            }

            public void mouseEntered(MouseEvent e) {
                panel.setBackground(HOVER);
            }

            public void mouseExited(MouseEvent e) {
                panel.setBackground(BACKGROUND);
            }
        });

        return panel;
    }

    public void addActionListeners(ActionListener diagramListener, ActionListener propertiesListener) {
        this.diagramDisplayAction = diagramListener;
        this.propertiesDisplayAction = propertiesListener;
    }


    public void update() {
        displayDiagrams(project);
    }

    @Override
    public void updateFromProject() {

    }
}