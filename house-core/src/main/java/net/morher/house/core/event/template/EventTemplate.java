package net.morher.house.core.event.template;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import net.morher.house.api.event.Event;
import net.morher.house.api.event.Event.Builder;
import net.morher.house.api.event.EventDispatcher;
import net.morher.house.api.event.EventListener;

/**
 * <p>
 * Event templates are interfaces defining a set of event structures. Each method on the interface corresponds to an avent type.
 * This class helps bringing the interface to life, by providing an adapter for dispatching events and a listener for receiving
 * events.
 * </p>
 * 
 * <p>
 * Consider the following interface:
 * </p>
 * 
 * <pre>
 * public interface DoorbellListener {
 * 
 *     &#64;EventType("doorbell.ring")
 *     void onRing();
 * }
 * </pre>
 * 
 * <p>
 * An adapter can easily be made and an event sent with the following code:
 * </p>
 * 
 * <pre>
 * DoorbellListener doorbell = EventTemplate
 *         .of(DoorbellListener.class)
 *         .withSubject("Main entrance")
 *         .createAdapter(eventDispatcher);
 * 
 * doorbell.onRing();
 * </pre>
 * 
 * <p>
 * In this example, an event will be sent with the type set to "doorbell.ring" and the subject "Main entrance" through the
 * provided {@link EventDispatcher}.
 * </p>
 * 
 * <p>
 * Similarly a listener can be made using the following code:
 * </p>
 * 
 * <pre>
 * {@link EventListener} eventListener = EventTemplate
 *         .of(DoorbellListener.class)
 *         .withSubject("Main entrance")
 *         .createListener(myDoorbellListener);
 * 
 * eventListener.receiveEvent(event);
 * </pre>
 * 
 * <p>
 * Once the event is received in the example above, onRing() is called on myDoorbellListener.
 * </p>
 * 
 * <p>
 * Template interfaces should only contain methods returning void. Methods not annotated with {@link EventType} will not produce
 * any events. If two or more methods are marked with the same event type, the behavior is undefined. Default implementations
 * will not be called on the adapter created with {@link #createAdapter(EventDispatcher)}, but the method will create an event
 * if it is annotated with {@link EventType}. It is up to the implementation of the delegate whether default methods will work
 * on a listener.
 * </p>
 * 
 * <p>
 * The event-methods can have parameters as long as they are one of the following:
 * </p>
 * <ul>
 * <li>Of the type {@link Event}</li>
 * <li>Annotated with {@link EventSubject}</li>
 * <li>Annotated with {@link EventAttribute}</li>
 * </ul>
 * <p>
 * Except for the Event-parameter, all other parameters must be {@link String}.
 * </p>
 * 
 * @author Morten Hermansen
 *
 * @param <T>
 *            The template interface type
 */
public class EventTemplate<T> {
    private final TemplateContext<T> context;
    private String subject;
    private Map<String, String> attributes = new HashMap<>();

    public static <T> EventTemplate<T> of(Class<T> eventTemplate) {
        return new EventTemplate<>(eventTemplate);
    }

    private EventTemplate(Class<T> templateClass) {
        this.context = new TemplateContext<>(templateClass);
    }

    public EventTemplate<T> withSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public EventTemplate<T> withAttribute(String key, String value) {
        attributes.put(key, value);
        return this;
    }

    /**
     * Create an adapter that will implement the template interface. Method calls on the returned template will generate an
     * event that will be dispatched on the given {@link EventDispatcher}.
     * 
     * The event will be of the type set by the {@link EventType} annotation on the called method. The subject will be set to
     * the EventTemplate given subject, is set, unless it is overridden by a non-null method argument on a method parameter
     * annotated with {@link EventSubject}. In the payload, all attributes set on the EventTemplate, using
     * {@link #withAttribute(String, String)}, and non-null arguments given on method parameters annotated with
     * {@link EventAttribute}.
     * 
     * Method parameters of the type {@link Event} will be ignored by the adapter.
     * 
     * @param eventDispatcher
     * @return
     */
    @SuppressWarnings("unchecked")
    public T createAdapter(EventDispatcher eventDispatcher) {
        return (T) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class<?>[] { context.getTemplateClass() },
                new Adapter(eventDispatcher));
    }

    /**
     * Listens for events meeting the EventTemplate criteria and calls the method associated with the event type on the given
     * delegate.
     * 
     * For any action to be performed on the delegate the event type must be represented in the template interface with an
     * {@link EventType} annotation. If subject is set using {@link #withSubject(String)}, the event subject must match. Any
     * attributes set using {@link #withAttribute(String, String)} must match the corresponding attribute in the event payload.
     * 
     * @param delegate
     * @return
     */
    public EventListener createListener(T delegate) {
        return new EventHandler(delegate);
    }

    class Adapter implements InvocationHandler {
        private final EventDispatcher eventDispatcher;

        public Adapter(EventDispatcher eventDispatcher) {
            this.eventDispatcher = eventDispatcher;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getDeclaringClass().equals(Object.class)) {
                method.invoke(this, args);
            }

            MethodHandler<T> handler = context.getHandler(method);

            if (handler != null) {
                Builder eventBuilder = Event.ofType(handler.getEventType());
                eventBuilder.withSubject(subject);
                eventBuilder.withAttributes(attributes);

                // TODO Event interceptors

                handler.contributeToEvent(eventBuilder, args);

                eventDispatcher.dispatchEvent(eventBuilder.build());
                return null;
            }
            // TODO Auto-generated method stub
            return null;
        }

    }

    class EventHandler implements EventListener {
        private final T delegate;

        public EventHandler(T delegate) {
            this.delegate = delegate;
        }

        @Override
        public void receiveEvent(Event event) {
            MethodHandler<T> handler = context.getHandler(event.getType());

            if (handler != null
                    && subjectMatches(event)
                    && attributesMatches(event)) {

                handler.callMethod(delegate, event);
            }
            // TODO Auto-generated method stub

        }

        private boolean subjectMatches(Event event) {
            return subject == null
                    || subject.equals(event.getSubject());
        }

        private boolean attributesMatches(Event event) {
            for (Entry<String, String> attribute : attributes.entrySet()) {
                String payloadValue = event.getString(attribute.getKey());
                if (Objects.equals(payloadValue, attribute.getValue())) {
                    return false;
                }
            }

            return true;
        }
    }

}
