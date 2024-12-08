package Utilities.CustomMessageDialog;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomMessageDialogTest {

    private Component parentComponent;

    @BeforeEach
    void setUp() {
        parentComponent = mock(Component.class);
    }

    @AfterEach
    void tearDown() {
        // Reset UIManager properties after each test
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        UIManager.put("OptionPane.messageForeground", null);
    }

    @Test
    void testShowSuccessDialog() {
        CustomMessageDialog.showSuccess(parentComponent, "Success Message", "Success Title");

        // Check if UIManager colors are reset
        assertNull(UIManager.get("OptionPane.background"));
        assertNull(UIManager.get("Panel.background"));
        assertNull(UIManager.get("OptionPane.messageForeground"));
    }

    @Test
    void testShowErrorDialog() {
        CustomMessageDialog.showError(parentComponent, "Error Message", "Error Title");

        // Check if UIManager colors are reset
        assertNull(UIManager.get("OptionPane.background"));
        assertNull(UIManager.get("Panel.background"));
        assertNull(UIManager.get("OptionPane.messageForeground"));
    }

    @Test
    void testShowWarningDialog() {
        CustomMessageDialog.showWarning(parentComponent, "Warning Message", "Warning Title");

        // Check if UIManager colors are reset
        assertNull(UIManager.get("OptionPane.background"));
        assertNull(UIManager.get("Panel.background"));
        assertNull(UIManager.get("OptionPane.messageForeground"));
    }

    @Test
    void testShowInfoDialog() {
        CustomMessageDialog.showInfo(parentComponent, "Info Message", "Info Title");

        // Check if UIManager colors are reset
        assertNull(UIManager.get("OptionPane.background"));
        assertNull(UIManager.get("Panel.background"));
        assertNull(UIManager.get("OptionPane.messageForeground"));
    }

    @Test
    void testCustomIconCreation() {
        Icon successIcon = CustomMessageDialog.createCustomIcon("✓", new Color(76, 175, 80));
        assertNotNull(successIcon);
        assertEquals(24, successIcon.getIconWidth());
        assertEquals(24, successIcon.getIconHeight());
    }

    @Test
    void testCustomIconPaintMethod() {
        Icon icon = CustomMessageDialog.createCustomIcon("!", Color.RED);
        Component mockComponent = mock(Component.class);
        Graphics2D mockGraphics = mock(Graphics2D.class);

        // Call the paintIcon method
        icon.paintIcon(mockComponent, mockGraphics, 0, 0);

        verify(mockGraphics, atLeastOnce()).setColor(Color.RED);
        verify(mockGraphics, atLeastOnce()).fillOval(anyInt(), anyInt(), eq(24), eq(24));
        verify(mockGraphics, atLeastOnce()).drawString(eq("!"), anyInt(), anyInt());
    }

    @Test
    void testResetUIManager() {
        UIManager.put("OptionPane.background", Color.RED);
        UIManager.put("Panel.background", Color.BLUE);
        UIManager.put("OptionPane.messageForeground", Color.YELLOW);

        CustomMessageDialog.resetUIManager();

        assertNull(UIManager.get("OptionPane.background"));
        assertNull(UIManager.get("Panel.background"));
        assertNull(UIManager.get("OptionPane.messageForeground"));
    }
}
