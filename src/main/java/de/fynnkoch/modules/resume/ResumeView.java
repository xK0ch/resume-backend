package de.fynnkoch.modules.resume;

import static lombok.AccessLevel.PRIVATE;

import de.fynnkoch.core.models.AbstractEntityView;
import io.swagger.v3.oas.annotations.media.Schema;
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

  @NotNull
  @Schema(description = "First name of the person")
  String firstName;

  @NotNull
  @Schema(description = "Last name of the person")
  String lastName;

  @NotNull
  @Schema(
      description = "Sex of the person",
      allowableValues = {"FEMALE", "DIVERSE", "MALE"})
  Sex sex;

  @NotNull
  @Schema(description = "Date of birth of the person")
  LocalDate dateOfBirth;

  @NotNull
  @Schema(
      description = "Address of the person including street and house number",
      example = "Main Street 1")
  String address;

  @NotNull
  @Schema(description = "Postal code of the city")
  String postalCode;

  @NotNull
  @Schema(description = "City where the person lives")
  String city;

  @NotNull
  @Schema(description = "Country where the person lives")
  String country;

  @NotNull
  @Schema(description = "Phone number of the person")
  String phoneNumber;

  @NotNull
  @Schema(description = "Email address of the person")
  String email;

  @NotNull
  @Schema(description = "Description of the persons skills, interests and experiences")
  String description;

  @Nullable
  @Schema(description = "LinkedIn profile URL of the person")
  String linkedin;

  @Nullable
  @Schema(description = "GitHub profile URL of the person")
  String github;

  @NotNull
  @Schema(
      description = "Status of the resume",
      allowableValues = {"ACTIVE", "INACTIVE"})
  Status status;
}
