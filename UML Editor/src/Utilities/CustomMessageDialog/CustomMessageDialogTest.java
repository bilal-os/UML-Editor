package Utilities.CustomMessageDialog;

import Utilities.CustomMessageDialog.CustomMessageDialog;
import org.junit.jupiter.api.Test;
import org.assertj.swing.fixture.FrameFixture;

import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class CustomMessageDialogTest {

    @Test
    void testShowSuccessDialog() {
        SwingUtilities.invokeLater(() -> {
            JFrame testFrame = new JFrame();
            testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            testFrame.setVisible(true);

            // Call the method under test
            assertDoesNotThrow(() -> CustomMessageDialog.showSuccess(testFrame, "Operation successful!", "Success"));
        });
    }

    @Test
    void testShowErrorDialog() {
        SwingUtilities.invokeLater(() -> {
            JFrame testFrame = new JFrame();
            testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            testFrame.setVisible(true);

            // Call the method under test
            assertDoesNotThrow(() -> CustomMessageDialog.showError(testFrame, "An error occurred!", "Error"));
        });
    }

    @Test
    void testShowWarningDialog() {
        SwingUtilities.invokeLater(() -> {
            JFrame testFrame = new JFrame();
            testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            testFrame.setVisible(true);

            // Call the method under test
            assertDoesNotThrow(() -> CustomMessageDialog.showWarning(testFrame, "This is a warning!", "Warning"));
        });
    }

    @Test
    void testShowInfoDialog() {
        SwingUtilities.invokeLater(() -> {
            JFrame testFrame = new JFrame();
            testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            testFrame.setVisible(true);

            // Call the method under test
            assertDoesNotThrow(() -> CustomMessageDialog.showInfo(testFrame, "Here is some information!", "Information"));
        });
    }

    @Test
    void testCustomIconCreation() {
        Icon icon = assertDoesNotThrow(() -> CustomMessageDialog.createCustomIcon("✓", Color.GREEN), "Icon creation should not throw an exception");
        assertNotNull(icon, "Custom icon should not be null");
        assertEquals(24, icon.getIconWidth(), "Icon width should be 24");
        assertEquals(24, icon.getIconHeight(), "Icon height should be 24");
    }

    @Test
    void testResetUIManager() {
        // Manually modify UIManager properties
        UIManager.put("OptionPane.background", Color.RED);
        UIManager.put("Panel.background", Color.RED);
        UIManager.put("OptionPane.messageForeground", Color.RED);

        // Call resetUIManager
        assertDoesNotThrow(CustomMessageDialog::resetUIManager, "resetUIManager should not throw an exception");

        // Verify that properties are reset
        assertNull(UIManager.get("OptionPane.background"), "OptionPane.background should be reset to null");
        assertNull(UIManager.get("Panel.background"), "Panel.background should be reset to null");
        assertNull(UIManager.get("OptionPane.messageForeground"), "OptionPane.messageForeground should be reset to null");
    }
}
