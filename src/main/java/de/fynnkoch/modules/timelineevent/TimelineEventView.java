package de.fynnkoch.modules.timelineevent;

import static lombok.AccessLevel.PRIVATE;

import de.fynnkoch.core.models.AbstractEntityView;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Data
@ToString(callSuper = true)
@FieldDefaults(level = PRIVATE)
@EqualsAndHashCode(callSuper = true)
public class TimelineEventView extends AbstractEntityView {

  @NotNull
  @Schema(description = "Name of the job position")
  String jobPosition;

  @NotNull
  @Schema(description = "Name of the institution")
  String institution;

  @NotNull
  @Schema(description = "Description of the job position")
  String description;

  @NotNull
  @Schema(description = "Date of the event")
  LocalDate dateOfEvent;
}
