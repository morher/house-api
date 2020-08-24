package net.morher.house.core.event.button;

import org.junit.jupiter.api.Test;

import net.morher.house.core.event.template.EventTemplate;

public class ButtonCommandTest {

    @Test
    public void testButtonCommandTemplate() {
        // Parsing the interface to verify the template.
        EventTemplate
                .of(ButtonCommand.class);
    }
}
