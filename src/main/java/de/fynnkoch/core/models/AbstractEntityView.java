package de.fynnkoch.core.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AbstractEntityView {

  @NotNull
  @Schema(
      description = "The unique identifier of the entity",
      example = "123e4567-e89b-12d3-a456-426614174000")
  private UUID id;
}
