package net.morher.house.core.config.mapping;

import net.morher.house.api.config.ConfigurationElement;

/**
 * Mapper implementation for Boolean values.
 * 
 * @author Morten Hermansen
 */
class BooleanMapper extends ConfigMapper<Boolean> {

    @Override
    public Boolean getValue(ConfigurationElement element, Boolean previousValue) {
        return element
                .asBoolean()
                .orElse(previousValue);
    }
}