package net.morher.house.core.convert;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.jupiter.api.Test;

public class ConvertersTest {

    @Test
    public void testStringConverter() {
        String value = "Test String";
        assertSame(value, Converters.find(String.class).fromString(value));
        assertSame(value, Converters.find(String.class).toString(value));
    }

    @Test
    public void testBooleanConverter() {
        assertEquals(false, Converters.find(boolean.class).fromString(null));
        assertEquals(true, Converters.find(boolean.class).fromString("true"));
        assertEquals("true", Converters.find(boolean.class).toString(true));

        assertEquals(null, Converters.find(Boolean.class).fromString(null));
        assertEquals(true, Converters.find(Boolean.class).fromString("true"));
        assertEquals(null, Converters.find(Boolean.class).toString(null));
        assertEquals("true", Converters.find(Boolean.class).toString(Boolean.TRUE));
    }

    @Test
    public void testIntegerConverter() {
        assertEquals(0, Converters.find(int.class).fromString(null));
        assertEquals(1, Converters.find(int.class).fromString("1"));
        assertEquals("2", Converters.find(int.class).toString(2));

        assertEquals(null, Converters.find(Integer.class).fromString(null));
        assertEquals(1, Converters.find(Integer.class).fromString("1"));
        assertEquals(null, Converters.find(Integer.class).toString(null));
        assertEquals("2", Converters.find(Integer.class).toString(2));
    }

    @Test
    public void testLongConverter() {
        assertEquals(0l, Converters.find(long.class).fromString(null));
        assertEquals(1l, Converters.find(long.class).fromString("1"));
        assertEquals("2", Converters.find(long.class).toString(2l));

        assertEquals(null, Converters.find(Long.class).fromString(null));
        assertEquals(Long.valueOf(1), Converters.find(Long.class).fromString("1"));
        assertEquals(null, Converters.find(Long.class).toString(null));
        assertEquals("2", Converters.find(Long.class).toString(Long.valueOf(2l)));
    }

    @Test
    public void testBigIntegerConverter() {
        assertEquals(null, Converters.find(BigInteger.class).fromString(null));
        assertEquals(BigInteger.valueOf(1), Converters.find(BigInteger.class).fromString("1"));
        assertEquals(null, Converters.find(BigInteger.class).toString(null));
        assertEquals("2", Converters.find(BigInteger.class).toString(BigInteger.valueOf(2)));
    }

    @Test
    public void testFloatConverter() {
        assertEquals(0f, Converters.find(float.class).fromString(null));
        assertEquals(1.1f, Converters.find(float.class).fromString("1.1"));
        assertEquals("2.1", Converters.find(float.class).toString(2.1f));

        assertEquals(null, Converters.find(Float.class).fromString(null));
        assertEquals(Float.valueOf(1.1f), Converters.find(Float.class).fromString("1.1"));
        assertEquals(null, Converters.find(Float.class).toString(null));
        assertEquals("2.1", Converters.find(Float.class).toString(Float.valueOf(2.1f)));
    }

    @Test
    public void testDoubleConverter() {
        assertEquals(0d, Converters.find(double.class).fromString(null));
        assertEquals(1.1d, Converters.find(double.class).fromString("1.1"));
        assertEquals("2.1", Converters.find(double.class).toString(2.1d));

        assertEquals(null, Converters.find(Double.class).fromString(null));
        assertEquals(Double.valueOf(1.1d), Converters.find(Double.class).fromString("1.1"));
        assertEquals(null, Converters.find(Double.class).toString(null));
        assertEquals("2.1", Converters.find(Double.class).toString(Double.valueOf(2.1d)));
    }

    @Test
    public void testBigDecimalConverter() {
        assertEquals(null, Converters.find(BigDecimal.class).fromString(null));
        assertEquals(BigDecimal.valueOf(1.1d), Converters.find(BigDecimal.class).fromString("1.1"));
        assertEquals(null, Converters.find(BigDecimal.class).toString(null));
        assertEquals("2.1", Converters.find(BigDecimal.class).toString(BigDecimal.valueOf(2.1d)));
    }

    @Test
    public void testEnumConverter() {
        assertEquals(null, Converters.find(TestEnum.class).fromString(null));
        assertEquals(TestEnum.VALUE, Converters.find(TestEnum.class).fromString("Value"));
        assertEquals(null, Converters.find(TestEnum.class).toString(null));
        assertEquals("Value", Converters.find(TestEnum.class).toString(TestEnum.VALUE));
    }

    @Test
    public void testUnknownTypeThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> Converters.find(UnknownType.class));
    }

    private static enum TestEnum {
        VALUE;
    }

    private static class UnknownType {
    }
}
