package net.morher.house.core.event.template;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;

import net.morher.house.api.event.Event;
import net.morher.house.core.convert.Converters;

public class ParameterHandlers {

    public static ParameterHandler getParameterHandler(Parameter parameter) {
        return getParameterHandler(parameter, parameter.getParameterizedType());
    }

    public static ParameterHandler getParameterHandler(Field field) {
        return getParameterHandler(field, field.getGenericType());
    }

    private static ParameterHandler getParameterHandler(AnnotatedElement parameter, Type type) {
        if (type instanceof Class && Event.class.isAssignableFrom((Class<?>) type)) {
            return new EventParameterHandler();
        }

        EventSubject subjectAnnotation = parameter.getAnnotation(EventSubject.class);
        if (subjectAnnotation != null) {
            return createSubjectParameterHandler(type);
        }

        EventAttribute attributeAnnotation = parameter.getAnnotation(EventAttribute.class);
        if (attributeAnnotation != null) {
            return createAttributeParameterHandler(type, attributeAnnotation);
        }
        CompoundParameter compoundParameterAnnotation = findAnnotation(CompoundParameter.class, parameter, getType(parameter));
        if (compoundParameterAnnotation != null && type instanceof Class) {
            return createCompositeParameterHandler((Class<?>) type);
        }
        throw new IllegalArgumentException("No parameter handler found for: " + parameter);
    }

    private static <A extends Annotation> A findAnnotation(Class<A> annotationClass, AnnotatedElement... annotatedElements) {
        if (annotatedElements != null) {
            for (AnnotatedElement annotatedElement : annotatedElements) {
                A annotation = annotatedElement.getAnnotation(annotationClass);
                if (annotation != null) {
                    return annotation;
                }
            }
        }
        return null;
    }

    private static AnnotatedElement getType(AnnotatedElement element) {
        if (element instanceof Parameter) {
            return ((Parameter) element).getType();
        }
        if (element instanceof Field) {
            return ((Field) element).getType();
        }
        return Void.class;
    }

    static ParameterHandler createSubjectParameterHandler(Type type) {
        if (!String.class.equals(type)) {
            throw new IllegalArgumentException("Parameter must be of type String");
        }

        return new SubjectParameterHandler();
    }

    static ParameterHandler createAttributeParameterHandler(Type type, EventAttribute attributeAnnotation) {
        String attributeKey = attributeAnnotation.value();
        return new AttributeParameterHandler(attributeKey, Converters.find(type));
    }

    private static ParameterHandler createCompositeParameterHandler(Class<?> type) {
        CompositeParameterHandler<?> handler = new CompositeParameterHandler<>(type);
        for (Field field : type.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())
                    && !Modifier.isFinal(field.getModifiers())) {

                handler.addFieldHandler(field, handler);
            }
        }
        return handler;
    }
}
