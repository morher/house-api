package net.morher.house.core.convert;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Factory for {@link Converter}. Supports String, boolean, Boolean, int, Integer, long, Long, BigInteger, float, Float, double,
 * Double, BigDecimal and enums.
 * 
 * @author Morten Hermansen
 */
public abstract class Converters {
    private static Map<Type, Converter<?>> converterCache = initializeCache();

    private static Map<Type, Converter<?>> initializeCache() {
        Map<Type, Converter<?>> cache = new HashMap<>();
        cache.put(String.class, (str) -> str);

        cache.put(boolean.class, new SimpleConverter<Boolean>(Boolean::valueOf, false));
        cache.put(Boolean.class, new SimpleConverter<Boolean>(Boolean::valueOf, null));

        cache.put(int.class, new SimpleConverter<Integer>(Integer::valueOf, 0));
        cache.put(Integer.class, new SimpleConverter<Integer>(Integer::valueOf, null));
        cache.put(long.class, new SimpleConverter<Long>(Long::valueOf, 0l));
        cache.put(Long.class, new SimpleConverter<Long>(Long::valueOf, null));
        cache.put(BigInteger.class, new SimpleConverter<BigInteger>(BigInteger::new, null));

        cache.put(float.class, new SimpleConverter<Float>(Float::valueOf, 0f));
        cache.put(Float.class, new SimpleConverter<Float>(Float::valueOf, null));
        cache.put(double.class, new SimpleConverter<Double>(Double::valueOf, 0.0));
        cache.put(Double.class, new SimpleConverter<Double>(Double::valueOf, null));
        cache.put(BigDecimal.class, new SimpleConverter<BigDecimal>(BigDecimal::new, null));

        return cache;
    }

    @SuppressWarnings("unchecked")
    public static <T> Converter<T> find(Type type) {
        Converter<T> converter = (Converter<T>) converterCache.get(type);
        // Enum
        if (type instanceof Class && Enum.class.isAssignableFrom((Class<?>) type)) {
            converter = createEnumConverter((Class) type);
        }

        if (converter == null) {
            throw new IllegalArgumentException("No converter found for type " + type);
        }
        return converter;
    }

    private static <E extends Enum<E>> Converter<E> createEnumConverter(Class<E> type) {
        EnumConverter<E> converter = new EnumConverter<E>(type);
        register(type, converter);
        return converter;
    }

    private static synchronized void register(Type type, Converter<?> converter) {
        Map<Type, Converter<?>> newCache = new HashMap<>(converterCache);
        newCache.put(type, converter);
        converterCache = newCache;
    }

    private static class SimpleConverter<T> implements Converter<T> {
        private final Function<String, T> fromStringConverter;
        private final T nullValue;

        public SimpleConverter(Function<String, T> fromStringConverter, T nullValue) {
            this.fromStringConverter = fromStringConverter;
            this.nullValue = nullValue;
        }

        @Override
        public T fromString(String str) {
            if (str != null) {
                return fromStringConverter.apply(str);
            }
            return nullValue;
        }
    }
}
