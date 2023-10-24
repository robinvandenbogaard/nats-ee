package nl.robinthedev.nats.annotation;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ConcatenatedSubjectTest {

  @Test
  void no_annotations_no_subject() {
    assertThat(new ConcatenatedSubject().getSubject()).isEmpty();
  }

  @Test
  void empty_subjects_results_in_no_subject() {
    assertThat(new ConcatenatedSubject().classSubject("").getSubject()).isEmpty();
    assertThat(new ConcatenatedSubject().methodSubject("").getSubject()).isEmpty();
    assertThat(new ConcatenatedSubject().classSubject("").methodSubject("").getSubject()).isEmpty();
    assertThat(new ConcatenatedSubject().classSubject(null).getSubject()).isEmpty();
    assertThat(new ConcatenatedSubject().methodSubject(null).getSubject()).isEmpty();
    assertThat(new ConcatenatedSubject().classSubject(null).methodSubject(null).getSubject())
        .isEmpty();
  }

  @Test
  void only_class() {
    assertThat(new ConcatenatedSubject().classSubject("someSubject").getSubject())
        .hasValue("someSubject");
  }

  @Test
  void only_method() {
    assertThat(new ConcatenatedSubject().methodSubject("anotherSubject").getSubject())
        .hasValue("anotherSubject");
  }

  @Test
  void puts_class_first_if_both_are_present() {
    assertThat(new ConcatenatedSubject().classSubject("first").methodSubject("second").getSubject())
        .hasValue("first.second");
  }
}
