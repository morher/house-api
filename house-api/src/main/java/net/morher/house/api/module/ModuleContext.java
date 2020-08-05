package net.morher.house.api.module;

import net.morher.house.api.event.EventDispatcher;
import net.morher.house.api.scheduling.Scheduler;
import net.morher.house.api.security.SecurityContext;

/**
 * Holds the context for a house module.
 * 
 * @author Morten Hermansen
 */
public interface ModuleContext {

    /**
     * @return The module {@link Scheduler}
     */
    Scheduler getScheduler();

    /**
     * @return The module {@link EventDispatcher}
     */
    EventDispatcher getEventDispatcher();

    /**
     * @return The node {@link SecurityContext}
     */
    SecurityContext getSecurityContext();
}
