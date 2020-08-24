package net.morher.house.core.event.template;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import net.morher.house.api.event.Event;
import net.morher.house.api.event.EventDispatcher;

public class EventTemplateTest {

    @Test
    public void testCreateAdapter() {
        EventDispatcher eventDispatcher = Mockito.mock(EventDispatcher.class);

        EventTemplate
                .of(TestTemplate.class)
                .createAdapter(eventDispatcher)
                .onSomethingHappened();

        Event event = getDispatchedEvent(eventDispatcher);

        assertNotNull(event);
        assertEquals("something.happened", event.getType());
    }

    @Test
    public void testCreateListener() {
        TestTemplate delegate = Mockito.mock(TestTemplate.class);

        Event event = Event
                .ofType("something.happened")
                .build();

        EventTemplate
                .of(TestTemplate.class)
                .createListener(delegate)
                .receiveEvent(event);

        Mockito
                .verify(delegate, Mockito.times(1))
                .onSomethingHappened();
    }

    private static Event getDispatchedEvent(EventDispatcher eventDispatcher) {
        ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
        Mockito
                .verify(eventDispatcher, Mockito.times(1))
                .dispatchEvent(eventCaptor.capture());
        Event event = eventCaptor.getValue();
        return event;
    }

    public static interface TestTemplate {

        @EventType("something.happened")
        void onSomethingHappened();

        @EventType("")
        void onSomethingElseHappened(@EventSubject String subject);
    }
}
