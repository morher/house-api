package net.morher.house.core.event.lights;

import net.morher.house.core.event.StatusReporting;
import net.morher.house.core.event.template.EventAttribute;
import net.morher.house.core.event.template.EventType;

/**
 * Common event types for controlling lights.
 * 
 * @author Morten Hermansen
 */
public interface LightCommand extends StatusReporting {
    // Event types
    public static final String SET_DIM_LEVEL = "lights.set.dim";
    public static final String SET_POWER = "lights.set.power";

    /**
     * Set a dimming source to a given level. For light sources that does not support dimming, the implementing receiver should
     * set a threshold level for what will turn it on. A dim level of 0 is the same as calling {@link #setPower(boolean)} with
     * the value {@code false}. The maximum dim-level is 1. Using negative values or values over 1 results in undefined
     * behavior, although delegates are recommended to handle these values gracefully by rejecting them.
     * 
     * As this event type just represents a request to set the dim level, the implementing delegate can choose to ignore the
     * request,or limit the allowed dimming range. The implementation will naturally have to adjust the level to the resolution
     * it is able to handle.
     * 
     * Not all dimmers support changing the speed of the fade function. In these cases the {@code dimSpeed} can be ignored.
     * 
     * @param level
     *            The requested dim-level; 0, 1 or any value in between.
     * 
     * @param dimSpeed
     *            The speed of the dimming, given in the number of seconds a fade between dim level 0 and 1. If null, the
     *            default speed is used.
     * 
     */
    @EventType(SET_DIM_LEVEL)
    void setDimLevel(
            @EventAttribute(LightState.DIM_LEVEL) float level,
            @EventAttribute(LightState.DIM_SPEED) Float dimSpeed);

    /**
     * Turns a light on or off. If dimming is supported, what dim-level would be used for {@code on} is implementation specific.
     * Typically it would be the dim-level used before turning off last time.
     * 
     * @param power
     */
    @EventType(SET_DIM_LEVEL)
    void setPower(@EventAttribute(LightState.POWER) LightPower power);
}
