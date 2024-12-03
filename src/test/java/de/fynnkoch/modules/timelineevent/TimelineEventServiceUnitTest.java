package de.fynnkoch.modules.timelineevent;

import static de.fynnkoch.modules.resume.ResumeFactory.resume;
import static de.fynnkoch.modules.timelineevent.TimelineEventFactory.timelineEvent;
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

public class TimelineEventServiceUnitTest implements AbstractUnitTest {

  @Mock private TimelineEventRepository timelineEventRepository;

  private TimelineEventService timelineEventService;

  @BeforeEach
  public void setup() {
    timelineEventService = new TimelineEventService(timelineEventRepository);
  }

  @Test
  void getAllByResume_success() {
    when(timelineEventRepository.findAllByResumeIdAndIsDeletedIsFalse(any()))
        .thenReturn(List.of(timelineEvent()));
    final List<TimelineEvent> timelineEvents = timelineEventService.getAllByResume(randomUUID());
    assertThat(timelineEvents).hasSize(1);
  }

  @Test
  void getOne_success() {
    when(timelineEventRepository.findByIdAndIsDeletedIsFalse(any()))
        .thenReturn(Optional.of(timelineEvent()));
    final TimelineEvent timelineEvent = timelineEventService.getOne(resume().getId());
    assertThat(timelineEvent).isNotNull();
  }

  @Test
  void getOne_failure() {
    when(timelineEventRepository.findByIdAndIsDeletedIsFalse(any())).thenReturn(Optional.empty());
    assertThatThrownBy(() -> timelineEventService.getOne(randomUUID()))
        .isInstanceOf(TimelineEventNotFoundException.class);
  }

  @Test
  void save_success() {
    final var timelineEvent = timelineEvent();
    when(timelineEventRepository.save(timelineEvent))
        .thenAnswer(
            args -> {
              final var savedTimelineEvent = args.getArgument(0, TimelineEvent.class);
              assertThat(savedTimelineEvent).isEqualTo(timelineEvent);
              return savedTimelineEvent;
            });
    final TimelineEvent savedTimelineEvent = timelineEventService.save(timelineEvent);
    assertThat(savedTimelineEvent).isNotNull();
  }

  @Test
  void delete_success() {
    final var timelineEvent = timelineEvent();
    when(timelineEventRepository.save(timelineEvent))
        .thenAnswer(
            args -> {
              final var savedTimelineEvent = args.getArgument(0, TimelineEvent.class);
              assertThat(savedTimelineEvent).isEqualTo(timelineEvent);
              return savedTimelineEvent;
            });
    final TimelineEvent savedTimelineEvent = timelineEventService.save(timelineEvent);
    assertThat(savedTimelineEvent).isNotNull();
  }
}
