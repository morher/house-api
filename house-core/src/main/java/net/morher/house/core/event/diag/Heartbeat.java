package net.morher.house.core.event.diag;

import net.morher.house.core.event.template.EventType;

/**
 * Event type for heartbeat.
 * 
 * @author Morten Hermansen
 */
public interface Heartbeat {
    public static final String EVENT_TYPE_HEARTBEAT = "diag.heartbeat";

    /**
     * Send heartbeat.
     */
    @EventType(EVENT_TYPE_HEARTBEAT)
    void heartbeat();
}
