package net.morher.house.api.event;

/**
 * The EventDispatcher is responsible for delivering events from a module service to the node container as well as the
 * {@link EventGateway}s. Module services can also create {@link EventGateway}s, where events from other nodes are forwarded,
 * here.
 * 
 * @author Morten Hermansen
 */
public interface EventDispatcher {

    /**
     * Dispatch a local event to all active services and gateways.
     * 
     * @param event
     */
    void dispatchEvent(Event event);

    /**
     * 
     * 
     * @param ownerModule
     * @param eventListener
     * @return
     */
    EventGateway createGateway(EventListener eventListener);

    void close();
}
