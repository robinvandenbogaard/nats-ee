package nl.robinthedev.nats.subscription;

import static org.assertj.core.api.Assertions.assertThatNoException;

import nl.robinthedev.nats.subscription.examples.MethodSubjectExample;
import org.junit.jupiter.api.Test;

class PayloadMethodTest {
  @Test
  void can_invoke_protected_methods() throws NoSuchMethodException {
    var instance = new MethodSubjectExample();
    var method = MethodSubjectExample.class.getDeclaredMethod("myMethod", String.class);
    var payloadMethod = new PayloadMethod(method);

    assertThatNoException().isThrownBy(() -> payloadMethod.invoke(instance, "test"));
  }
}
