package net.morher.house.api.config;

import java.util.Optional;

/**
 * A ConfigurationElement can be a node on a tree of configuration elements or a leaf, containing a value. As a leaf its value
 * can be parsed either as a String, an Integer or a boolean value. It can also represent a list of configuration elements.
 * 
 * @author Morten Hermansen
 */
public interface ConfigurationElement extends Iterable<ConfigurationElement> {

    /**
     * Get the configuration element with the given path. A {@link ConfigurationElement} is always returned even if the path is
     * not present in the configuration. Each node in the path is separated by a period. For example "node.anotherNode".
     * 
     * @param path
     *            The node path to get
     * 
     * @return The node if present, otherwise an empty node.
     */
    ConfigurationElement get(String path);

    /**
     * Get the current element value as a {@link String} if any value is present.
     * 
     * @return An {@link Optional} that with the {@link String} value if present, otherwise an empty {@link Optional}.
     */
    Optional<String> asString();

    /**
     * Get the current element value as a {@link Integer} if any value is present.
     * 
     * @return An {@link Optional} that with the {@link Integer} value if present, otherwise an empty {@link Optional}.
     */
    Optional<Integer> asInteger();

    /**
     * Get the current element value as a {@link Boolean} if any value is present.
     * 
     * @return An {@link Optional} that with the {@link Boolean} value if present, otherwise an empty {@link Optional}.
     */
    Optional<Boolean> asBoolean();

    /**
     * Shortcut for {@link #get(String) and {@link #asString()} that will return {@code null} if the value is absent.
     * 
     * @param path
     * @return The {@link String} value of the given path is present, otherwise {@code null}.
     */
    default String getString(String path) {
        return get(path).asString().orElse(null);
    }

}
