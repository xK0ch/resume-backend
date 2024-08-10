package de.fynnkoch.modules.resume;

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

  @GetMapping
  @ResponseStatus(OK)
  @Transactional(readOnly = true)
  @Operation(summary = "Get all resumes", tags = "Resumes")
  List<ResumeView> getAll();

  @GetMapping("/{resumeId}")
  @ResponseStatus(OK)
  @Transactional(readOnly = true)
  @Operation(summary = "Get a resume", tags = "Resumes")
  ResumeView getOne(@PathVariable final UUID resumeId);

  @PostMapping
  @ResponseStatus(CREATED)
  @Transactional
  @Operation(summary = "Create a resume", tags = "Resumes")
  ResumeView create(@RequestBody final ResumeCreateOrder resumeView);

  @PutMapping("/{resumeId}")
  @ResponseStatus(OK)
  @Transactional
  @Operation(summary = "Update a resume", tags = "Resumes")
  ResumeView update(
      @PathVariable final UUID resumeId,
      @RequestBody final ResumeUpdateOrder resumeUpdateOrder,
      @RequestHeader(IF_MODIFIED_SINCE) @DateTimeFormat(pattern = ISO_DATETIME_FORMAT)
          final ZonedDateTime ifModifiedSince);

  @PatchMapping("/{resumeId}")
  @ResponseStatus(OK)
  @Transactional
  @Operation(
      summary =
          "Toggles the status of a resume from ACTIVE to INACTIVE and the other way around."
              + " Only one resume can be active at a time",
      tags = "Resumes")
  ResumeView toggleStatus(
      @PathVariable final UUID resumeId,
      @RequestHeader(IF_MODIFIED_SINCE) @DateTimeFormat(pattern = ISO_DATETIME_FORMAT)
          final ZonedDateTime ifModifiedSince);

  @DeleteMapping("/{resumeId}")
  @ResponseStatus(NO_CONTENT)
  @Transactional
  @Operation(summary = "Delete a resume", tags = "Resumes")
  void delete(
      @PathVariable final UUID resumeId,
      @RequestHeader(IF_MODIFIED_SINCE) @DateTimeFormat(pattern = ISO_DATETIME_FORMAT)
          final ZonedDateTime ifModifiedSince);
}
