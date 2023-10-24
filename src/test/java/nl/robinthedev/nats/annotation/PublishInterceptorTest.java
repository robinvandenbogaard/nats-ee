package nl.robinthedev.nats.annotation;

import static org.assertj.core.api.Assertions.*;

import nl.robinthedev.nats.mapper.NatsMapper;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PublishInterceptorTest {

  private TestPublishConnection connection;
  private PublishInterceptor interceptor;

  @BeforeEach
  void setUp() {
    connection = new TestPublishConnection();
    interceptor = new PublishInterceptor(connection, NatsMapper.createDefault());
  }

  @Test
  void only_use_subject_from_method_if_class_has_none() throws Exception {
    var context = getInvocationContext(new MethodsWithAnnotation(), "submit");

    interceptor.publishInvocation(context);

    var publishParameters = connection.getPublishParameters();
    assertThat(publishParameters.subject()).isEqualTo("submitSubject");
  }

  @Test
  void use_the_method_return_type_as_data_to_publish() throws Exception {
    var context = getInvocationContext(new MethodsWithAnnotation(), "doPublish");

    interceptor.publishInvocation(context);

    var publishParameters = connection.getPublishParameters();
    assertThat(publishParameters.bodyString()).isEqualTo("{\"content\":\"doPublishData\"}");
  }

  @Test
  void only_use_subject_from_class_if_method_has_none() throws Exception {
    var context = getInvocationContext(new ClassAndMethodWithAnnotation(), "noSubjectMethod");

    interceptor.publishInvocation(context);

    var publishParameters = connection.getPublishParameters();
    assertThat(publishParameters.subject()).isEqualTo("classSubject");
  }

  @Test
  void concatenate_subjects_from_class_and_method_annotation() throws Exception {
    var context = getInvocationContext(new ClassAndMethodWithAnnotation(), "submit");

    interceptor.publishInvocation(context);

    var publishParameters = connection.getPublishParameters();
    assertThat(publishParameters.subject()).isEqualTo("classSubject.methodSubject");
  }

  @Test
  void do_nothing_if_method_is_not_annotated_with_Publish() throws Exception {
    var context = getInvocationContext(new NothingWithAnnotation(), "regularMethod");

    interceptor.publishInvocation(context);

    assertThat(connection.isInvoked()).isFalse();
  }

  @Test
  void raise_an_error_if_neither_class_and_method_has_a_subject() throws Exception {
    var context = getInvocationContext(new MethodsWithAnnotation(), "missingSubjectMethod");

    ThrowableAssert.ThrowingCallable publishInvocation =
        () -> interceptor.publishInvocation(context);

    assertThatThrownBy(publishInvocation)
        .isInstanceOf(IllegalStateException.class)
        .hasMessage(
            "Subject is missing on nl.robinthedev.nats.annotation.PublishInterceptorTest$MethodsWithAnnotation#missingSubjectMethod,"
                + " add at least one subject to the method or class @Publish annotation, so we know where to publish the body to.");
  }

  @Test
  void raise_an_error_if_method_does_not_have_a_return_type() throws Exception {
    var context = getInvocationContext(new MethodsWithAnnotation(), "voidMethod");

    ThrowableAssert.ThrowingCallable publishInvocation =
        () -> interceptor.publishInvocation(context);

    assertThatThrownBy(publishInvocation)
        .isInstanceOf(IllegalStateException.class)
        .hasMessage(
            "Return type is missing on nl.robinthedev.nats.annotation.PublishInterceptorTest$MethodsWithAnnotation#voidMethod,"
                + " add a return type as it is used as body to publish on the subject.");
  }

  TestMethodInvocationContext getInvocationContext(Object target, String methodName)
      throws NoSuchMethodException {
    var method = target.getClass().getMethod(methodName);
    return new TestMethodInvocationContext(target, method, new Object[] {});
  }

  private static class MethodsWithAnnotation {

    @Publish(subject = "submitSubject")
    public String submit() {
      return "submitData";
    }

    @Publish(subject = "doPublishSubject")
    public MyData doPublish() {
      return new MyData("doPublishData");
    }

    @Publish
    public String missingSubjectMethod() {
      return "missingSubjectMethod";
    }

    @Publish(subject = "voidMethodSubject")
    public void voidMethod() {}
  }

  private record MyData(String content) {}

  @Publish(subject = "classSubject")
  private static class ClassWithAnnotation {

    public String notAnnotatedMethod() {
      return "";
    }
  }

  @Publish(subject = "classSubject")
  private static class ClassAndMethodWithAnnotation {

    @Publish(subject = "methodSubject")
    public String submit() {
      return "";
    }

    @Publish
    public String noSubjectMethod() {
      return "";
    }
  }

  private static class NothingWithAnnotation {
    public String regularMethod() {
      return "regularMethod";
    }
  }
}
