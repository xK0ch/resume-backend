package de.fynnkoch.modules.resume;

import static de.fynnkoch.core.Constants.ISO_DATETIME_FORMAT;
import static org.springframework.http.HttpHeaders.IF_MODIFIED_SINCE;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/resumes")
public interface ResumeContract {

  @GetMapping(produces = APPLICATION_JSON_VALUE)
  @Transactional(readOnly = true)
  @ResponseStatus(OK)
  @OkayResponse(description = "If the resumes have been loaded successfully.")
  @Operation(summary = "Get all resumes", tags = "Resumes")
  List<ResumeView> getAll();

  @GetMapping(value = "/{resumeId}", produces = APPLICATION_JSON_VALUE)
  @Transactional(readOnly = true)
  @ResponseStatus(OK)
  @OkayResponse(description = "If the resume has been loaded successfully.")
  @NotFoundResponse(description = "If no resume was found for the given id.")
  @Operation(summary = "Get a resume", tags = "Resumes")
  ResumeView getOne(@PathVariable final UUID resumeId);

  @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  @Transactional
  @ResponseStatus(CREATED)
  @CreatedResponse(description = "If the resume has been created successfully.")
  @UnauthorizedResponse
  @Operation(
      summary = "Create a resume",
      tags = "Resumes",
      security = @SecurityRequirement(name = "basicAuth"))
  ResumeView create(@RequestBody final ResumeCreateOrder resumeView);

  @PutMapping(
      value = "/{resumeId}",
      consumes = APPLICATION_JSON_VALUE,
      produces = APPLICATION_JSON_VALUE)
  @Transactional
  @ResponseStatus(OK)
  @OkayResponse(description = "If the resume has been updated successfully.")
  @UnauthorizedResponse
  @NotFoundResponse(description = "If no resume has been found for the given id.")
  @PreconditionFailedResponse
  @Operation(
      summary = "Update a resume",
      tags = "Resumes",
      security = @SecurityRequirement(name = "basicAuth"))
  ResumeView update(
      @PathVariable final UUID resumeId,
      @RequestBody final ResumeUpdateOrder resumeUpdateOrder,
      @RequestHeader(IF_MODIFIED_SINCE) @DateTimeFormat(pattern = ISO_DATETIME_FORMAT)
          final ZonedDateTime ifModifiedSince);

  @PatchMapping(value = "/{resumeId}/toggle-status", produces = APPLICATION_JSON_VALUE)
  @Transactional
  @ResponseStatus(OK)
  @OkayResponse(description = "If the status of the resume has been toggled successfully.")
  @UnauthorizedResponse
  @NotFoundResponse(description = "If no resume has been found for the given id.")
  @PreconditionFailedResponse
  @Operation(
      summary =
          "Toggles the status of a resume from ACTIVE to INACTIVE and the other way around."
              + " Only one resume can be active at a time",
      tags = "Resumes",
      security = @SecurityRequirement(name = "basicAuth"))
  ResumeView toggleStatus(
      @PathVariable final UUID resumeId,
      @RequestHeader(IF_MODIFIED_SINCE) @DateTimeFormat(pattern = ISO_DATETIME_FORMAT)
          final ZonedDateTime ifModifiedSince);

  @DeleteMapping(value = "/{resumeId}", produces = APPLICATION_JSON_VALUE)
  @Transactional
  @ResponseStatus(NO_CONTENT)
  @NoContentResponse(description = "If the resume has been deleted successfully.")
  @UnauthorizedResponse
  @NotFoundResponse(description = "If no resume has been found for the given id.")
  @PreconditionFailedResponse
  @Operation(
      summary = "Delete a resume",
      tags = "Resumes",
      security = @SecurityRequirement(name = "basicAuth"))
  void delete(
      @PathVariable final UUID resumeId,
      @RequestHeader(IF_MODIFIED_SINCE) @DateTimeFormat(pattern = ISO_DATETIME_FORMAT)
          final ZonedDateTime ifModifiedSince);
}
