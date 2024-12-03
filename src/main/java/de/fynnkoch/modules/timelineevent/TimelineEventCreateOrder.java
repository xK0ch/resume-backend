package de.fynnkoch.modules.timelineevent;

import static lombok.AccessLevel.PRIVATE;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = PRIVATE)
public class TimelineEventCreateOrder {

  @NotNull String jobPosition;

  @NotNull String institution;

  @NotNull String description;

  @NotNull LocalDate dateOfEvent;
}
