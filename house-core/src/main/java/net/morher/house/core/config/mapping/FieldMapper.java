package net.morher.house.core.config.mapping;

import java.lang.reflect.Field;

import net.morher.house.api.config.ConfigurationElement;

/**
 * Mapper that handles a single field in a configuration class.
 * 
 * @author Morten Hermansen
 *
 * @param <T>
 *            The class the field belongs to
 * @param <V>
 *            The value type of the field
 */
class FieldMapper<T, V> {
    private final Field field;
    private final String path;
    private final ConfigMapper<V> valueMapper;

    public FieldMapper(Field field, String path, ConfigMapper<V> valueMapper) {
        this.field = field;
        this.path = path;
        this.valueMapper = valueMapper;
    }

    /**
     * Gets the corresponding configuration node, calls the associated {@link ConfigMapper} to convert the value, and sets the
     * new value to the field on the target object if the value has changed.
     * 
     * @param target
     *            The target configuration object
     * @param element
     *            The current configuration node
     */
    public void fillField(T target, ConfigurationElement element) {
        V previousValue = get(target);
        V newValue = valueMapper.getValue(element.get(path), previousValue);
        if (newValue != previousValue) {
            set(target, newValue);
        }
    }

    @SuppressWarnings("unchecked")
    protected V get(T target) {
        try {
            return (V) field.get(target);
        } catch (Exception e) {
            throw new IllegalStateException("Cannot get the value of field" + field, e);
        }
    }

    protected void set(T target, V value) {
        try {
            field.set(target, value);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new IllegalStateException("Cannot set the value of field" + field, e);
        }
    }
}
