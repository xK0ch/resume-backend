package de.fynnkoch.core.helpers;

import static java.lang.String.format;

import de.fynnkoch.core.models.AbstractEntity;
import jakarta.persistence.OptimisticLockException;
import jakarta.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EntityHelper {

  public static <T extends AbstractEntity> void checkIfUnmodifiedSince(
      @NotNull final UUID id,
      @NotNull final ZonedDateTime ifUnmodifiedSince,
      @NotNull final T entity) {
    if (entity.getLastModifiedAt().isAfter(ifUnmodifiedSince)) {
      throw new OptimisticLockException(
          format("Entity with id %s was modified since %s", id, ifUnmodifiedSince));
    }
  }
}
