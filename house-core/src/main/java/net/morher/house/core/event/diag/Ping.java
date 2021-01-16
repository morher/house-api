package net.morher.house.core.event.diag;

import net.morher.house.core.event.template.EventAttribute;
import net.morher.house.core.event.template.EventSubject;
import net.morher.house.core.event.template.EventType;

/**
 * Event type for Ping.
 * 
 * @author Morten Hermansen
 */
public interface Ping {
    public static final String EVENT_TYPE_PING = "diag.ping";
    public static final String EVENT_TYPE_PONG = "diag.pong";

    /**
     * Send ping to the other nodes.
     * 
     * @param returnValue
     *            A value that should be returned in the pong message. Can be {@code null}.
     * @param targetSubject
     *            The target node. Can be {@code null} for all nodes.
     */
    @EventType(EVENT_TYPE_PING)
    void ping(@EventAttribute("returnValue") String returnValue, @EventSubject String targetSubject);

    /**
     * Answer a ping-event.
     * 
     * @param returnValue
     *            The value given in the ping event.
     * @param fromSubject
     *            Typically the node name.
     */
    @EventType(EVENT_TYPE_PONG)
    void pong(@EventAttribute("returnValue") String returnValue, @EventSubject String fromSubject);
}
