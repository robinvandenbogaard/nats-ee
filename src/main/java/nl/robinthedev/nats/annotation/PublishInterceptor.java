package nl.robinthedev.nats.annotation;

import io.nats.client.Connection;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import java.lang.reflect.Method;
import java.util.Optional;
import nl.robinthedev.nats.mapper.NatsMapper;

@Publish
@Priority(10)
@Interceptor
class PublishInterceptor {

  private final Connection connection;

  private final NatsMapper mapper;

  @Inject
  PublishInterceptor(Connection connection, NatsMapper mapper) {
    this.connection = connection;
    this.mapper = mapper;
  }

  @AroundInvoke
  Object publishInvocation(InvocationContext context) throws Exception {
    Object result = context.proceed();
    if (isMissingReturnType(context.getMethod())) {
      throw getMissingReturnTypeException(context);
    }

    if (hasRequiredAnnotations(context)) {
      var subject = conjureSubject(context).orElseThrow(() -> getMissingSubjectException(context));
      publish(result, subject);
    }

    return result;
  }

  private boolean isMissingReturnType(Method method) {
    return method.getReturnType().equals(void.class);
  }

  private IllegalStateException getMissingReturnTypeException(InvocationContext context) {
    return new IllegalStateException(
        ("Return type is missing on %s#%s,"
                + " add a return type as it is used as body to publish on the subject.")
            .formatted(context.getTarget().getClass().getName(), context.getMethod().getName()));
  }

  private IllegalStateException getMissingSubjectException(InvocationContext context) {
    return new IllegalStateException(
        ("Subject is missing on %s#%s, add at least one subject to the method or class @Publish annotation,"
                + " so we know where to publish the body to.")
            .formatted(context.getTarget().getClass().getName(), context.getMethod().getName()));
  }

  private boolean hasRequiredAnnotations(InvocationContext context) {
    return context.getTarget().getClass().isAnnotationPresent(Publish.class)
        || context.getMethod().isAnnotationPresent(Publish.class);
  }

  private void publish(Object body, String subject) {
    connection.publish(subject, mapper.serialize(body));
  }

  private Optional<String> conjureSubject(InvocationContext context) {
    var methodSubject = getSubjectFromMethod(context.getMethod());
    var classSubject = getSubjectFromType(context.getTarget().getClass());
    return new ConcatenatedSubject()
        .classSubject(classSubject)
        .methodSubject(methodSubject)
        .getSubject();
  }

  private String getSubjectFromType(Class<?> targetType) {
    return Optional.ofNullable(targetType.getAnnotation(Publish.class))
        .map(Publish::subject)
        .orElse(null);
  }

  private String getSubjectFromMethod(Method method) {
    return Optional.ofNullable(method.getAnnotation(Publish.class))
        .map(Publish::subject)
        .orElse(null);
  }
}
