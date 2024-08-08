package de.fynnkoch.modules.skill;

import static lombok.AccessLevel.PRIVATE;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class SkillService {

  SkillRepository skillRepository;

  @Transactional(readOnly = true)
  public List<Skill> getAllByResume(@NotNull final UUID resumeId) {
    return skillRepository.findAllByResumeIdAndIsDeletedIsFalse(resumeId);
  }

  @Transactional(readOnly = true)
  public Skill getOne(@NonNull final UUID skillId) {
    return skillRepository
        .findByIdAndIsDeletedIsFalse(skillId)
        .orElseThrow(() -> new SkillNotFoundException(skillId));
  }

  @Transactional
  public Skill save(@NonNull final Skill skill) {
    return skillRepository.save(skill);
  }

  @Transactional
  public void delete(@NonNull final Skill skill) {
    skill.setIsDeleted(true);
    skillRepository.save(skill);
  }
}
