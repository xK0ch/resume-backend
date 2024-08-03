package de.fynnkoch.core;

import de.fynnkoch.modules.resume.ResumeNotFoundException;
import jakarta.persistence.OptimisticLockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(OptimisticLockException.class)
    public ProblemDetail handleOptimisticLockException(final OptimisticLockException exception) {
        final ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(HttpStatus.PRECONDITION_FAILED, exception.getMessage());
        problemDetail.setTitle("Entity has been modified");
        return problemDetail;
    }

    @ExceptionHandler(ResumeNotFoundException.class)
    public ProblemDetail handleResumeNotFoundException(final ResumeNotFoundException exception) {
        final ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
        problemDetail.setTitle("Resume not found");
        return problemDetail;
    }
}
