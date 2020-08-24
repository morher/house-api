package net.morher.house.core.event.template;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import net.morher.house.api.event.Event;

public class ParameterHandlersTest {
    @Test
    public void testEventParameter() {
        ParameterHandler handler = ParameterHandlers.getParameterHandler(param("eventParameter", 0));

        Assertions.assertNotNull(handler);
        Assertions.assertTrue(handler instanceof EventParameterHandler);
    }

    @Test
    public void testSubjectParameter() {
        ParameterHandler handler = ParameterHandlers.getParameterHandler(param("subjectParameter", 0));

        Assertions.assertNotNull(handler);
        Assertions.assertTrue(handler instanceof SubjectParameterHandler);
    }

    @Test
    public void testInvalidSubjectParameter() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> ParameterHandlers.getParameterHandler(param("invalidSubjectParameter", 0)));
    }

    @Test
    public void testAttributeParameter() {
        ParameterHandler handler = ParameterHandlers.getParameterHandler(param("attributeParameter", 0));

        Assertions.assertNotNull(handler);
        Assertions.assertTrue(handler instanceof AttributeParameterHandler);
        Assertions.assertEquals("Test attribute", ((AttributeParameterHandler) handler).attributeKey);
    }

    @Test
    public void testUnknownParameter() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> ParameterHandlers.getParameterHandler(param("unknownParameter", 0)));
    }

    private static Parameter param(String methodName, int paramIndex) {
        for (Method method : TestMethods.class.getMethods()) {
            if (methodName.equals(method.getName())) {
                return method.getParameters()[0];
            }
        }
        Assertions.fail("Invalid test: Could not find method '" + methodName + "'");
        return null;
    }

    private interface TestMethods {
        void eventParameter(Event event);

        void subjectParameter(@EventSubject String subject);

        void invalidSubjectParameter(@EventSubject int subject);

        void attributeParameter(@EventAttribute("Test attribute") String testAttribute);

        void unknownParameter(String somethingElse);
    }

}
