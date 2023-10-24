package nl.robinthedev.nats.annotation;

import jakarta.enterprise.util.Nonbinding;
import jakarta.interceptor.InterceptorBinding;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate a method, and it will publish this return value to the subject defined by the value in
 * subject.
 *
 * <p>It will concatenate the subject on the class with the subject on the method if they are both
 * present.
 *
 * <p>If only one subject is present it will use that subject.
 *
 * <p>If no subject is present the {@link nl.robinthedev.nats.annotation.PublishInterceptor} will raise an {@link java.lang.IllegalStateException}
 */
@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Inherited
public @interface Publish {
  @Nonbinding
  String subject() default "";
}
