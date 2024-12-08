package Utilities.Component;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ComponentObserverTest {

    @Test
    void testUpdateFromComponent() {
        Component mockComponent = mock(Component.class);
        ComponentObserver observer = new ComponentObserver() {
            @Override
            public void updateFromComponent(Component component) {
                assertEquals(mockComponent, component);
            }
        };

        observer.updateFromComponent(mockComponent);
    }
}
