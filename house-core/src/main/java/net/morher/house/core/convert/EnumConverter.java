package net.morher.house.core.convert;

import java.util.EnumSet;

public class EnumConverter<E extends Enum<E>> implements Converter<E> {
    private final Class<E> enumType;

    public EnumConverter(Class<E> enumType) {
        this.enumType = enumType;
    }

    @Override
    public String toString(E value) {
        if (value != null) {
            String name = value.name();
            return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase().replace('_', ' ');
        }
        return null;
    }

    @Override
    public E fromString(String str) {
        if (str != null) {
            String name = str.replace(' ', '_');

            for (E value : EnumSet.allOf(enumType)) {
                if (value.name().equalsIgnoreCase(name)) {
                    return value;
                }
            }
            throw new IllegalArgumentException("Value '" + str + "' is invalid for enum '" + enumType + "'");
        }
        return null;
    }
}
