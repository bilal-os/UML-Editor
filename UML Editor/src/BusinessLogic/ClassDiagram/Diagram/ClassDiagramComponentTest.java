package BusinessLogic.ClassDiagram.Diagram;

import BusinessLogic.ClassDiagram.Components.Class;
import BusinessLogic.ClassDiagram.Components.Package;
import BusinessLogic.ClassDiagram.Diagram.ClassDiagram;
import BusinessLogic.ClassDiagram.Components.*;
import org.junit.jupiter.api.Test;

import java.awt.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import Utilities.Component.Component;  // Fully qualifying the Component class

class ClassDiagramTest {

    @Test
    void testClassDiagramConstructor() {
        ClassDiagram classDiagram = new ClassDiagram();
        assertNotNull(classDiagram);
        assertEquals("Class Diagram", classDiagram.getName());
        assertEquals(0, classDiagram.getComponents().size());
    }

    @Test
    void testClassDiagramWithId() {
        ClassDiagram classDiagram = new ClassDiagram(1);
        assertNotNull(classDiagram);
        assertEquals("Class Diagram", classDiagram.getName());
        assertEquals(1, classDiagram.getId());
        assertTrue(classDiagram.getComponentNames().contains("Class"));
    }

    @Test
    void testCreateClassComponent() {
        ClassDiagram classDiagram = new ClassDiagram();
        Component classComponent = (Component) classDiagram.createComponent("Class");
        assertNotNull(classComponent);
        assertTrue(classComponent instanceof Class);
        assertEquals("NewClass", classComponent.getName());
    }

    @Test
    void testCreateInterfaceComponent() {
        ClassDiagram classDiagram = new ClassDiagram();
        Component interfaceComponent = (Component) classDiagram.createComponent("Interface");
        assertNotNull(interfaceComponent);
        assertTrue(interfaceComponent instanceof Interface);
        assertEquals("NewInterface", interfaceComponent.getName());
    }

    @Test
    void testCreateEnumComponent() {
        ClassDiagram classDiagram = new ClassDiagram();
        Component enumComponent = (Component) classDiagram.createComponent("Enum");
        assertNotNull(enumComponent);
        assertTrue(enumComponent instanceof Enumeration);
        assertEquals("NewEnum", enumComponent.getName());
    }

    @Test
    void testCreatePackageComponent() {
        ClassDiagram classDiagram = new ClassDiagram();
        Component packageComponent = (Component) classDiagram.createComponent("Package");
        assertNotNull(packageComponent);
        assertTrue(packageComponent instanceof Package);
        assertEquals("NewPackage", packageComponent.getName());
    }


    @Test
    void testComponentNames() {
        ClassDiagram classDiagram = new ClassDiagram(1);
        assertTrue(classDiagram.getComponentNames().contains("Class"));
        assertTrue(classDiagram.getComponentNames().contains("Interface"));
        assertTrue(classDiagram.getComponentNames().contains("Enum"));
        assertTrue(classDiagram.getComponentNames().contains("Association"));
        assertTrue(classDiagram.getComponentNames().contains("Abstract Class"));
        assertTrue(classDiagram.getComponentNames().contains("Package"));
        assertTrue(classDiagram.getComponentNames().contains("Inheritance"));
        assertTrue(classDiagram.getComponentNames().contains("Composition"));
        assertTrue(classDiagram.getComponentNames().contains("Aggregation"));
    }
}
