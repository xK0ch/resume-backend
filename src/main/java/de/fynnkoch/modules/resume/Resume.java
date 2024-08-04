package de.fynnkoch.modules.resume;

import static lombok.AccessLevel.PRIVATE;

import de.fynnkoch.core.models.AbstractEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@SuperBuilder
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE)
@EqualsAndHashCode(callSuper = true)
public class Resume extends AbstractEntity {

  @NotNull @Column String firstName;

  @NotNull @Column String lastName;

  @NotNull
  @Column
  @Enumerated(EnumType.STRING)
  Sex sex;

  @NotNull @Column LocalDate dateOfBirth;

  @NotNull @Column String address;

  @NotNull @Column String postalCode;

  @NotNull @Column String city;

  @NotNull @Column String country;

  @Nullable @Column String phoneNumber;

  @Nullable @Column String mobileNumber;

  @Nullable @Column String email;

  @NotNull @Column String description;

  @Nullable @Column String linkedin;

  @Nullable @Column String github;

  @NotNull
  @Column
  @Enumerated(EnumType.STRING)
  Status status;
}
