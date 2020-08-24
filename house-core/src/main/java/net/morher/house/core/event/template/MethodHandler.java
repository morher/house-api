package net.morher.house.core.event.template;

import java.lang.reflect.Method;

import net.morher.house.api.event.Event;
import net.morher.house.api.event.Event.Builder;

public class MethodHandler<T> {
    private final String eventType;
    private final Method method;
    private final int parameterCount;
    private final ParameterHandler[] parameterHandlers;

    public MethodHandler(String eventType, Method method) {
        this.eventType = eventType;
        this.method = method;
        parameterCount = method.getParameterCount();
        this.parameterHandlers = getParameterHandlers();
    }

    private final ParameterHandler[] getParameterHandlers() {
        ParameterHandler[] handlers = new ParameterHandler[parameterCount];
        for (int i = 0; i < parameterCount; i++) {
            handlers[i] = ParameterHandlers.getParameterHandler(method.getParameters()[i]);
        }
        return handlers;
    }

    public String getEventType() {
        return eventType;
    }

    public Method getMethod() {
        return method;
    }

    public void callMethod(T target, Event event) {
        Object[] args = new Object[parameterHandlers.length];
        for (int i = 0; i < parameterCount; i++) {
            args[i] = parameterHandlers[i].getParameterValue(event);
        }
        try {
            method.invoke(target, args);

        } catch (Exception e) {
            throw new RuntimeException("Error occured when calling delegate", e);
        }
    }

    public void contributeToEvent(Builder eventBuilder, Object[] args) {
        for (int i = 0; i < parameterCount; i++) {
            parameterHandlers[i].contributeToEvent(eventBuilder, args[i]);
        }
    }
}
