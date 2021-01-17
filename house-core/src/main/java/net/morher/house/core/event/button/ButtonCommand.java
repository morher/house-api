package net.morher.house.core.event.button;

import net.morher.house.core.event.template.EventAttribute;
import net.morher.house.core.event.template.EventTemplate;
import net.morher.house.core.event.template.EventType;

/**
 * Common event type for commanding a button-like interface. Sending these event could control an external interface, like
 * simulating remote control pushes.
 * 
 * Different kind of button implementations have different requirements. In some cases, the push duration matters. Like when
 * controlling window blinds. However, a play-button on a media controller only needs a click. The push and release really
 * doesn't make any difference. It is thus up to the implementation of the delegate, the event receiver, to support both
 * scenarios.
 * 
 * If the sender sends {@code push} with {@code holding} set to {@code true}, it should also send {@code release} when the
 * button should be released.
 *
 * For listening to button actions, use {@link ButtonListener}.
 * 
 * @author Morten Hermansen
 * 
 * @see EventTemplate
 * @see ButtonListener
 */
public interface ButtonCommand {
    public static final String EVENT_BUTTON_PRESS = "button.control.press";
    public static final String EVENT_BUTTON_RELEASE = "button.control.release";
    public static final String ATTRIBUTE_HOLDING = "button.holding";

    /**
     * Request the push of a button. If holding is set to false, the button should be released immediately.
     * 
     * @param holding
     * 
     */
    @EventType(EVENT_BUTTON_PRESS)
    void press(@EventAttribute(ATTRIBUTE_HOLDING) boolean holding);

    @EventType(EVENT_BUTTON_RELEASE)
    void release();
}
