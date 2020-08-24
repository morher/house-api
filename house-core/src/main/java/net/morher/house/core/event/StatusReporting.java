package net.morher.house.core.event;

import net.morher.house.core.event.template.EventType;

/**
 * Common event template for requesting a status update from a subject. A subject supporting status reports should send back a
 * report event in whatever style is native to it.
 * 
 * @author Morten Hermansen
 */
public interface StatusReporting {
    public static final String EVENT_TYPE_POLL_STATE = "common.pollState";

    @EventType(EVENT_TYPE_POLL_STATE)
    void pollState();
}
