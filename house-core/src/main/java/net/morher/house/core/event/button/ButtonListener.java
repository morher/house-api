package net.morher.house.core.event.button;

import net.morher.house.core.event.StatusReporting;
import net.morher.house.core.event.template.EventAttribute;
import net.morher.house.core.event.template.EventTemplate;
import net.morher.house.core.event.template.EventType;

/**
 * Common event type for received button activities. The events could for example originate from interactions with a physical
 * button through an IO interface, or by touch on a touch-screen.
 *
 * Buttons come in all shapes. In some cases when it is pushed and when it is released makes a difference. Some buttons just
 * want to be clicked. Sending a {@code release} event is not required, except if {@code press} was sent with the
 * {@code holding} attribute set to {@code true}.
 * 
 * For controlling buttons, use {@link ButtonCommand}.
 * 
 * @author Morten Hermansen
 * 
 * @see {@link EventTemplate}, {@link ButtonCommand}, {@link StatusReporting}
 */
public interface ButtonListener {
    public static final String EVENT_BUTTON_ONPRESS = "button.input.press";
    public static final String EVENT_BUTTON_ONRELEASE = "button.input.release";
    public static final String ATTRIBUTE_HOLDING = "button.holding";

    @EventType(EVENT_BUTTON_ONPRESS)
    void onPress(@EventAttribute(ATTRIBUTE_HOLDING) boolean isHolding);

    @EventType(EVENT_BUTTON_ONRELEASE)
    void onRelease();
}
