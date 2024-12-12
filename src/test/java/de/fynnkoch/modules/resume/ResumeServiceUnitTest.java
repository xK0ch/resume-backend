package de.fynnkoch.modules.resume;

import static de.fynnkoch.modules.resume.ResumeFactory.resume;
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

public class ResumeServiceUnitTest implements AbstractUnitTest {

  @Mock private ResumeRepository resumeRepository;

  private ResumeService resumeService;

  @BeforeEach
  public void setup() {
    resumeService = new ResumeService(resumeRepository);
  }

  @Test
  void getAll_success() {
    when(resumeRepository.findAllByIsDeletedIsFalse()).thenReturn(List.of(resume()));
    final List<Resume> resume = resumeService.getAll();
    assertThat(resume).hasSize(1);
  }

  @Test
  void getOne_success() {
    when(resumeRepository.findByIdAndIsDeletedIsFalse(any())).thenReturn(Optional.of(resume()));
    final Resume resume = resumeService.getOne(randomUUID());
    assertThat(resume).isNotNull();
  }

  @Test
  void getOne_failure() {
    when(resumeRepository.findByIdAndIsDeletedIsFalse(any())).thenReturn(Optional.empty());
    assertThatThrownBy(() -> resumeService.getOne(randomUUID()))
        .isInstanceOf(ResumeNotFoundException.class);
  }

  @Test
  void save_success() {
    final var resume = resume();
    when(resumeRepository.save(resume))
        .thenAnswer(
            args -> {
              final var savedResume = args.getArgument(0, Resume.class);
              assertThat(savedResume).isEqualTo(resume);
              return savedResume;
            });
    final Resume savedResume = resumeService.save(resume);
    assertThat(savedResume).isNotNull();
  }

  @Test
  void toggleStatus_successInactive() {
    final var resume = resume();
    when(resumeRepository.save(resume)).thenReturn(resume);
    when(resumeRepository.findByStatusAndIsDeletedIsFalse(any())).thenReturn(Optional.empty());
    final var result = resumeService.toggleStatus(resume);
    assertThat(result.getStatus()).isEqualTo(Status.ACTIVE);
  }

  @Test
  void toggleStatus_successActive() {
    final var resume = resume();
    resume.setStatus(Status.ACTIVE);
    when(resumeRepository.save(any())).thenReturn(resume());
    final var result = resumeService.toggleStatus(resume);
    assertThat(result.getStatus()).isEqualTo(Status.INACTIVE);
  }

  @Test
  void delete_success() {
    final var resume = resume();
    when(resumeRepository.save(resume))
        .thenAnswer(
            args -> {
              final var savedResume = args.getArgument(0, Resume.class);
              assertThat(savedResume).isEqualTo(resume);
              return savedResume;
            });
    resumeService.delete(resume);
    assertThat(resume.getIsDeleted()).isTrue();
  }
}
