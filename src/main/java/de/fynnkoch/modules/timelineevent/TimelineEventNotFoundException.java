package de.fynnkoch.modules.timelineevent;

import static java.lang.String.format;

import java.util.UUID;

public class TimelineEventNotFoundException extends RuntimeException {

  public TimelineEventNotFoundException(final UUID timelineEventId) {
    super(format("Timeline event with id %s not found", timelineEventId));
  }
}
