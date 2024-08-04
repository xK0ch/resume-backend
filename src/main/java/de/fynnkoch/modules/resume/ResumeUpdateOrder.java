package de.fynnkoch.modules.resume;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResumeUpdateOrder {

  @NotNull String firstName;

  @NotNull String lastName;

  @NotNull Sex sex;

  @NotNull LocalDate dateOfBirth;

  @NotNull String address;

  @NotNull String postalCode;

  @NotNull String city;

  @NotNull String country;

  @Nullable String phoneNumber;

  @Nullable String mobileNumber;

  @Nullable String email;

  @NotNull String description;

  @Nullable String linkedin;

  @Nullable String github;
}
