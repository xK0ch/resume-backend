package de.fynnkoch.modules.resume;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class ResumeService {

  ResumeRepository resumeRepository;

  @Transactional(readOnly = true)
  public List<Resume> getAllResumes() {
    return resumeRepository.findAll();
  }
}
