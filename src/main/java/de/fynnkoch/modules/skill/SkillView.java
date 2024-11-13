package de.fynnkoch.modules.skill;

import static lombok.AccessLevel.PRIVATE;

import de.fynnkoch.core.models.AbstractEntityView;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Data
@ToString(callSuper = true)
@FieldDefaults(level = PRIVATE)
@EqualsAndHashCode(callSuper = true)
public class SkillView extends AbstractEntityView {

  @NotNull
  @Schema(description = "Name of the skill")
  String name;

  @NotNull
  @Schema(
      description = "Level of the skill",
      allowableValues = {"NOVICE", "ADVANCED_BEGINNER", "INTERMEDIATE", "ADVANCED", "EXPERT"})
  SkillLevel skillLevel;
}
