package net.morher.house.core.config.mapping;

import java.util.Optional;

import net.morher.house.api.config.ConfigurationElement;

/**
 * Mapper implementation for Optional values.
 * 
 * @author Morten Hermansen
 *
 * @param <T>
 *            The generic type for the Optional
 */
class OptionalMapper<T> extends ConfigMapper<Optional<T>> {
    private final ConfigMapper<T> mapper;

    public OptionalMapper(ConfigMapper<T> mapper) {
        this.mapper = mapper;
    }

    @Override
    public Optional<T> getValue(ConfigurationElement element, Optional<T> previousValue) {
        T unpackedPreviousValue = previousValue != null
                ? previousValue.orElse(null)
                : null;
        return Optional.ofNullable(mapper.getValue(element, unpackedPreviousValue));
    }
}