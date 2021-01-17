package net.morher.house.core.event.lights;

import org.junit.jupiter.api.Test;

import net.morher.house.core.event.template.EventTemplate;

public class LightCommandTest {

    @Test
    public void testLightCommandTemplate() {
        EventTemplate
                .of(LightCommand.class);
    }
}
