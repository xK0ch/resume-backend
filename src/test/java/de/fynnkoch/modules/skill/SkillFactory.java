package de.fynnkoch.modules.skill;

import static de.fynnkoch.modules.resume.ResumeFactory.resume;
import static de.fynnkoch.modules.skill.SkillCategory.OPERATING_SYSTEM;
import static de.fynnkoch.modules.skill.SkillCategory.OTHER;
import static de.fynnkoch.modules.skill.SkillLevel.ADVANCED;
import static de.fynnkoch.modules.skill.SkillLevel.EXPERT;
import static de.fynnkoch.modules.skill.SkillLevel.INTERMEDIATE;

import de.fynnkoch.modules.resume.Resume;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SkillFactory {

  public static Skill skill() {
    return Skill.builder()
        .isDeleted(false)
        .name("Spring Boot")
        .skillLevel(EXPERT)
        .skillCategory(OPERATING_SYSTEM)
        .resume(resume())
        .build();
  }

  public static Skill skill(final Resume resume) {
    final var skill = skill();
    skill.setResume(resume);
    return skill;
  }

  public static SkillCreateOrder skillCreateOrder() {
    return SkillCreateOrder.builder()
        .name("Angular")
        .skillLevel(ADVANCED)
        .skillCategory(OPERATING_SYSTEM)
        .build();
  }

  public static SkillUpdateOrder skillUpdateOrder() {
    return SkillUpdateOrder.builder()
        .name("React")
        .skillLevel(INTERMEDIATE)
        .skillCategory(OTHER)
        .build();
  }
}
