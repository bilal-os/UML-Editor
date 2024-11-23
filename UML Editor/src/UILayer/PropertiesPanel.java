package UILayer;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import Utilities.Component;
import Utilities.CustomMessageDialog;
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

    private ActionListener addPropertyActionListener;
    private ActionListener editPropertyActionListener;

    public PropertiesPanel() {
        setLayout(new BorderLayout(0, 10));
        setBackground(BACKGROUND_COLOR);

        add(createTitlePanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
        add(createFormPanel(), BorderLayout.SOUTH);

        setPreferredSize(new Dimension(450, 600));
        setBorder(createPanelBorder());
    }

    private CompoundBorder createPanelBorder() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(218, 220, 224)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );
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

        // Add a custom cell editor to validate input directly
        propertiesTable.getColumnModel().getColumn(1).setCellEditor(new ValidatingCellEditor(new JTextField()));

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
        addButton.addActionListener(e -> addNewProperty());

        // Layout components
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        inputPanel.add(typeLabel, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        inputPanel.add(propertyTypeComboBox, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        inputPanel.add(valueLabel, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
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

    private void addNewProperty() {
        if (component == null) return;

        String selectedType = (String) propertyTypeComboBox.getSelectedItem();
        String value = valueTextField.getText().trim();

        if (selectedType == null || value.isEmpty())
        {
            CustomMessageDialog.showError(this, "Please select a type and enter a value.", "Input Error");
            return;
        }

        try {
            component.addProperty(selectedType, value);
            tableModel.addRow(new Object[]{selectedType, value});
            valueTextField.setText("");
        } catch (IllegalArgumentException e) {
            CustomMessageDialog.showError(this, e.getMessage(), "Validation Error");
        }
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

        return button;
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

    private class ValidatingCellEditor extends DefaultCellEditor {
        public ValidatingCellEditor(JTextField textField) {
            super(textField);
        }

        @Override
        public boolean stopCellEditing() {
            try {
                int editingRow = propertiesTable.getEditingRow();
                String newValue = (String) getCellEditorValue();
                Property property = component.getProperties().get(editingRow);
                property.setValue(newValue); // Validate new value
                return super.stopCellEditing();
            } catch (IllegalArgumentException ex) {
                CustomMessageDialog.showError(propertiesTable, ex.getLocalizedMessage(), "Validation Error");
                return false; // Prevent the editor from stopping
            }
        }
    }

    public void addActionListeners(ActionListener addPropertyActionListener, ActionListener editPropertyActionListener)
    {
        this.addPropertyActionListener=addPropertyActionListener;
        this.editPropertyActionListener=editPropertyActionListener;
    }

}