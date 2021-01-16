package net.morher.house.core.event.diag;

import org.junit.jupiter.api.Test;

import net.morher.house.core.event.template.EventTemplate;

public class HeartbeatTest {

    @Test
    public void testHeartbeatTemplate() {
        EventTemplate
                .of(Heartbeat.class);
    }
}
