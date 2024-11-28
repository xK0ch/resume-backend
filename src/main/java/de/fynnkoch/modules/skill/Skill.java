package de.fynnkoch.modules.skill;

import static lombok.AccessLevel.PRIVATE;

import de.fynnkoch.core.models.AbstractEntity;
import de.fynnkoch.modules.resume.Resume;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
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
public class Skill extends AbstractEntity {

  @NotNull @Column String name;

  @NotNull
  @Column
  @Enumerated(EnumType.STRING)
  SkillCategory skillCategory;

  @NotNull
  @Column
  @Enumerated(EnumType.STRING)
  SkillLevel skillLevel;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "resume_id")
  Resume resume;
}
