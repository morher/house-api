package net.morher.house.core.event.template;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

class TemplateContext<T> {
    private final Class<T> templateClass;
    private final Map<Method, MethodHandler<T>> methods = new HashMap<>();
    private final Map<String, MethodHandler<T>> eventTypes = new HashMap<>();

    public TemplateContext(Class<T> templateClass) {
        this.templateClass = templateClass;

        for (Method method : templateClass.getMethods()) {
            EventType eventTypeAnnotation = method.getAnnotation(EventType.class);
            if (eventTypeAnnotation != null) {
                add(new MethodHandler<T>(eventTypeAnnotation.value(), method));
                System.out.println(eventTypeAnnotation.value());
            }
        }
    }

    private final void add(MethodHandler<T> handler) {
        methods.put(handler.getMethod(), handler);
        eventTypes.put(handler.getEventType(), handler);
    }

    public Class<T> getTemplateClass() {
        return templateClass;
    }

    public MethodHandler<T> getHandler(Method method) {
        return methods.get(method);
    }

    public MethodHandler<T> getHandler(String eventType) {
        return eventTypes.get(eventType);
    }
}