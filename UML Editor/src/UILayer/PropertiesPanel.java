package UILayer;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;

import Utilities.Component;
import Utilities.Property;

public class PropertiesPanel extends JPanel {
    private static final Color PRIMARY_COLOR = new Color(51, 153, 255);
    private static final Color BACKGROUND_COLOR = new Color(248, 249, 250);
    private static final Color HEADER_COLOR = new Color(240, 240, 240);
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 16);
    private static final Font REGULAR_FONT = new Font("Segoe UI", Font.PLAIN, 12);

    private JTable propertiesTable;
    private DefaultTableModel tableModel;
    private Component component;
    private JComboBox<String> propertyTypeComboBox;
    private JTextField valueTextField;
    private JButton addButton;

    public PropertiesPanel() {
        setLayout(new BorderLayout(0, 10));
        setBackground(BACKGROUND_COLOR);

        add(createTitlePanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
        add(createFormPanel(), BorderLayout.SOUTH);

        setPreferredSize(new Dimension(450, 600));
        setBorder(createPanelBorder());
    }

    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(HEADER_COLOR);

        JLabel titleLabel = new JLabel("Properties Panel");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(new Color(51, 51, 51));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(218, 220, 224)),
                BorderFactory.createEmptyBorder(0, 0, 5, 0)
        ));

        return titlePanel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);

        tableModel = new DefaultTableModel(new Object[]{"Type", "Value"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1;
            }
        };

        propertiesTable = new JTable(tableModel);
        propertiesTable.setFont(REGULAR_FONT);
        propertiesTable.setRowHeight(30);
        propertiesTable.setShowGrid(true);
        propertiesTable.setGridColor(new Color(230, 230, 230));
        propertiesTable.setSelectionBackground(new Color(232, 240, 254));
        propertiesTable.setSelectionForeground(Color.BLACK);

        // Style the table header
        JTableHeader header = propertiesTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setBackground(HEADER_COLOR);
        header.setForeground(new Color(51, 51, 51));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(218, 220, 224)));

        // Add listener for value changes
        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getColumn() == 1 && component != null) {
                    String newValue = (String) tableModel.getValueAt(e.getFirstRow(), e.getColumn());
                    Property property = component.getProperties().get(e.getFirstRow());
                    property.setValue(newValue);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(propertiesTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(218, 220, 224)));
        scrollPane.getViewport().setBackground(Color.WHITE);

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(BACKGROUND_COLOR);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(218, 220, 224)),
                        "Add New Property",
                        javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                        javax.swing.border.TitledBorder.DEFAULT_POSITION,
                        new Font("Segoe UI", Font.BOLD, 12),
                        new Color(51, 51, 51)
                ),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Create input panel for better alignment
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Type ComboBox
        JLabel typeLabel = new JLabel("Type:");
        typeLabel.setFont(REGULAR_FONT);
        propertyTypeComboBox = new JComboBox<>();
        propertyTypeComboBox.setFont(REGULAR_FONT);
        propertyTypeComboBox.setPreferredSize(new Dimension(200, 30));

        // Value TextField
        JLabel valueLabel = new JLabel("Value:");
        valueLabel.setFont(REGULAR_FONT);
        valueTextField = new JTextField();
        valueTextField.setFont(REGULAR_FONT);
        valueTextField.setPreferredSize(new Dimension(200, 30));

        // Add button with modern styling
        addButton = createStyledButton("Add Property");

        // Layout components
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        inputPanel.add(typeLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        inputPanel.add(propertyTypeComboBox, gbc);
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        inputPanel.add(valueLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        inputPanel.add(valueTextField, gbc);

        // Button panel for center alignment
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.add(addButton);

        formPanel.add(inputPanel);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(buttonPanel);

        return formPanel;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(PRIMARY_COLOR);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(PRIMARY_COLOR.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(PRIMARY_COLOR);
            }
        });

        button.addActionListener(e -> addNewProperty());
        return button;
    }

    private CompoundBorder createPanelBorder() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(218, 220, 224), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );
    }

    private void addNewProperty() {
        if (component == null) return;

        String selectedType = (String) propertyTypeComboBox.getSelectedItem();
        String value = valueTextField.getText().trim();

        if (selectedType == null || value.isEmpty()) {
            showErrorDialog("Please select a type and enter a value.");
            return;
        }

        Property newProperty = new Property(selectedType, value);
        component.addProperty(newProperty);
        tableModel.addRow(new Object[]{selectedType, value});
        valueTextField.setText("");
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Input Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    public void displayProperties(Component component) {
        this.component = component;
        tableModel.setRowCount(0);

        if (component != null) {
            ArrayList<Property> properties = component.getProperties();
            for (Property property : properties) {
                tableModel.addRow(new Object[]{property.gettype(), property.getValue()});
            }

            propertyTypeComboBox.removeAllItems();
            for (String type : component.getPropertiesTypes()) {
                propertyTypeComboBox.addItem(type);
            }
        }
    }
}