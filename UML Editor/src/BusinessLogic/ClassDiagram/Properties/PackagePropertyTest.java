package BusinessLogic.ClassDiagram.Properties;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Utilities.Component.Component;
import static org.mockito.Mockito.*;

class PackagePropertyTest {

    private Component mockComponent;
    private PackageProperty packageProperty;

    @BeforeEach
    void setUp() {
        mockComponent = mock(Component.class);  // Mock the Component dependency
        packageProperty = new PackageProperty("Package Name", "MyPackage", mockComponent); // Instantiate PackageProperty
    }

    @Test
    void testConstructorValidPackageName() {
        String type = "Package Name";
        String validValue = "MyPackage";

        assertDoesNotThrow(() -> new PackageProperty(type, validValue, mockComponent),
                "Valid package name should not throw an exception");
    }

    @Test
    void testConstructorInvalidPackageName() {
        String type = "Package Name";
        String invalidValue = "mypackage";  // Starts with lowercase letter

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new PackageProperty(type, invalidValue, mockComponent));

        assertTrue(exception.getMessage().contains("Invalid Package Name"),
                "Exception message should indicate invalid package name syntax");
    }

    @Test
    void testConstructorUnknownPropertyType() {
        String type = "Unknown Type";
        String value = "MyPackage";

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new PackageProperty(type, value, mockComponent));

        assertTrue(exception.getMessage().contains("Unknown property type for Package"),
                "Exception message should indicate unknown property type");
    }

    @Test
    void testIsValidNameValid() {
        PackageProperty property = new PackageProperty();
        assertTrue(property.isValidName("MyPackage"), "Valid package name should return true");
        assertTrue(property.isValidName("Package_123"), "Package name with underscore and numbers should return true");
    }

    @Test
    void testIsValidNameInvalid() {
        PackageProperty property = new PackageProperty();
        assertFalse(property.isValidName("mypackage"), "Package name starting with lowercase should return false");
        assertFalse(property.isValidName("123Package"), "Package name starting with a number should return false");
        assertFalse(property.isValidName(""), "Empty package name should return false");
        assertFalse(property.isValidName(null), "Null package name should return false");
    }
}
