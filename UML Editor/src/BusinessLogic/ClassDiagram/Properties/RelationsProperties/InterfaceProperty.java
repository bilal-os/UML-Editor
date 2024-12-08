package BusinessLogic.ClassDiagram.Properties.RelationsProperties;

import static org.junit.jupiter.api.Assertions.*;

import BusinessLogic.ClassDiagram.Properties.InterfaceProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Utilities.Component.Component;

import static org.mockito.Mockito.*;

class InterfacePropertyTest {

    private Component mockComponent;

    @BeforeEach
    void setUp() {
        mockComponent = mock(Component.class); // Mock the Component dependency
    }

    @Test
    void testConstructorValidInterfaceName() {
        String type = "Interface Name";
        String validValue = "MyInterface";

        assertDoesNotThrow(() -> new InterfaceProperty(type, validValue, mockComponent),
                "Valid interface name should not throw an exception");
    }

    @Test
    void testConstructorInvalidInterfaceName() {
        String type = "Interface Name";
        String invalidValue = "myInterface"; // Starts with lowercase

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new InterfaceProperty(type, invalidValue, mockComponent));

        assertTrue(exception.getMessage().contains("Invalid Interface Name"),
                "Exception message should indicate invalid interface name syntax");
    }

    @Test
    void testConstructorInvalidInterfaceType() {
        String type = "Unknown Type";
        String value = "MyInterface";

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new InterfaceProperty(type, value, mockComponent));

        assertTrue(exception.getMessage().contains("Unknown property type for Interface"),
                "Exception message should indicate unknown property type");
    }

    @Test
    void testAddValueValidMethod() {
        InterfaceProperty property = new InterfaceProperty("Method", "+methodName(param1, param2) : void", mockComponent);
        String newValue = "+anotherMethod() : int";

        assertDoesNotThrow(() -> property.addValue(newValue),
                "Valid method should not throw an exception");
        assertEquals(newValue, property.getValue(), "Method value should be updated correctly");
    }

    @Test
    void testAddValueInvalidMethod() {
        InterfaceProperty property = new InterfaceProperty("Method", "+methodName(param1, param2) : void", mockComponent);
        String invalidValue = "methodName(param1) : void"; // Missing '+'

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> property.addValue(invalidValue));

        assertTrue(exception.getMessage().contains("Invalid Interface Method"),
                "Exception message should indicate invalid method syntax");
    }

    @Test
    void testAddValueInvalidWidthHeight() {
        InterfaceProperty property = new InterfaceProperty("Width", "100", mockComponent);
        String invalidValue = "notANumber"; // Invalid numeric value

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> property.addValue(invalidValue));

        assertTrue(exception.getMessage().contains("Width and Height must be numeric values."),
                "Exception message should indicate invalid width/height input");
    }

    @Test
    void testIsValidNameValid() {
        InterfaceProperty property = new InterfaceProperty();
        assertTrue(property.isValidName("MyInterface"), "Valid interface name should return true");
        assertTrue(property.isValidName("My_Interface123"), "Interface name with underscore and numbers should return true");
    }

    @Test
    void testIsValidNameInvalid() {
        InterfaceProperty property = new InterfaceProperty();
        assertFalse(property.isValidName("myInterface"), "Interface name starting with lowercase should return false");
        assertFalse(property.isValidName("123Interface"), "Interface name starting with a number should return false");
        assertFalse(property.isValidName(""), "Empty interface name should return false");
        assertFalse(property.isValidName(null), "Null interface name should return false");
    }

    @Test
    void testIsValidMethodValid() {
        InterfaceProperty property = new InterfaceProperty();
        assertTrue(property.isValidMethod("+myMethod(param1, param2) : void"), "Valid method should return true");
        assertTrue(property.isValidMethod("+methodName() : int"), "Method with no parameters should return true");
        assertTrue(property.isValidMethod("+methodWithGenerics<T> (param) : void"), "Method with generics should return true");
    }

    @Test
    void testIsValidMethodInvalid() {
        InterfaceProperty property = new InterfaceProperty();
        assertFalse(property.isValidMethod("methodName(param1) : void"), "Method missing '+' should return false");
        assertFalse(property.isValidMethod("+methodName(param1) : "), "Method missing return type should return false");
        assertFalse(property.isValidMethod("+methodName()"), "Method missing return type should return false");
        assertFalse(property.isValidMethod("+methodName(param1) : int extra"), "Method with extra characters should return false");
    }
}
