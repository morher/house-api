package net.morher.house.api.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import net.morher.house.api.module.ModuleContext;

public class BaseServiceTest {

    @Test
    @DisplayName("Context() should throw an exception when module is not initialized")
    public void contextNotInitialized() {
        Assertions.assertThrows(IllegalStateException.class, createService()::context);
    }

    @Test
    @DisplayName("Context() should return the same context the module was initialized with")
    public void moduleProperlyInitialized() {
        ModuleContext context = Mockito.mock(ModuleContext.class);

        BaseService service = createService();
        service.initialize(context);

        Assertions.assertSame(service.context(), context);
    }

    @Test
    @DisplayName("Module should not be initialized more than once")
    public void cannotInitializeTwice() {
        ModuleContext context = Mockito.mock(ModuleContext.class);

        BaseService service = createService();
        service.initialize(context);

        Assertions.assertThrows(IllegalStateException.class, () -> service.initialize(context));
    }

    private BaseService createService() {
        return new TestService();
    }

    private static class TestService extends BaseService {

    }
}
