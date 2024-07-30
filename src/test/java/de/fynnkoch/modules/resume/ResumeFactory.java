package de.fynnkoch.modules.resume;

import static de.fynnkoch.modules.resume.Sex.MALE;

import java.time.LocalDate;

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
                .email("some-mail@spring.com")
                .description("Some description")
                .linkedin("https://www.linkedin.com/in/some-linkedin")
                .github("https://github.com/some-github")
                .build();
    }
}
