package nl.robinthedev.nats.subscription;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class PayloadMethod {

  private final Method method;

  public PayloadMethod(Method method) {
    this.method = method;
  }

  public void invoke(Object instance, Object payload)
      throws InvocationTargetException, IllegalAccessException {
    method.invoke(instance, payload);
  }

  public Class<?> type() {
    return method.getParameterTypes()[0];
  }

  public String name() {
    return method.getDeclaringClass().getName() + "#" + method.getName();
  }
}
