package net.morher.house.core.config.mapping;

import java.util.ArrayList;
import java.util.Collection;

import net.morher.house.api.config.ConfigurationElement;

/**
 * Maoper implementation for collections/lists.
 * 
 * @author Morten Hermansen
 *
 * @param <T>
 *            The generic type for the collection/list
 */
class CollectionMapper<T> extends ConfigMapper<Collection<T>> {
    private final ConfigMapper<T> mapper;

    public CollectionMapper(ConfigMapper<T> mapper) {
        this.mapper = mapper;
    }

    @Override
    public Collection<T> getValue(ConfigurationElement listElement, Collection<T> previousValue) {
        Collection<T> list = previousValue != null
                ? new ArrayList<>(previousValue)
                : new ArrayList<>();

        for (ConfigurationElement element : listElement) {
            list.add(mapper.getValue(element, null));
        }
        return list;
    }
}