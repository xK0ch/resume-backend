package de.fynnkoch.modules.timelineevent;

import static lombok.AccessLevel.PRIVATE;

import de.fynnkoch.core.models.AbstractEntity;
import de.fynnkoch.modules.resume.Resume;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class TimelineEvent extends AbstractEntity {

  @NotNull @Column String jobPosition;

  @NotNull @Column String institution;

  @NotNull @Column String description;

  @NotNull @Column LocalDate dateOfEvent;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "resume_id")
  Resume resume;
}
