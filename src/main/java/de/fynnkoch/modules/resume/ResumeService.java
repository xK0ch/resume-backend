package de.fynnkoch.modules.resume;

import static lombok.AccessLevel.PRIVATE;

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
public class ResumeService {

  ResumeRepository resumeRepository;

  @Transactional(readOnly = true)
  public List<Resume> getAll() {
    return resumeRepository.findAllByIsDeletedIsFalse();
  }

  @Transactional(readOnly = true)
  public Resume getOne(@NonNull final UUID resumeId) {
    return resumeRepository
        .findByIdAndIsDeletedIsFalse(resumeId)
        .orElseThrow(() -> new ResumeNotFoundException(resumeId));
  }

  @Transactional
  public Resume save(@NonNull final Resume resume) {
    return resumeRepository.save(resume);
  }

  @Transactional
  public Resume toggleStatus(@NonNull final Resume resume) {
    if (resume.getStatus().equals(Status.INACTIVE)) {
      final var activeResume = resumeRepository.findByStatusAndIsDeletedIsFalse(Status.ACTIVE);
      if (activeResume.isPresent()) {
        activeResume.get().setStatus(Status.INACTIVE);
        resumeRepository.save(activeResume.get());
      }
    }

    resume.setStatus(resume.getStatus().equals(Status.ACTIVE) ? Status.INACTIVE : Status.ACTIVE);
    return resumeRepository.save(resume);
  }

  @Transactional
  public void delete(@NonNull final Resume resume) {
    resume.setIsDeleted(true);
    resumeRepository.save(resume);
  }
}
