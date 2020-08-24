package net.morher.house.core.event.template;

import net.morher.house.api.event.Event;
import net.morher.house.api.event.Event.Builder;

public class EventParameterHandler implements ParameterHandler {

    @Override
    public Object getParameterValue(Event event) {
        return event;
    }

    @Override
    public void contributeToEvent(Builder eventBuilder, Object arg) {
        // Do nothing...
    }

}
