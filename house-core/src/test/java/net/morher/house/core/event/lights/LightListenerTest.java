package net.morher.house.core.event.lights;

import org.junit.jupiter.api.Test;

import net.morher.house.core.event.template.EventTemplate;

public class LightListenerTest {

    @Test
    public void testLightListenerTemplate() {
        EventTemplate
                .of(LightListener.class);
    }
}
