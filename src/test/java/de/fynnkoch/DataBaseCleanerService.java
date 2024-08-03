package de.fynnkoch;

import static lombok.AccessLevel.PRIVATE;

import de.fynnkoch.modules.resume.ResumeRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@FieldDefaults(level = PRIVATE, makeFinal = true)
class DataBaseCleanerService {

  ResumeRepository resumeRepository;

  void resetAllTables() {
    resumeRepository.deleteAllInBatch();
  }
}
