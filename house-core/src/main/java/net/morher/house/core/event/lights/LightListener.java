package net.morher.house.core.event.lights;

import net.morher.house.core.event.StatusReporting;
import net.morher.house.core.event.template.EventType;

public interface LightListener {
    // Event type
    public static final String STATE_REPORT = "light.state";

    /**
     * Receive the current state of the light. These events can be sent from a light implementor at any moment. They should
     * however be sent whenever the state is changed or when a status report is requested through
     * {@link StatusReporting#pollState()}.
     * 
     * @param state
     *            The current reported light state
     */
    @EventType(STATE_REPORT)
    void onStateReport(LightState state);
}
