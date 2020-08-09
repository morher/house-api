package net.morher.house.core.config.mapping;

import net.morher.house.api.config.ConfigurationElement;

/**
 * Mapper implementation for String values.
 * 
 * @author Morten Hermansen
 */
class StringMapper extends ConfigMapper<String> {

    @Override
    public String getValue(ConfigurationElement element, String previousValue) {
        return element
                .asString()
                .orElse(previousValue);
    }
}