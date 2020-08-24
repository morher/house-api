package net.morher.house.core.event.template;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Mark the parameter or class that should be parsed as a compound parameter. Fields in the marked type must be marked with
 * mappings just like the parameters.
 * 
 * @author Morten Hermansen
 */
@Documented
@Retention(RUNTIME)
@Target({ TYPE, PARAMETER })
public @interface CompoundParameter {

}
