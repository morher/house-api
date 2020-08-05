package net.morher.house.api.service;

import net.morher.house.api.config.ConfigurationElement;
import net.morher.house.api.event.Event;
import net.morher.house.api.module.ModuleContext;

/**
 * Base implementation of {@link HouseService}. Extend this to create a custom service.
 * 
 * @author Morten Hermansen
 */
public abstract class BaseService implements HouseService {
    private ModuleContext context;

    protected final ModuleContext context() {
        if (context == null) {
            throw new IllegalStateException("Module is not initialized");
        }
        return this.context;
    }

    @Override
    public final void initialize(ModuleContext context) {
        if (this.context != null) {
            throw new IllegalStateException("Module is already initialized");
        }
        this.context = context;
        this.doInitiate();

    }

    protected void doInitiate() {
        // Override to do special initialization of the module...
    }

    @Override
    public void configure(ConfigurationElement config) {
        // Do nothing...

    }

    @Override
    public void receiveEvent(Event event) {
        // Do nothing...

    }

    @Override
    public void tearDown() {
        // Do nothing...
    }

}
