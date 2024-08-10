package de.fynnkoch.modules.resume;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ResumeExceptionHandler {

  @ExceptionHandler(ResumeNotFoundException.class)
  public ProblemDetail handleResumeNotFoundException(final ResumeNotFoundException exception) {
    final ProblemDetail problemDetail =
        ProblemDetail.forStatusAndDetail(NOT_FOUND, exception.getMessage());
    problemDetail.setTitle("Resume not found");
    return problemDetail;
  }
}
