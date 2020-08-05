package net.morher.house.api.service;

import net.morher.house.api.config.ConfigurationElement;
import net.morher.house.api.event.Event;
import net.morher.house.api.module.ModuleContext;

/**
 * Services are as central to House as events. Services should be made to serve a small dedicated purpose with a general event
 * interface. Some common event types can be found in the core package. The services should work together to provide more
 * advanced functionality.
 * 
 * As an example a doorbell-service would only be responsible to receive button-click events and send doorbell-events. It could
 * include functionality to block repeated clicks, but interfacing with the actual button should be outsources to a separate
 * service. On a Raspberry PI there could be a service responsible for communicating with the physical button through the GPIO
 * pins. The GPIO service would send button push and button release events that would be picked up by the doorbell service. The
 * doorbell service could send events for turning on and off the chime that the GPIO service would pick up and activate another
 * pin accordingly. Although, not connecting these services directly, but rather through general events, one could replace the
 * chime with a service sending a notification to Slack.
 * 
 * To allow for expansions to this interface in later versions, service implementations should extend {@link BaseService} rather
 * than implementing this interface directly.
 * 
 * @author Morten Hermansen
 */
public interface HouseService {

    /**
     * Initialize the module. This method should be the first to be called by the module container. It should only be called
     * once.
     * 
     * @param context
     *            The {@link ModuleContext}.
     */
    void initialize(ModuleContext context);

    /**
     * Configure the module. This method might be called several times to reconfigure the service. It can be expected that
     * {@link #initialize(ModuleContext)} is called before this method.
     * 
     * @param config
     *            The node configuration
     */
    void configure(ConfigurationElement config);

    /**
     * Receive an event from the event bus. this method should only be called after the service is initialized with a call to
     * {@link #initialize(ModuleContext)}.
     * 
     * @param event
     *            The event
     */
    void receiveEvent(Event event);

    /**
     * Method to be called when the service is stopped. The service should not expect to receive events at this point.
     */
    void tearDown();

}
