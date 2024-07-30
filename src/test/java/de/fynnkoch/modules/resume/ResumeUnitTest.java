package de.fynnkoch.modules.resume;

import static de.fynnkoch.modules.resume.ResumeFactory.resume;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import de.fynnkoch.AbstractUnitTest;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class ResumeUnitTest implements AbstractUnitTest {

  @Mock private ResumeRepository resumeRepository;

  private ResumeService resumeService;

  @BeforeEach
  public void setup() {
    resumeService = new ResumeService(resumeRepository);
  }

  @Test
  void getContactInformation() {
    when(resumeRepository.findAll()).thenReturn(List.of(resume()));
    final List<Resume> resume = resumeService.getAllResumes();
    assertThat(resume).hasSize(1);
  }
}
