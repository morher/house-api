package net.morher.house.core.convert;

/**
 * Interface for string value converters. Useful for converting event attributes to and from a more usable type.
 * 
 * @author Morten Hermansen
 *
 * @param <T>
 *            The class the converter converts to and from.
 */
public interface Converter<T> {
    /**
     * Convert the value to a String
     * 
     * @param value
     *            The value to be converted
     * @return A string representation of the value
     */
    default String toString(T value) {
        return value != null
                ? value.toString()
                : null;

    }

    /**
     * Convert the String value.
     * 
     * @param str
     *            The String to convert
     * @return The converted value
     */
    T fromString(String str);
}
