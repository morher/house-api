package net.morher.house.api.event;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Events and the event distribution system is the central nerve of House. Events can be notifications from sensors, timer
 * events, commands or reactions to other events. Events are sent to all services running in all of the nodes in the House
 * network.
 * 
 * Services send events through the {@link EventDispatcher} and from there the node container is responsible for distributing it
 * to all local running services, including the one who sent it. The node container will also forward the event to all
 * {@link EventGateway}s.
 * 
 * Some services facilitates communication with other nodes. Once a connection is established, an {@link EventGateway} will be
 * created. When such a service receives an event to be forwarded, it will dispatch the message through the
 * {@link EventGateway}. Events dispatch through a gateway will not be delivered back to the same gateway, but it will be
 * delivered to all local services and all other gateways.
 * 
 * The {@code type} signals what has happened, or alternatively what action is requested. The event can also carry a
 * {@code payload} in the form of key-value pairs. Both the key and the value are {@link String}s. Events will automatically be
 * assigned an {@code id} for tracking purposes.
 * 
 * @author Morten Hermansen
 */
public class Event {
    private final String id;
    private final String type;
    private final Map<String, String> payload;

    public Event(String id, String type, Map<String, String> payload) {
        this.id = id;
        this.type = type;
        this.payload = payload != null
                ? Collections.unmodifiableMap(payload)
                : Collections.emptyMap();
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Map<String, String> getPayload() {
        return payload;
    }

    public String getString(String key) {
        return payload.get(key);
    }

    public static Builder ofType(String type) {
        return new Builder(type);
    }

    public static Builder withIdAndType(String id, String type) {
        return new Builder(id, type);
    }

    /**
     * A builder for the {@link Event}. For new events, use {@link Event#ofType(String)}. For events received through a gateway
     * should be created
     * 
     * @author Morten Hermansen
     */
    public static class Builder {
        private final String id;
        private final String type;

        private final Map<String, String> payload = new HashMap<>();

        private Builder(String type) {
            this(generateId(), type);
        }

        private Builder(String id, String type) {
            this.id = id;
            this.type = type;
        }

        public Builder withAttribute(String key, String value) {
            payload.put(key, value);
            return this;
        }

        public Event build() {
            return new Event(id, type, payload);
        }

        private static String generateId() {
            return UUID.randomUUID().toString();
        }
    }
}
