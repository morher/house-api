package net.morher.house.core.event.template;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;

import net.morher.house.api.event.Event;
import net.morher.house.api.event.Event.Builder;

public class CompositeParameterHandler<T> implements ParameterHandler {
    private final Class<T> compositeType;
    private final Constructor<T> constructor;
    private final Collection<FieldHandler> fieldHandlers = new ArrayList<>();

    public CompositeParameterHandler(Class<T> compositeType) {
        this.compositeType = compositeType;
        try {
            constructor = compositeType.getDeclaredConstructor();
            constructor.setAccessible(true);

        } catch (NoSuchMethodException | SecurityException e) {
            throw new IllegalArgumentException("Type '" + compositeType + "' does not have a non-parameter constructor", e);
        }
    }

    public void addFieldHandler(Field field, ParameterHandler handler) {
        if (!field.getDeclaringClass().isAssignableFrom(compositeType)) {
            throw new IllegalArgumentException("The field '" + field + "' is not part of '" + compositeType + "'");
        }
        fieldHandlers.add(new FieldHandler(field, handler));
    }

    @Override
    public Object getParameterValue(Event event) {
        T instance = createInstance();
        for (FieldHandler fieldHandler : fieldHandlers) {
            fieldHandler.setFieldValue(instance, event);
        }
        return instance;
    }

    private T createInstance() {
        try {
            return constructor.newInstance();

        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new IllegalStateException("Failed to create instance of composite parameter '" + compositeType.getSimpleName() + "'", e);
        }
    }

    @Override
    public void contributeToEvent(Builder eventBuilder, Object arg) {
        if (compositeType.isInstance(arg)) {
            @SuppressWarnings("unchecked")
            T target = (T) arg;
            for (FieldHandler fieldHandler : fieldHandlers) {
                fieldHandler.contributeToEvent(target, eventBuilder, arg);
            }
        }
    }

    private class FieldHandler {
        private final Field field;
        private final ParameterHandler handler;

        public FieldHandler(Field field, ParameterHandler handler) {
            field.setAccessible(true);
            this.field = field;
            this.handler = handler;
        }

        public void setFieldValue(T target, Event event) {
            Object value = handler.getParameterValue(event);
            if (value != null) {
                try {
                    field.set(target, value);

                } catch (IllegalArgumentException | IllegalAccessException e) {
                    throw new IllegalStateException("Failed to set the value of '" + field.getName() + "' in the composite parameter '" + compositeType + "'", e);
                }
            }
        }

        public void contributeToEvent(T target, Builder eventBuilder, Object arg) {
            try {
                Object value = field.get(target);
                handler.contributeToEvent(eventBuilder, value);

            } catch (IllegalArgumentException | IllegalAccessException e) {
                throw new IllegalStateException("Failed to set the value of '" + field.getName() + "' in the composite parameter '" + compositeType + "'", e);
            }
        }
    }
}
