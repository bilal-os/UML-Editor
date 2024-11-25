package UILayer;

import Utilities.Component;
import Utilities.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ComponentPanel extends JPanel implements Observer {
    private Component component;
    private JLabel nameLabel;
    ActionListener propertiesPanelListener;
    public ComponentPanel(Component component,ActionListener propertiesPanelListener) {
        this.component = component;
        this.propertiesPanelListener = propertiesPanelListener;
        component.getNameProperty().addObserver(this);
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));

        nameLabel = new JLabel(component.getName() != null ? component.getName() : component.getClass().getSimpleName());
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        nameLabel.setForeground(Color.BLACK);

        add(nameLabel, BorderLayout.CENTER);

        // Event handling for mouse interactions
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                // Handle the mouse press, e.g., open properties or modify the component
                System.out.println("Component clicked: " + component.getName());
                if (propertiesPanelListener != null) {
                    propertiesPanelListener.actionPerformed(
                            new ActionEvent(component, ActionEvent.ACTION_PERFORMED, "showProperties")
                    );
                }
            }

            public void mouseEntered(MouseEvent e) {
                setBackground(Color.LIGHT_GRAY);
            }

            public void mouseExited(MouseEvent e) {
                setBackground(Color.WHITE);
            }
        });
    }

    private void changeComponentName(String name) {
        if (name != null && !name.equals(nameLabel.getText())) {
            nameLabel.setText(name); // Update the displayed name when the component's name changes
        }
    }

    @Override
    public void update() {
        changeComponentName(component.getName()); // Update name when notified by the observer
    }
}
