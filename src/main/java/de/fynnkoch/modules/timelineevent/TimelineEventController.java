package de.fynnkoch.modules.timelineevent;

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
public class TimelineEventController implements TimelineEventContract {

  TimelineEventService timelineEventService;
  TimelineEventMapper timelineEventMapper;
  ResumeService resumeService;

  @Override
  public List<TimelineEventView> getAllByResume(@NonNull final UUID resumeId) {
    this.resumeService.getOne(resumeId);
    return this.timelineEventService.getAllByResume(resumeId).stream()
        .map(this.timelineEventMapper::toView)
        .toList();
  }

  @Override
  public TimelineEventView create(
      @NonNull final UUID resumeId,
      @NonNull final TimelineEventCreateOrder timelineEventCreateOrder) {
    final var resume = this.resumeService.getOne(resumeId);
    final var timelineEvent =
        this.timelineEventMapper.fromCreateOrder(timelineEventCreateOrder, resume);
    return this.timelineEventMapper.toView(this.timelineEventService.save(timelineEvent));
  }

  @Override
  public TimelineEventView update(
      @NonNull final UUID resumeId,
      @NonNull final UUID timelineEventId,
      @NonNull final TimelineEventUpdateOrder timelineEventUpdateOrder,
      @NonNull final ZonedDateTime ifModifiedSince) {
    this.resumeService.getOne(resumeId);
    final var existingTimelineEvent = this.timelineEventService.getOne(timelineEventId);

    checkIfUnmodifiedSince(timelineEventId, ifModifiedSince, existingTimelineEvent);

    final var timelineEventToBeSaved =
        this.timelineEventMapper.fromUpdateOrder(existingTimelineEvent, timelineEventUpdateOrder);
    return this.timelineEventMapper.toView(this.timelineEventService.save(timelineEventToBeSaved));
  }

  @Override
  public void delete(
      @NonNull final UUID resumeId,
      @NonNull final UUID timelineEventId,
      @NonNull final ZonedDateTime ifModifiedSince) {
    this.resumeService.getOne(resumeId);
    final var existingTimelineEvent = this.timelineEventService.getOne(timelineEventId);

    checkIfUnmodifiedSince(timelineEventId, ifModifiedSince, existingTimelineEvent);

    this.timelineEventService.delete(existingTimelineEvent);
  }
}
