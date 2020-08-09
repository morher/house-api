package net.morher.house.core.config;

import net.morher.house.api.config.ConfigurationElement;

/**
 * An interface for {@link ConfigurationElement} that can be built and updated.
 * 
 * @author Morten Hermansen
 */
public interface EditableConfigurationElement extends ConfigurationElement {

    /**
     * Works the same way as {@link ConfigurationElement#get(String)} except that this method will create the nodes and attach
     * them to the current configuration tree.
     * 
     * @param path
     *            The path to find/create
     * @return The node
     */
    EditableConfigurationElement with(String path);

    /**
     * Set the value to the given String value.
     * 
     * @param value
     *            The value
     * @return {@code this} for chaining.
     */
    EditableConfigurationElement set(String value);

    /**
     * Shorthand for {@link #with(String)} and {@link #set(String)}.
     * 
     * @param path
     *            The path to find/create
     * @param value
     *            The value
     * @return {@code this} for chaining, not the node at the give path.
     */
    default EditableConfigurationElement set(String path, String value) {
        with(path).set(value);
        return this;
    }

    /**
     * 
     * @param value
     *            The value
     * @return {@code this} for chaining.
     */
    EditableConfigurationElement set(int value);

    /**
     * Shorthand for {@link #with(String)} and {@link #set(int)}.
     * 
     * @param path
     *            The path to find/create
     * @param value
     *            The value
     * @return {@code this} for chaining, not the node at the give path.
     */
    default EditableConfigurationElement set(String path, int value) {
        with(path).set(value);
        return this;
    }

    /**
     * 
     * @param value
     *            The value
     * @return {@code this} for chaining.
     */
    EditableConfigurationElement set(boolean value);

    /**
     * Shorthand for {@link #with(String)} and {@link #set(boolean)}.
     * 
     * @param path
     *            The path to find/create
     * @param value
     *            The value
     * @return {@code this} for chaining, not the node at the give path.
     */
    default EditableConfigurationElement set(String path, boolean value) {
        with(path).set(value);
        return this;
    }

    /**
     * Add a new child node to the this element.
     * 
     * @return The newly created attached subnode.
     */
    EditableConfigurationElement add();
}
