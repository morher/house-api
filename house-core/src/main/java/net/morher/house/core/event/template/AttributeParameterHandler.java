package net.morher.house.core.event.template;

import net.morher.house.api.event.Event;
import net.morher.house.api.event.Event.Builder;
import net.morher.house.core.convert.Converter;

public class AttributeParameterHandler implements ParameterHandler {
    final String attributeKey;
    final Converter<Object> converter;

    @SuppressWarnings("unchecked")
    public AttributeParameterHandler(String attributeKey, Converter<?> converter) {
        this.attributeKey = attributeKey;
        this.converter = (Converter<Object>) converter;
    }

    @Override
    public Object getParameterValue(Event event) {
        return converter.fromString(event.getString(attributeKey));
    }

    @Override
    public void contributeToEvent(Builder eventBuilder, Object arg) {
        if (arg instanceof String) {
            eventBuilder.withAttribute(attributeKey, converter.toString(arg));
        }
    }

}
