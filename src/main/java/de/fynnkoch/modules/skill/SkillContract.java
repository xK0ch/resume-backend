package de.fynnkoch.modules.skill;

import static de.fynnkoch.core.Constants.ISO_DATETIME_FORMAT;
import static org.springframework.http.HttpHeaders.IF_MODIFIED_SINCE;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import io.swagger.v3.oas.annotations.Operation;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/resumes/{resumeId}/skills")
public interface SkillContract {

  @GetMapping
  @ResponseStatus(OK)
  @Transactional(readOnly = true)
  @Operation(summary = "Get all skills of a resume", tags = "Skills")
  List<SkillView> getAllByResume(@PathVariable final UUID resumeId);

  @PostMapping
  @ResponseStatus(CREATED)
  @Transactional
  @Operation(summary = "Create a skill for a resume", tags = "Skills")
  SkillView create(
      @PathVariable final UUID resumeId, @RequestBody final SkillCreateOrder skillCreateOrder);

  @PutMapping("/{skillId}")
  @ResponseStatus(OK)
  @Transactional
  @Operation(summary = "Update a skill of a resume", tags = "Skills")
  SkillView update(
      @PathVariable final UUID resumeId,
      @PathVariable final UUID skillId,
      @RequestBody final SkillUpdateOrder skillUpdateOrder,
      @RequestHeader(IF_MODIFIED_SINCE) @DateTimeFormat(pattern = ISO_DATETIME_FORMAT)
          final ZonedDateTime ifModifiedSince);

  @DeleteMapping("/{skillId}")
  @ResponseStatus(NO_CONTENT)
  @Transactional
  @Operation(summary = "Delete a skill of a resume", tags = "Skills")
  void delete(
      @PathVariable final UUID resumeId,
      @PathVariable final UUID skillId,
      @RequestHeader(IF_MODIFIED_SINCE) @DateTimeFormat(pattern = ISO_DATETIME_FORMAT)
          final ZonedDateTime ifModifiedSince);
}
