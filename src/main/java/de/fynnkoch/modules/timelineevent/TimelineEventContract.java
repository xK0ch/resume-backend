package de.fynnkoch.modules.timelineevent;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/resumes/{resumeId}/timeline-events")
public interface TimelineEventContract {

  @GetMapping(produces = APPLICATION_JSON_VALUE)
  @Transactional(readOnly = true)
  @ResponseStatus(OK)
  @OkayResponse(description = "If the timeline events have been loaded successfully.")
  @NotFoundResponse(description = "If no resume was found for the given id.")
  @Operation(summary = "Get the timeline events of a resume", tags = "Timeline events")
  List<TimelineEventView> getAllByResume(@PathVariable final UUID resumeId);

  @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  @Transactional
  @ResponseStatus(CREATED)
  @CreatedResponse(description = "If the timeline event has been created successfully.")
  @UnauthorizedResponse
  @NotFoundResponse(description = "If no resume was found for the given id.")
  @Operation(
      summary = "Create a timeline event for a resume",
      tags = "Timeline events",
      security = @SecurityRequirement(name = "basicAuth"))
  TimelineEventView create(
      @PathVariable final UUID resumeId,
      @RequestBody final TimelineEventCreateOrder timelineEventCreateOrder);

  @PutMapping(
      value = "/{timelineEventId}",
      consumes = APPLICATION_JSON_VALUE,
      produces = APPLICATION_JSON_VALUE)
  @Transactional
  @ResponseStatus(OK)
  @OkayResponse(description = "If the timeline event has been updated successfully.")
  @UnauthorizedResponse
  @NotFoundResponse(description = "If no resume or timeline event was found for the given ids.")
  @PreconditionFailedResponse
  @Operation(
      summary = "Update a timeline event of a resume",
      tags = "Timeline events",
      security = @SecurityRequirement(name = "basicAuth"))
  TimelineEventView update(
      @PathVariable final UUID resumeId,
      @PathVariable final UUID timelineEventId,
      @RequestBody final TimelineEventUpdateOrder timelineEventUpdateOrder,
      @RequestHeader(IF_MODIFIED_SINCE) @DateTimeFormat(pattern = ISO_DATETIME_FORMAT)
          final ZonedDateTime ifModifiedSince);

  @DeleteMapping(value = "/{timelineEventId}", produces = APPLICATION_JSON_VALUE)
  @Transactional
  @ResponseStatus(NO_CONTENT)
  @NoContentResponse(description = "If the timeline vent has been deleted successfully.")
  @UnauthorizedResponse
  @NotFoundResponse(
      description = "If no resume or timeline event has been found for the given ids.")
  @PreconditionFailedResponse
  @Operation(
      summary = "Delete a timeline event of a resume",
      tags = "Timeline events",
      security = @SecurityRequirement(name = "basicAuth"))
  void delete(
      @PathVariable final UUID resumeId,
      @PathVariable final UUID timelineEventId,
      @RequestHeader(IF_MODIFIED_SINCE) @DateTimeFormat(pattern = ISO_DATETIME_FORMAT)
          final ZonedDateTime ifModifiedSince);
}
