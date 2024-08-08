package de.fynnkoch.modules.skill;

import static de.fynnkoch.modules.resume.ResumeFactory.resume;
import static de.fynnkoch.modules.skill.SkillFactory.skill;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import de.fynnkoch.AbstractUnitTest;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class SkillServiceUnitTest implements AbstractUnitTest {

  @Mock private SkillRepository skillRepository;

  private SkillService skillService;

  @BeforeEach
  public void setup() {
    skillService = new SkillService(skillRepository);
  }

  @Test
  void getAllByResume_success() {
    when(skillRepository.findAllByResumeIdAndIsDeletedIsFalse(any())).thenReturn(List.of(skill()));
    final List<Skill> resume = skillService.getAllByResume(randomUUID());
    assertThat(resume).hasSize(1);
  }

  @Test
  void getOne_success() {
    when(skillRepository.findByIdAndIsDeletedIsFalse(any())).thenReturn(Optional.of(skill()));
    final Skill skill = skillService.getOne(resume().getId());
    assertThat(skill).isNotNull();
  }

  @Test
  void getOne_failure() {
    when(skillRepository.findByIdAndIsDeletedIsFalse(any())).thenReturn(Optional.empty());
    assertThatThrownBy(() -> skillService.getOne(randomUUID()))
        .isInstanceOf(SkillNotFoundException.class);
  }

  @Test
  void save_success() {
    final var skill = skill();
    when(skillRepository.save(skill))
        .thenAnswer(
            args -> {
              final var savedSkill = args.getArgument(0, Skill.class);
              assertThat(savedSkill).isEqualTo(skill);
              return savedSkill;
            });
    final Skill savedSkill = skillService.save(skill);
    assertThat(savedSkill).isNotNull();
  }

  @Test
  void delete_success() {
    final var skill = skill();
    when(skillRepository.save(skill))
        .thenAnswer(
            args -> {
              final var savedSkill = args.getArgument(0, Skill.class);
              assertThat(savedSkill).isEqualTo(skill);
              return savedSkill;
            });
    final Skill savedSkill = skillService.save(skill);
    assertThat(savedSkill).isNotNull();
  }
}
