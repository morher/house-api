package net.morher.house.core.config.mapping;

import net.morher.house.api.config.ConfigurationElement;

/**
 * Mapper implementation for integer values.
 * 
 * @author Morten Hermansen
 */
class IntegerMapper extends ConfigMapper<Integer> {

    @Override
    public Integer getValue(ConfigurationElement element, Integer previousValue) {
        return element
                .asInteger()
                .orElse(previousValue);
    }
}