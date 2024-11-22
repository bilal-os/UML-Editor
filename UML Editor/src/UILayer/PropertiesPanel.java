package UILayer;

import Utilities.Property;
import Utilities.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;

public class PropertiesPanel extends JPanel {
    private JTable propertiesTable;
    private DefaultTableModel tableModel;
    private Component component;

    static class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        public ButtonRenderer getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "Delete" : value.toString());
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private final JButton button;
        private String label;
        private boolean clicked;
        private int row;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public JButton getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "Delete" : value.toString();
            button.setText(label);
            clicked = true;
            this.row = row;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (clicked) {
                // Perform delete operation
                deleteProperty(row);
            }
            clicked = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            clicked = false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }

    public PropertiesPanel() {
        setupPanel();
    }

    private void setupPanel() {
        setLayout(new BorderLayout(10, 10));
        setPreferredSize(new Dimension(450, 700));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        add(createTitlePanel(), BorderLayout.NORTH);
        add(createTableScrollPane(), BorderLayout.CENTER);
    }

    private JPanel createTitlePanel() {
        JLabel titleLabel = new JLabel("Properties Panel", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.setBackground(new Color(240, 240, 240));
        return titlePanel;
    }

    private JScrollPane createTableScrollPane() {
        String[] columnNames = {"Property Type", "Value", "Action"};

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column < 2; // Allow editing for 'Property Type' and 'Value'
            }
        };

        propertiesTable = new JTable(tableModel) {
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 2 ? JButton.class : super.getColumnClass(column);
            }
        };
        configureTable();
        return new JScrollPane(propertiesTable);
    }

    private void configureTable() {
        propertiesTable.setRowHeight(40);
        propertiesTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        propertiesTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        // Configure Action column
        TableColumn actionColumn = propertiesTable.getColumnModel().getColumn(2);
        actionColumn.setCellRenderer(new ButtonRenderer());
        actionColumn.setCellEditor(new ButtonEditor(new JCheckBox()));
    }

    private void deleteProperty(int selectedRow) {
        if (selectedRow != -1) {
            // Remove from component properties if linked
            if (component != null && selectedRow < component.getProperties().size()) {
                component.getProperties().remove(selectedRow);
            }
            // Remove row from table model
            tableModel.removeRow(selectedRow);

            // Refresh the table to reflect changes
            refreshTableData();
        }
    }

    public void displayProperties(Component component) {
        this.component = component;
        refreshTableData();
    }

    private void refreshTableData() {
        tableModel.setRowCount(0); // Clear the table
        if (component != null) {
            for (Property property : component.getProperties()) {
                tableModel.addRow(new Object[]{property.gettype(), property.getValue(), "Delete"});
            }
        }
    }

}