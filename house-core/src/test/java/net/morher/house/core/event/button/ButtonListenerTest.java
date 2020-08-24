package net.morher.house.core.event.button;

import org.junit.jupiter.api.Test;

import net.morher.house.core.event.template.EventTemplate;

public class ButtonListenerTest {

    @Test
    public void testButtonListenerTemplate() {
        // Parsing the interface to verify the template.
        EventTemplate
                .of(ButtonListener.class);
    }
}
