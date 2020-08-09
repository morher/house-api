package net.morher.house.core.config.mapping;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Context for parsing configuration classes and generating a {@link ConfigMapper}.
 * 
 * @author Morten Hermansen
 */
class ClassParsingContext {
    private final Map<Class<?>, ObjectMapper<?>> objectMappers = new HashMap<>();

    public <T> ConfigMapper<T> getConfigMapper(Class<T> targetClass) {
        return getValueMapper(targetClass, null);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private <T> ConfigMapper<T> getValueMapper(Class<T> targetClass, ParameterizedType parameterizedType) {
        if (targetClass.isAssignableFrom(String.class)) {
            return (ConfigMapper<T>) new StringMapper();

        } else if (targetClass.isAssignableFrom(Boolean.class)
                || targetClass.isAssignableFrom(boolean.class)) {
            return (ConfigMapper<T>) new BooleanMapper();

        } else if (targetClass.isAssignableFrom(int.class)
                || targetClass.isAssignableFrom(Integer.class)) {
            return (ConfigMapper<T>) new IntegerMapper();

        } else if (Enum.class.isAssignableFrom(targetClass)) {
            return new EnumMapper(targetClass);

        } else if (targetClass.equals(Optional.class)) {
            if (parameterizedType == null) {
                throw new IllegalArgumentException("Cannot find type for Optional");
            }
            return new OptionalMapper(getSubValueMapper(parameterizedType.getActualTypeArguments()[0]));

        } else if (targetClass.equals(Collection.class)
                || targetClass.equals(List.class)) {
            return new CollectionMapper(getSubValueMapper(parameterizedType.getActualTypeArguments()[0]));

        }
        return createObjectMapper(targetClass);
    }

    private <T> ConfigMapper<T> createObjectMapper(Class<T> targetClass) {
        @SuppressWarnings("unchecked")
        ObjectMapper<T> mapper = (ObjectMapper<T>) objectMappers.get(targetClass);
        if (mapper == null) {
            mapper = new ObjectMapper<T>(targetClass);
            objectMappers.put(targetClass, mapper);

            addFieldMappers(targetClass, mapper);
        }
        return mapper;
    }

    private <T> void addFieldMappers(Class<T> targetClass, ObjectMapper<T> mapper) {
        for (Field field : targetClass.getDeclaredFields()) {
            if (isMappedField(field)) {
                field.setAccessible(true);
                parseField(mapper, field);
            }
        }
    }

    private <T> void parseField(ObjectMapper<T> mapper, Field field) {
        String path = field.getName();
        ConfigMapper<Object> valueMapper = (ConfigMapper<Object>) getValueMapper(field.getType(), parameterizedType(field.getGenericType()));
        mapper.addFieldMapper(new FieldMapper<T, Object>(field, path, valueMapper));
    }

    private boolean isMappedField(Field field) {
        int modifiers = field.getModifiers();
        return !Modifier.isStatic(modifiers)
                && !Modifier.isFinal(modifiers);
    }

    private ConfigMapper<?> getSubValueMapper(Type targetType) {
        if (targetType instanceof Class) {
            return getValueMapper((Class<?>) targetType, null);
        }
        if (targetType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) targetType;
            return getValueMapper((Class<?>) parameterizedType.getRawType(), parameterizedType);
        }
        throw new IllegalArgumentException("Unsupported Type type");
    }

    private static ParameterizedType parameterizedType(Type type) {
        return (type instanceof ParameterizedType)
                ? (ParameterizedType) type
                : null;
    }
}
