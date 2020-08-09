package net.morher.house.core.config.mapping;

import java.util.ArrayList;
import java.util.Collection;

import net.morher.house.api.config.ConfigurationElement;

/**
 * Mapper implementation for custom configuration classes.
 * 
 * @author Morten Hermansen
 *
 * @param <T>
 *            The custom configuration type
 */
class ObjectMapper<T> extends ConfigMapper<T> {
    private final Class<T> targetClass;
    private final Collection<FieldMapper<T, ?>> handlers = new ArrayList<>();

    public ObjectMapper(Class<T> targetClass) {
        this.targetClass = targetClass;
    }

    public void addFieldMapper(FieldMapper<T, ?> mapper) {
        handlers.add(mapper);
    }

    @Override
    public T getValue(ConfigurationElement element, T previousValue) {
        T obj = previousValue != null
                ? previousValue
                : createObject();

        for (FieldMapper<T, ?> handler : handlers) {
            handler.fillField(obj, element);
        }

        return obj;
    }

    private T createObject() {
        try {
            return targetClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to create configuration object of class " + targetClass.getSimpleName(), e);
        }
    }

}
