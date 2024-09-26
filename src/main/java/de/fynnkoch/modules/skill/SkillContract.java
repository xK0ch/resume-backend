package de.fynnkoch.modules.skill;

import static de.fynnkoch.core.Constants.ISO_DATETIME_FORMAT;
import static org.springframework.http.HttpHeaders.IF_MODIFIED_SINCE;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import de.fynnkoch.core.docs.CreatedResponse;
import de.fynnkoch.core.docs.NoContentResponse;
import de.fynnkoch.core.docs.NotFoundResponse;
import de.fynnkoch.core.docs.OkayResponse;
import de.fynnkoch.core.docs.PreconditionFailedResponse;
import de.fynnkoch.core.docs.UnauthorizedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
  @Transactional(readOnly = true)
  @ResponseStatus(OK)
  @OkayResponse(description = "If the skills have been loaded successfully.")
  @NotFoundResponse(description = "If no resume was found for the given id.")
  @Operation(summary = "Get all skills of a resume", tags = "Skills")
  List<SkillView> getAllByResume(@PathVariable final UUID resumeId);

  @PostMapping
  @Transactional
  @ResponseStatus(CREATED)
  @CreatedResponse(description = "If the skill has been created successfully.")
  @UnauthorizedResponse
  @NotFoundResponse(description = "If no resume was found for the given id.")
  @Operation(
      summary = "Create a skill for a resume",
      tags = "Skills",
      security = @SecurityRequirement(name = "basicAuth"))
  SkillView create(
      @PathVariable final UUID resumeId, @RequestBody final SkillCreateOrder skillCreateOrder);

  @PutMapping("/{skillId}")
  @Transactional
  @ResponseStatus(OK)
  @OkayResponse(description = "If the skill has been updated successfully.")
  @UnauthorizedResponse
  @NotFoundResponse(description = "If no resume or skill was found for the given ids.")
  @PreconditionFailedResponse
  @Operation(
      summary = "Update a skill of a resume",
      tags = "Skills",
      security = @SecurityRequirement(name = "basicAuth"))
  SkillView update(
      @PathVariable final UUID resumeId,
      @PathVariable final UUID skillId,
      @RequestBody final SkillUpdateOrder skillUpdateOrder,
      @RequestHeader(IF_MODIFIED_SINCE) @DateTimeFormat(pattern = ISO_DATETIME_FORMAT)
          final ZonedDateTime ifModifiedSince);

  @DeleteMapping("/{skillId}")
  @Transactional
  @ResponseStatus(NO_CONTENT)
  @NoContentResponse(description = "If the skill has been deleted successfully.")
  @UnauthorizedResponse
  @NotFoundResponse(description = "If no resume or skill has been found for the given ids.")
  @PreconditionFailedResponse
  @Operation(
      summary = "Delete a skill of a resume",
      tags = "Skills",
      security = @SecurityRequirement(name = "basicAuth"))
  void delete(
      @PathVariable final UUID resumeId,
      @PathVariable final UUID skillId,
      @RequestHeader(IF_MODIFIED_SINCE) @DateTimeFormat(pattern = ISO_DATETIME_FORMAT)
          final ZonedDateTime ifModifiedSince);
}
