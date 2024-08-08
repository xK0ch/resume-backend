package de.fynnkoch.modules.skill;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SkillExceptionHandler {

  @ExceptionHandler(SkillNotFoundException.class)
  public ProblemDetail handleSkillNotFoundException(final SkillNotFoundException exception) {
    final ProblemDetail problemDetail =
        ProblemDetail.forStatusAndDetail(NOT_FOUND, exception.getMessage());
    problemDetail.setTitle("Skill not found");
    return problemDetail;
  }
}
