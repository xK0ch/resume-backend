package de.fynnkoch.core.helpers;

import jakarta.persistence.OptimisticLockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EntityHelperExceptionHandler {

  @ExceptionHandler(OptimisticLockException.class)
  public ProblemDetail handleOptimisticLockException(final OptimisticLockException exception) {
    final ProblemDetail problemDetail =
        ProblemDetail.forStatusAndDetail(HttpStatus.PRECONDITION_FAILED, exception.getMessage());
    problemDetail.setTitle("Entity has been modified");
    return problemDetail;
  }
}
