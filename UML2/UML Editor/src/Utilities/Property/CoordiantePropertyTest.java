package Utilities.Property;

import Utilities.Component.Component;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CoordiantePropertyTest {
    private CoordianteProperty coordianteProperty;
    private Component mockComponent;

    @BeforeEach
    void setUp() {
        mockComponent = mock(Component.class);
        coordianteProperty = new CoordianteProperty("X Coordinate", 50, mockComponent);
    }

    // =======================
    // Test Constructor
    // =======================
    @Test
    void testConstructor() {
        assertEquals("X Coordinate", coordianteProperty.gettype());
        assertEquals("50", coordianteProperty.getValue());
        assertEquals(mockComponent, coordianteProperty.getComponent());
    }

    // =======================
    // Test Getters and Setters
    // =======================
    @Test
    void testGetComponent() {
        assertEquals(mockComponent, coordianteProperty.getComponent());
    }

    @Test
    void testSetComponent() {
        Component newMockComponent = mock(Component.class);
        coordianteProperty.setComponent(newMockComponent);
        assertEquals(newMockComponent, coordianteProperty.getComponent());
    }

    // =======================
    // Test Input Validation
    // =======================
    @Test
    void testValidateInputWithValidPositiveValue() {
        assertDoesNotThrow(() -> coordianteProperty.validateInput("X Coordinate", "100"));
    }

    @Test
    void testValidateInputWithZeroValue() {
        assertDoesNotThrow(() -> coordianteProperty.validateInput("X Coordinate", "0"));
    }

    @Test
    void testValidateInputWithNegativeValue() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                coordianteProperty.validateInput("X Coordinate", "-10")
        );
        assertEquals("Enter a valid coordinate", exception.getMessage());
    }

    @Test
    void testValidateInputWithNonNumericValue() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                coordianteProperty.validateInput("X Coordinate", "abc")
        );
        assertEquals("Value is not a valid number", exception.getMessage());
    }

    @Test
    void testValidateInputWithEmptyString() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                coordianteProperty.validateInput("X Coordinate", "")
        );
        assertEquals("Value is not a valid number", exception.getMessage());
    }

    @Test
    void testValidateInputWithNullValue() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                coordianteProperty.validateInput("X Coordinate", null)
        );
        assertEquals("Value is not a valid number", exception.getMessage());
    }

    // =======================
    // Test addValue
    // =======================
    @Test
    void testAddValueWithValidPositiveValue() {
        assertDoesNotThrow(() -> coordianteProperty.addValue("100"));
        assertEquals("100", coordianteProperty.getValue());
    }

    @Test
    void testAddValueWithZeroValue() {
        assertDoesNotThrow(() -> coordianteProperty.addValue("0"));
        assertEquals("0", coordianteProperty.getValue());
    }

    @Test
    void testAddValueWithNegativeValue() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                coordianteProperty.addValue("-10")
        );
        assertEquals("Enter a valid coordinate", exception.getMessage());
    }

    @Test
    void testAddValueWithNonNumericValue() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                coordianteProperty.addValue("abc")
        );
        assertEquals("Value is not a valid number", exception.getMessage());
    }

    @Test
    void testAddValueWithEmptyString() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                coordianteProperty.addValue("")
        );
        assertEquals("Value is not a valid number", exception.getMessage());
    }

    @Test
    void testAddValueWithNullValue() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                coordianteProperty.addValue(null)
        );
        assertEquals("Value is not a valid number", exception.getMessage());
    }

    // =======================
    // Test Serialization
    // =======================
    @Test
    void testSerialization() throws IOException, ClassNotFoundException {
        coordianteProperty.addValue("75");

        // Serialize the object
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream objectOut = new ObjectOutputStream(byteOut);
        objectOut.writeObject(coordianteProperty);

        // Deserialize the object
        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream objectIn = new ObjectInputStream(byteIn);
        CoordianteProperty deserializedProperty = (CoordianteProperty) objectIn.readObject();

        assertNotNull(deserializedProperty);
        assertEquals("X Coordinate", deserializedProperty.gettype());
        assertEquals("75", deserializedProperty.getValue());

        // The transient `component` should be null after deserialization
        assertNull(deserializedProperty.getComponent());
    }

    // =======================
    // Test Observer Notifications
    // =======================
    @Test
    void testNotifyObserversOnValueChange() {
        PropertyObserver mockObserver = mock(PropertyObserver.class);
        coordianteProperty.addObserver(mockObserver);

        coordianteProperty.addValue("80");

        // Verify observer is notified when value changes
        verify(mockObserver, times(1)).updateFromProperty(coordianteProperty);
    }

    @Test
    void testNoObserverNotificationOnInvalidValue() {
        PropertyObserver mockObserver = mock(PropertyObserver.class);
        coordianteProperty.addObserver(mockObserver);

        // Try to add invalid value, observer should not be notified
        try {
            coordianteProperty.addValue("-20");
        } catch (IllegalArgumentException ignored) {}

        verify(mockObserver, never()).updateFromProperty(coordianteProperty);
    }
}
