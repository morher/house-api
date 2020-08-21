package net.morher.house.core.convert;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EnumConverterTest {
    private final EnumConverter<TestEnum> converter = new EnumConverter<>(TestEnum.class);

    @Test
    public void testToString() {
        Assertions.assertNull(converter.toString(null));
        Assertions.assertEquals("Value1", converter.toString(TestEnum.VALUE1));
        Assertions.assertEquals("Value with underscore", converter.toString(TestEnum.VALUE_WITH_UNDERSCORE));
        Assertions.assertEquals("Value in lowercase", converter.toString(TestEnum.value_in_lowercase));
    }

    @Test
    public void testFromString() {
        Assertions.assertNull(converter.toString(null));

        Assertions.assertEquals(TestEnum.VALUE1, converter.fromString("Value1"));
        Assertions.assertEquals(TestEnum.VALUE1, converter.fromString("value1"));
        Assertions.assertEquals(TestEnum.VALUE1, converter.fromString("VALUE1"));

        Assertions.assertEquals(TestEnum.VALUE_WITH_UNDERSCORE, converter.fromString("Value with underscore"));
        Assertions.assertEquals(TestEnum.VALUE_WITH_UNDERSCORE, converter.fromString("Value_with_underscore"));

        Assertions.assertEquals(TestEnum.value_in_lowercase, converter.fromString("Value in lowercase"));
        Assertions.assertEquals(TestEnum.value_in_lowercase, converter.fromString("VALUE IN LOWERCASE"));
    }

    @Test
    public void testFromStringInvalidValue() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> converter.fromString("invalid"));
    }

    private static enum TestEnum {
        VALUE1, VALUE_WITH_UNDERSCORE, value_in_lowercase;
    }
}
