package de.fynnkoch.modules.timelineevent;

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
public class TimelineEventService {

  TimelineEventRepository timelineEventRepository;

  @Transactional(readOnly = true)
  public List<TimelineEvent> getAllByResume(@NotNull final UUID resumeId) {
    return timelineEventRepository.findAllByResumeIdAndIsDeletedIsFalse(resumeId);
  }

  @Transactional(readOnly = true)
  public TimelineEvent getOne(@NonNull final UUID timelineEventId) {
    return timelineEventRepository
        .findByIdAndIsDeletedIsFalse(timelineEventId)
        .orElseThrow(() -> new TimelineEventNotFoundException(timelineEventId));
  }

  @Transactional
  public TimelineEvent save(@NonNull final TimelineEvent timelineEvent) {
    return timelineEventRepository.save(timelineEvent);
  }

  @Transactional
  public void delete(@NonNull final TimelineEvent timelineEvent) {
    timelineEvent.setIsDeleted(true);
    timelineEventRepository.save(timelineEvent);
  }
}
