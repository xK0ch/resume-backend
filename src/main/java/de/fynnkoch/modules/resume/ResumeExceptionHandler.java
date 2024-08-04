package de.fynnkoch.modules.resume;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ResumeExceptionHandler {

  @ExceptionHandler(ResumeNotFoundException.class)
  public ProblemDetail handleResumeNotFoundException(final ResumeNotFoundException exception) {
    final ProblemDetail problemDetail =
        ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    problemDetail.setTitle("Resume not found");
    return problemDetail;
  }
}
