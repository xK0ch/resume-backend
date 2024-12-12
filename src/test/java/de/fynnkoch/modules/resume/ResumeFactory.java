package de.fynnkoch.modules.resume;

import static de.fynnkoch.modules.resume.Sex.DIVERSE;
import static de.fynnkoch.modules.resume.Sex.FEMALE;
import static de.fynnkoch.modules.resume.Sex.MALE;
import static de.fynnkoch.modules.resume.Status.INACTIVE;
import static java.util.UUID.randomUUID;

import java.time.LocalDate;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ResumeFactory {

  public static Resume resume() {
    return Resume.builder()
        .isDeleted(false)
        .firstName("Tom")
        .lastName("Smith")
        .sex(MALE)
        .dateOfBirth(LocalDate.of(1990, 1, 1))
        .address("Main Street 1")
        .postalCode("12345")
        .city("Springfield")
        .country("USA")
        .phoneNumber("+1234567890")
        .mobileNumber("+1234567890")
        .email("some-mail@spring.com")
        .description("Some description")
        .linkedin("https://www.linkedin.com/in/some-linkedin")
        .github("https://github.com/some-github")
        .status(INACTIVE)
        .build();
  }

  public static Resume resume(final Status status) {
    final var resume = resume();
    resume.setStatus(status);
    return resume;
  }

  public static ResumeCreateOrder resumeCreateOrder() {
    return ResumeCreateOrder.builder()
        .firstName("Linda")
        .lastName("Jones")
        .sex(FEMALE)
        .dateOfBirth(LocalDate.of(1998, 12, 17))
        .address("Second Street 2")
        .postalCode("67891")
        .city("Springfield")
        .country("USA")
        .phoneNumber("+831461041")
        .mobileNumber("+831461041")
        .email("some-mail@boot.com")
        .description("It's me, Linda")
        .linkedin("https://www.linkedin.com/in/linbdalinkedin")
        .github("https://github.com/linda-github")
        .build();
  }

  public static ResumeUpdateOrder resumeUpdateOrder() {
    return ResumeUpdateOrder.builder()
        .firstName("Michael")
        .lastName("Williams")
        .sex(DIVERSE)
        .dateOfBirth(LocalDate.of(1995, 5, 5))
        .address("Third Street 3")
        .postalCode("54321")
        .city("Springfield")
        .country("USA")
        .phoneNumber("+9876543210")
        .mobileNumber("+9876543210")
        .email("some-mail@java.com")
        .description("It's me, Michael")
        .linkedin("https://www.linkedin.com/in/michael-linkedin")
        .github("https://github.com/michael-github")
        .build();
  }
}
