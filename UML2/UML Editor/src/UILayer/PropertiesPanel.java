package UILayer;

import javax.swing.*;
import java.awt.*;

public class PropertiesPanel extends JPanel {
    private JTextArea propertiesArea;
    private JButton saveButton;

    public PropertiesPanel() {
        setLayout(new BorderLayout(10, 10));  // Padding for better spacing
        setPreferredSize(new Dimension(300, 600));  // Consistent size

        propertiesArea = new JTextArea();
        propertiesArea.setEditable(false);
        propertiesArea.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(propertiesArea);
        add(scrollPane, BorderLayout.CENTER);

        // Save button for interaction
        saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveProperties());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void updateProperties(String properties) {
        propertiesArea.setText(properties);
    }

    public void saveProperties() {
        System.out.println("Properties saved");
    }

    public void resetProperties() {
        propertiesArea.setText("");
    }
}
