package de.fynnkoch.core.models;

import static lombok.AccessLevel.PRIVATE;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = PRIVATE)
public class AbstractEntityView {

  @NotNull
  @Schema(
      description = "The unique identifier of the entity",
      example = "123e4567-e89b-12d3-a456-426614174000")
  UUID id;

  @NotNull
  @Schema(
      description = "The timestamp the entity has last been modified",
      example = "2024-12-05T10:40:51.123456+02:00")
  ZonedDateTime lastModifiedAt;
}
