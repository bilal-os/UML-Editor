package Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.Component;

public class CustomMessageDialog {
    // Constants for dialog themes
    private static final Color SUCCESS_COLOR = new Color(76, 175, 80);
    private static final Color ERROR_COLOR = new Color(244, 67, 54);
    private static final Color WARNING_COLOR = new Color(255, 152, 0);
    private static final Color INFO_COLOR = new Color(33, 150, 243);

    // Method to show success message
    public static void showSuccess(Component parentComponent, String message, String title) {
        UIManager.put("OptionPane.background", SUCCESS_COLOR.brighter());
        UIManager.put("Panel.background", SUCCESS_COLOR.brighter());
        UIManager.put("OptionPane.messageForeground", Color.WHITE);

        JLabel label = new JLabel(message);
        label.setFont(new Font("Arial", Font.BOLD, 14));

        JOptionPane.showMessageDialog(
                parentComponent,
                label,
                title,
                JOptionPane.INFORMATION_MESSAGE,
                createCustomIcon("✓", SUCCESS_COLOR)
        );
        resetUIManager();
    }

    // Method to show error message
    public static void showError(Component parentComponent, String message, String title) {
        UIManager.put("OptionPane.background", ERROR_COLOR.brighter());
        UIManager.put("Panel.background", ERROR_COLOR.brighter());
        UIManager.put("OptionPane.messageForeground", Color.WHITE);

        JLabel label = new JLabel(message);
        label.setFont(new Font("Arial", Font.BOLD, 14));

        JOptionPane.showMessageDialog(
                parentComponent,
                label,
                title,
                JOptionPane.ERROR_MESSAGE,
                createCustomIcon("×", ERROR_COLOR)
        );
        resetUIManager();
    }

    // Method to show warning message
    public static void showWarning(Component parentComponent, String message, String title) {
        UIManager.put("OptionPane.background", WARNING_COLOR.brighter());
        UIManager.put("Panel.background", WARNING_COLOR.brighter());
        UIManager.put("OptionPane.messageForeground", Color.WHITE);

        JLabel label = new JLabel(message);
        label.setFont(new Font("Arial", Font.BOLD, 14));

        JOptionPane.showMessageDialog(
                parentComponent,
                label,
                title,
                JOptionPane.WARNING_MESSAGE,
                createCustomIcon("!", WARNING_COLOR)
        );
        resetUIManager();
    }

    // Method to show info message
    public static void showInfo(Component parentComponent, String message, String title) {
        UIManager.put("OptionPane.background", INFO_COLOR.brighter());
        UIManager.put("Panel.background", INFO_COLOR.brighter());
        UIManager.put("OptionPane.messageForeground", Color.WHITE);

        JLabel label = new JLabel(message);
        label.setFont(new Font("Arial", Font.BOLD, 14));

        JOptionPane.showMessageDialog(
                parentComponent,
                label,
                title,
                JOptionPane.INFORMATION_MESSAGE,
                createCustomIcon("i", INFO_COLOR)
        );
        resetUIManager();
    }

    // Helper method to create custom icons
    private static Icon createCustomIcon(String text, Color color) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw circle background
                g2.setColor(color);
                g2.fillOval(x, y, getIconWidth(), getIconHeight());

                // Draw text
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 16));
                FontMetrics fm = g2.getFontMetrics();
                int textX = x + (getIconWidth() - fm.stringWidth(text)) / 2;
                int textY = y + (getIconHeight() - fm.getHeight()) / 2 + fm.getAscent();
                g2.drawString(text, textX, textY);

                g2.dispose();
            }

            @Override
            public int getIconWidth() {
                return 24;
            }

            @Override
            public int getIconHeight() {
                return 24;
            }
        };
    }

    // Reset UI Manager properties
    private static void resetUIManager() {
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        UIManager.put("OptionPane.messageForeground", null);
    }
}