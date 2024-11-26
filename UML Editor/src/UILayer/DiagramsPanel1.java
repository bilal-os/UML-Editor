package UILayer;

import Utilities.Diagram;
import Utilities.Observer;
import Utilities.Project;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DiagramsPanel1 extends JPanel implements Observer {
    // Core data structures
    private ArrayList<Diagram> diagrams;
    private Project currentProject;

    // UI Components
    private JPanel contentPanel;
    private JScrollPane scrollPane;

    // Action Listeners
    private ActionListener componentPaletteListener;
    private ActionListener propertiesPanelListener;

    // Colors and Styling
    private static final Color BACKGROUND_COLOR = new Color(248, 249, 250);
    private static final Color TITLE_COLOR = new Color(52, 58, 64);
    private static final Color BORDER_COLOR = new Color(222, 226, 230);
    private static final Color DIAGRAM_HOVER_COLOR = new Color(233, 236, 239);

    public DiagramsPanel1(Project currentProject) {
        this.currentProject = currentProject;
        this.diagrams = new ArrayList<>(currentProject.getDiagrams());

        currentProject.addObserver(this);

        initializeModernUI();
        displayDiagrams();
    }

    private void initializeModernUI() {
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);

        // Modern title panel
        JPanel titlePanel = createModernTitlePanel();

        // Content panel with more modern styling
        contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(BACKGROUND_COLOR);
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Scroll pane with subtle border
        scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(new CompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_COLOR),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Smoother scrolling

        add(titlePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setPreferredSize(new Dimension(320, 650));
    }

    private JPanel createModernTitlePanel() {
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(BACKGROUND_COLOR);
        titlePanel.setBorder(new CompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR),
                new EmptyBorder(12, 15, 12, 15)
        ));

        JLabel titleLabel = new JLabel("Project Diagrams");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(TITLE_COLOR);

        titlePanel.add(titleLabel, BorderLayout.WEST);
        return titlePanel;
    }

    public void displayDiagrams() {
        contentPanel.removeAll();

        diagrams = currentProject.getDiagrams();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);

        for (Diagram diagram : diagrams) {
            DiagramPanel diagramPanel = new DiagramPanel(
                    diagram,
                    componentPaletteListener,
                    propertiesPanelListener
            );

            // Add hover and modern styling to diagram panels
            diagramPanel.setBackground(Color.WHITE);
            diagramPanel.setBorder(new CompoundBorder(
                    new LineBorder(BORDER_COLOR, 1, true),
                    new EmptyBorder(10, 15, 10, 15)
            ));

            // Add hover effect
            diagramPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    diagramPanel.setBackground(DIAGRAM_HOVER_COLOR);
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    diagramPanel.setBackground(Color.WHITE);
                }
            });

            contentPanel.add(diagramPanel, gbc);
            gbc.gridy++;
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // Remaining methods stay the same as in previous implementation
    public void addActionListeners(
            ActionListener componentPaletteListener,
            ActionListener propertiesPanelListener
    ) {
        this.componentPaletteListener = componentPaletteListener;
        this.propertiesPanelListener = propertiesPanelListener;
        displayDiagrams();
    }

    @Override
    public void update() {
        displayDiagrams();
    }


}