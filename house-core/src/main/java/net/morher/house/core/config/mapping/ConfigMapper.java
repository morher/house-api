package net.morher.house.core.config.mapping;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import net.morher.house.api.config.ConfigurationElement;

/**
 * <p>
 * Utility for parsing {@link ConfigurationElement}s to the given class tree.
 * 
 * <p>
 * Usage:
 * </p>
 * 
 * <pre>
 * public class MyModuleConfig {
 *     private String buttonName;
 *     private boolean normallyOn = true;
 *     private int pushDurationMs = 50;
 * 
 *     // ... get...()
 * }
 * 
 * MyModuleConfig moduleConfig = ConfigMapper
 *         .of(MyModuleConfig.class)
 *         .readConfig(configurationElement.get("MyModule"));
 * </pre>
 * 
 * <p>
 * The mapper supports {@link String}, {@link Boolean}, {@code boolean}, {@link Integer}, {@code int}, enums, {@link Optional}
 * of any of the preceding, {@link Collection} and {@link List} as well as any class with a public default constructor with
 * fields of any of these types. Default values can be set by initiating the field with the selected value. Collections and
 * lists will be initialized to an empty list if predefined as null event if the configuration path is not present. Optional
 * fields will be initialized to {@link Optional#empty()} if null. Other classes will be created if not predefined, even if the
 * configuration path is not present.
 * </p>
 * 
 * @author Morten Hermansen
 *
 * @param <T>
 *            The class the configuration will be mapped into
 */
public abstract class ConfigMapper<T> {

    /**
     * Creates a configuration mapper for the given class.
     * 
     * @param <T>
     *            The class the configuration will be mapped into
     * @param targetClass
     *            The class the configuration will be mapped into
     * @return A configuration mapper for the given class
     */
    public static <T> ConfigMapper<T> of(Class<T> targetClass) {
        return new ClassParsingContext()
                .getConfigMapper(targetClass);
    }

    /**
     * Read a configuration tree into the target configuration class
     * 
     * @param element
     *            The configuration tree to be read
     * @return The configuration object
     */
    public T readConfig(ConfigurationElement element) {
        return getValue(element, null);
    }

    /**
     * Determine the new value. Implementation varies dependent on the type.
     * 
     * @param element
     * @param previousValue
     * @return
     */
    protected abstract T getValue(ConfigurationElement element, T previousValue);
}
