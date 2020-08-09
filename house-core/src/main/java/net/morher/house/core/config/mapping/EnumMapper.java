package net.morher.house.core.config.mapping;

import net.morher.house.api.config.ConfigurationElement;

/**
 * Mapper implementation for enum values.
 * 
 * @author Morten Hermansen
 *
 * @param <E>
 *            The enum type
 */
class EnumMapper<E extends Enum<E>> extends ConfigMapper<E> {
    private final Class<E> enumType;

    public EnumMapper(Class<E> enumType) {
        this.enumType = enumType;
    }

    private E toEnum(String value) {
        return Enum.valueOf(enumType, value.toUpperCase());
    }

    @Override
    public E getValue(ConfigurationElement element, E previousValue) {
        return element
                .asString()
                .map(this::toEnum)
                .orElse(previousValue);
    }
}