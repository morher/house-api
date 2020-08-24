package net.morher.house.core.event.template;

import net.morher.house.api.event.Event;
import net.morher.house.api.event.Event.Builder;

public class SubjectParameterHandler implements ParameterHandler {

    @Override
    public Object getParameterValue(Event event) {
        return event.getSubject();
    }

    @Override
    public void contributeToEvent(Builder eventBuilder, Object arg) {
        if (arg instanceof String) {
            eventBuilder.withSubject((String) arg);
        }

    }

}
