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
  @Schema(description = "First name of the resumes creator")
  String firstName;

  @NotNull
  @Schema(description = "Last name of the resumes creator")
  String lastName;

  @NotNull
  @Schema(
      description = "Sex of the resumes creator",
      allowableValues = {"FEMALE", "DIVERSE", "MALE"})
  Sex sex;

  @NotNull
  @Schema(description = "Date of birth of the resumes creator")
  LocalDate dateOfBirth;

  @NotNull
  @Schema(
      description = "Address of the resumes creator including street and house number",
      example = "Main Street 1")
  String address;

  @NotNull
  @Schema(description = "Postal code of the city")
  String postalCode;

  @NotNull
  @Schema(description = "City where the resumes creator lives")
  String city;

  @NotNull
  @Schema(description = "Country where the resumes creator lives")
  String country;

  @Nullable
  @Schema(description = "Phone number of the resumes creator")
  String phoneNumber;

  @Nullable
  @Schema(description = "Mobile number of the resumes creator")
  String mobileNumber;

  @Nullable
  @Schema(description = "Email address of the resumes creator")
  String email;

  @NotNull
  @Schema(description = "Description of the resumes creators skills, interests and experiences")
  String description;

  @Nullable
  @Schema(description = "LinkedIn profile URL of the resumes creator")
  String linkedin;

  @Nullable
  @Schema(description = "GitHub profile URL of the resumes creator")
  String github;

  @NotNull
  @Schema(
      description = "Status of the resume",
      allowableValues = {"ACTIVE", "INACTIVE"})
  Status status;
}
