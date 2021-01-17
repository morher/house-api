package net.morher.house.core.event.lights;

import net.morher.house.core.event.template.CompoundParameter;
import net.morher.house.core.event.template.EventAttribute;

@CompoundParameter
public class LightState {
    // Attributes
    public static final String DIM_LEVEL = "dimLevel";
    public static final String DIM_SPEED = "dimSpeed";
    public static final String POWER = "power";

    @EventAttribute(DIM_LEVEL)
    public Float dimLevel;

    @EventAttribute(DIM_SPEED)
    public Float domSpeed;

    @EventAttribute(POWER)
    public LightPower power;
}
