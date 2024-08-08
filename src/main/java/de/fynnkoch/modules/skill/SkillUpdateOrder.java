package de.fynnkoch.modules.skill;

import static lombok.AccessLevel.PRIVATE;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = PRIVATE)
public class SkillUpdateOrder {

  @NotNull String name;

  @NotNull SkillLevel skillLevel;
}
