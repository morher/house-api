package net.morher.house.api.event;

/**
 * An EventGateway serves as an event link to other house nodes. Events are forwarded both from services on the local node, as
 * well as events received through other gateways.
 * 
 * @author Morten Hermansen
 */
public interface EventGateway {

    /**
     * Dispatch an event from another node to this node and all other gateways. The Event should not be dispatched to the
     * {@link EventListener} associated with this gateway through {@link EventDispatcher#createGateway(EventListener)}.
     * 
     * @param event
     */
    void dispatchEvent(Event event);

    /**
     * Closes the gateway. Calls to {@link #dispatchEvent(Event)} after closing the gateway will be ignored.
     */
    void close();
}
