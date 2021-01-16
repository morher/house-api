package net.morher.house.core.event.diag;

import org.junit.jupiter.api.Test;

import net.morher.house.core.event.template.EventTemplate;

public class PingTest {

    @Test
    public void testPingTemplate() {
        EventTemplate
                .of(Ping.class);
    }
}
