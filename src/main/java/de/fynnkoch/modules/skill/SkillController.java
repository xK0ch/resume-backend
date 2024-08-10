package de.fynnkoch.modules.skill;

import static de.fynnkoch.core.helpers.EntityHelper.checkIfUnmodifiedSince;
import static lombok.AccessLevel.PRIVATE;

import de.fynnkoch.modules.resume.ResumeService;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class SkillController implements SkillContract {

  SkillService skillService;
  SkillMapper skillMapper;
  ResumeService resumeService;

  @Override
  public List<SkillView> getAllByResume(@NonNull final UUID resumeId) {
    return this.skillService.getAllByResume(resumeId).stream()
        .map(this.skillMapper::toView)
        .toList();
  }

  @Override
  public SkillView create(
      @NonNull final UUID resumeId, @NonNull final SkillCreateOrder skillCreateOrder) {
    final var resume = this.resumeService.getOne(resumeId);
    final var skill = this.skillMapper.fromCreateOrder(skillCreateOrder, resume);
    return this.skillMapper.toView(this.skillService.save(skill));
  }

  @Override
  public SkillView update(
      @NonNull final UUID resumeId,
      @NonNull final UUID skillId,
      @NonNull final SkillUpdateOrder skillUpdateOrder,
      @NonNull final ZonedDateTime ifModifiedSince) {
    this.resumeService.getOne(resumeId);
    final var existingSkill = this.skillService.getOne(skillId);

    checkIfUnmodifiedSince(skillId, ifModifiedSince, existingSkill);

    final var skillToBeSaved = this.skillMapper.fromUpdateOrder(existingSkill, skillUpdateOrder);
    return this.skillMapper.toView(this.skillService.save(skillToBeSaved));
  }

  @Override
  public void delete(
      @NonNull final UUID resumeId,
      @NonNull final UUID skillId,
      @NonNull final ZonedDateTime ifModifiedSince) {
    this.resumeService.getOne(resumeId);
    final var existingSkill = this.skillService.getOne(skillId);

    checkIfUnmodifiedSince(skillId, ifModifiedSince, existingSkill);

    this.skillService.delete(existingSkill);
  }
}
