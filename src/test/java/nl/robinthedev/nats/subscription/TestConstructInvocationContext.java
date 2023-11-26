package nl.robinthedev.nats.subscription;

import jakarta.interceptor.InvocationContext;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;

public class TestConstructInvocationContext implements InvocationContext {
  private final Class<?> clazz;
  private boolean proceedInvoked;

  public TestConstructInvocationContext(Class<?> clazz) {
    this.clazz = clazz;
  }

  @Override
  public Object getTarget() {
    return null;
  }

  @Override
  public Object getTimer() {
    return null;
  }

  @Override
  public Method getMethod() {
    return null;
  }

  @Override
  public Constructor<?> getConstructor() {
    try {
      return clazz.getDeclaredConstructor();
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Object[] getParameters() {
    return new Object[0];
  }

  @Override
  public void setParameters(Object[] params) {}

  @Override
  public Map<String, Object> getContextData() {
    return null;
  }

  @Override
  public Object proceed() {
    proceedInvoked = true;
    return null;
  }

  public boolean isProceedInvoked() {
    return proceedInvoked;
  }
}
