package de.fynnkoch.modules.resume;

import static org.springframework.http.HttpStatus.OK;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/resumes")
public interface ResumeContract {

  @GetMapping
  @ResponseStatus(OK)
  @Transactional(readOnly = true)
  @Operation(summary = "Get all resumes", tags = "Resumes")
  List<ResumeView> getAllResumes();
}
