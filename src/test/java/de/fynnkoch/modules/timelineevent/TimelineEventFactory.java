package de.fynnkoch.modules.timelineevent;

import static de.fynnkoch.modules.resume.ResumeFactory.resume;
import static java.util.UUID.randomUUID;

import de.fynnkoch.modules.resume.Resume;
import java.time.LocalDate;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TimelineEventFactory {

  public static TimelineEvent timelineEvent() {
    return TimelineEvent.builder()
        .isDeleted(false)
        .jobPosition("Software Engineer")
        .institution("Some company")
        .description("Lorem ipsum")
        .dateOfEvent(LocalDate.now())
        .resume(resume())
        .build();
  }

  public static TimelineEvent timelineEvent(final Resume resume) {
    final var timelineEvent = timelineEvent();
    timelineEvent.setResume(resume);
    return timelineEvent;
  }

  public static TimelineEventCreateOrder timelineEventCreateOrder() {
    return TimelineEventCreateOrder.builder()
        .jobPosition("Tester")
        .institution("Example company")
        .description("Lorem ipsum")
        .dateOfEvent(LocalDate.now())
        .build();
  }

  public static TimelineEventUpdateOrder timelineEventUpdateOrder() {
    return TimelineEventUpdateOrder.builder()
        .jobPosition("Project manager")
        .institution("Company GmbH")
        .description("Lorem ipsum")
        .dateOfEvent(LocalDate.now())
        .build();
  }
}
