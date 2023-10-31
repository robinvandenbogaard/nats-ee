package nl.robinthedev.nats.subscription;

import java.lang.reflect.Method;

class PayloadSubscribeMethod implements SubscribeMethod {
  private final Method method;
  private final Subject subject;

  public PayloadSubscribeMethod(Class<?> declaringClazz, Method method) {
    this.method = method;
    var classSubject = new ClassSubject(declaringClazz.getAnnotation(Subscriptions.class));
    var methodSubject = new MethodSubject(method.getAnnotation(Subscribe.class));
    this.subject = classSubject.concat(methodSubject);
  }

  @Override
  public Subject subject() {
    return subject;
  }

  @Override
  public PayloadMethod messageMethod() {
    return new PayloadMethod(method);
  }

  @Override
  public boolean hasRequiredParameter() {
    return method.getParameterCount() == 1;
  }

  @Override
  public boolean isSubjectEmpty() {
    return subject().isEmpty();
  }
}
