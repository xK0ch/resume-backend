package de.fynnkoch.core.helpers;

import static org.springframework.http.HttpStatus.PRECONDITION_FAILED;

import jakarta.persistence.OptimisticLockException;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EntityHelperExceptionHandler {

  @ExceptionHandler(OptimisticLockException.class)
  public ProblemDetail handleOptimisticLockException(final OptimisticLockException exception) {
    final ProblemDetail problemDetail =
        ProblemDetail.forStatusAndDetail(PRECONDITION_FAILED, exception.getMessage());
    problemDetail.setTitle("Entity has been modified");
    return problemDetail;
  }
}
