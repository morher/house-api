package net.morher.house.core.event.template;

import net.morher.house.api.event.Event;

public interface ParameterHandler {

    Object getParameterValue(Event event);

    void contributeToEvent(Event.Builder eventBuilder, Object arg);

}
