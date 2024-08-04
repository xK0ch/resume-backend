package de.fynnkoch.modules.resume;

import static lombok.AccessLevel.PRIVATE;

import de.fynnkoch.core.models.AbstractEntityView;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Data
@ToString(callSuper = true)
@FieldDefaults(level = PRIVATE)
@EqualsAndHashCode(callSuper = true)
public class ResumeView extends AbstractEntityView {

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

  @NotNull Status status;
}
