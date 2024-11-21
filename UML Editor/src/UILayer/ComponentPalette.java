package UILayer;

import javax.swing.*;
import java.awt.*;

public class ComponentPalette extends JPanel {
    public ComponentPalette() {
        setLayout(new GridLayout(0, 1, 5, 10));  // Grid layout with better spacing

        // Adding buttons with consistent style
        JButton classButton = createStyledButton("Class");
        classButton.addActionListener(e -> addClassToCanvas());
        add(classButton);

        JButton packageButton = createStyledButton("Package");
        packageButton.addActionListener(e -> addPackageToCanvas());
        add(packageButton);
    }

    private JButton createStyledButton(String label) {
        JButton button = new JButton(label);
        button.setPreferredSize(new Dimension(150, 40));  // Consistent button size
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(new Color(0, 123, 255));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }

    private void addClassToCanvas() {
        System.out.println("Class component added");
    }

    private void addPackageToCanvas() {
        System.out.println("Package component added");
    }
}
