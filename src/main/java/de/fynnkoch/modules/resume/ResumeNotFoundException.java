package de.fynnkoch.modules.resume;

import java.util.UUID;

import static java.lang.String.format;

public class ResumeNotFoundException extends RuntimeException {

  public ResumeNotFoundException(final UUID resumeId) {
    super(format("Resume with id %s not found", resumeId));
  }
}
