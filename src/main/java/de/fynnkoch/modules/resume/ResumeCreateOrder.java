package de.fynnkoch.modules.resume;

import de.fynnkoch.core.models.Sex;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResumeCreateOrder {

  @NotNull String firstName;

  @NotNull String lastName;

  @NotNull Sex sex;

  @NotNull LocalDate dateOfBirth;

  @NotNull String address;

  @NotNull String postalCode;

  @NotNull String city;

  @NotNull String country;

  @NotNull String phoneNumber;

  @NotNull String email;

  @NotNull String description;

  @Nullable String linkedin;

  @Nullable String github;
}
