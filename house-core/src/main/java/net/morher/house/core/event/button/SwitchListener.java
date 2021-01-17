package net.morher.house.core.event.button;

import net.morher.house.core.event.StatusReporting;
import net.morher.house.core.event.template.EventAttribute;
import net.morher.house.core.event.template.EventTemplate;
import net.morher.house.core.event.template.EventType;

/**
 * Event template for receiving status reports from switches. A switch can have two or more states identified by an integer. The
 * simplest implementation would be a light switch, with it two stable states. While it could be claimed that a spring loaded
 * push-button is just a mono-stable switch, it's up to the implementation to decide what template to use. While a switch could
 * support a momentary state, this interface is primarily intended for switches that keep their state for more than a couple
 * seconds. The state can be a negative integer. This would make sense for a on-off-on switch, where 0 would represent off, and
 * 1 would represent one side and -1 would represent the other.
 * 
 * On a rotary selector, the values could go from 1 to 12. There are no requirement that 0 or any other value has a specific
 * meaning.
 * 
 * @author Morten Hermansen
 * 
 * @see EventTemplate
 * @see ButtonListener
 * @see StatusReporting
 */
public interface SwitchListener {
    // Event type
    public static final String STATE_REPORT = "switch.state";

    /**
     * Receive the current state of the switch. These events can be sent from a switch monitor implementation at any moment.
     * They should however be sent whenever the state is changed or when a status report is requested through
     * {@link StatusReporting#pollState()}.
     * 
     * @param switchState
     *            The current reported switch state
     */
    @EventType(STATE_REPORT)
    void onStateReport(@EventAttribute(STATE_REPORT) int switchState);

}
