package net.morher.house.api.config;

/**
 * The ConfigurationProvider loads and monitors the nodes configuration.
 * 
 * @author Morten Hermansen
 */
public interface ConfigurationProvider {

    /**
     * Loads and returns the node configuration. The source is implementation dependent. It could be reading a file or
     * contacting an external configuration service.
     * 
     * @return The configuration
     */
    ConfigurationElement loadConfiguration();

    /**
     * A {@code ConfigurationProvider} can optionally support update monitoring. Returning true tells the node container that
     * the configuration might have changed.
     * 
     * @return Wether the configuration might have been updated.
     */
    default boolean isConfigurationUpdated() {
        return false;
    }
}
