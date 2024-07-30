package de.fynnkoch.modules.resume;

import static lombok.AccessLevel.PRIVATE;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class ResumeController implements ResumeContract {

  ResumeService resumeService;
  ResumeMapper resumeMapper;

  @Override
  public List<ResumeView> getAllResumes() {
    return this.resumeService.getAllResumes().stream().map(this.resumeMapper::toView).toList();
  }
}
