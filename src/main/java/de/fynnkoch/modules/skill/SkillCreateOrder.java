package de.fynnkoch.modules.skill;

import static lombok.AccessLevel.PRIVATE;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = PRIVATE)
public class SkillCreateOrder {

  @NotNull String name;

  @NotNull SkillCategory skillCategory;

  @NotNull SkillLevel skillLevel;
}
