package de.fynnkoch.modules.skill;

import static java.lang.String.format;

import java.util.UUID;

public class SkillNotFoundException extends RuntimeException {

  public SkillNotFoundException(final UUID skillId) {
    super(format("Skill with id %s not found", skillId));
  }
}
