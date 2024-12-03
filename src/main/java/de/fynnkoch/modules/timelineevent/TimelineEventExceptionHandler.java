package de.fynnkoch.modules.timelineevent;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TimelineEventExceptionHandler {

  @ExceptionHandler(TimelineEventNotFoundException.class)
  public ProblemDetail handleTimelineEventNotFoundException(
      final TimelineEventNotFoundException exception) {
    final ProblemDetail problemDetail =
        ProblemDetail.forStatusAndDetail(NOT_FOUND, exception.getMessage());
    problemDetail.setTitle("Timeline event not found");
    return problemDetail;
  }
}
