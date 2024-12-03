package de.fynnkoch.modules.timelineevent;

import static de.fynnkoch.core.Constants.ISO_DATETIME_FORMAT;
import static de.fynnkoch.modules.resume.ResumeFactory.resume;
import static de.fynnkoch.modules.timelineevent.TimelineEventFactory.timelineEvent;
import static de.fynnkoch.modules.timelineevent.TimelineEventFactory.timelineEventCreateOrder;
import static de.fynnkoch.modules.timelineevent.TimelineEventFactory.timelineEventUpdateOrder;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static java.lang.String.format;
import static java.time.ZonedDateTime.now;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.IF_MODIFIED_SINCE;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.PRECONDITION_FAILED;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import de.fynnkoch.AbstractIntegrationTest;
import de.fynnkoch.modules.resume.Resume;
import de.fynnkoch.modules.resume.ResumeRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ProblemDetail;

public class TimelineEventControllerIntegrationTest extends AbstractIntegrationTest {

  private static final String TIMELINE_EVENT_PATH = "/resumes/%s/timeline-events/%s";
  private static final String TIMELINE_EVENTS_PATH = "/resumes/%s/timeline-events";

  @Autowired private TimelineEventRepository timelineEventRepository;
  @Autowired private ResumeRepository resumeRepository;

  private Resume resume;

  @BeforeEach
  protected void before() {
    this.resume = resumeRepository.save(resume());
  }

  @Test
  public void getAllByResume_success() {
    final var timelineEvent = this.timelineEventRepository.save(timelineEvent(this.resume));

    final List<TimelineEventView> timelineEvents =
        given()
            .contentType(JSON)
            .get(getFullPathVariable(format(TIMELINE_EVENTS_PATH, this.resume.getId())))
            .then()
            .statusCode(OK.value())
            .extract()
            .jsonPath()
            .getList("", TimelineEventView.class);

    assertThat(timelineEvents).hasSize(1);
    assertThat(timelineEvents.getFirst())
        .extracting(
            TimelineEventView::getJobPosition,
            TimelineEventView::getInstitution,
            TimelineEventView::getDescription,
            TimelineEventView::getDateOfEvent)
        .containsExactly(
            timelineEvent.getJobPosition(),
            timelineEvent.getInstitution(),
            timelineEvent.getDescription(),
            timelineEvent.getDateOfEvent());
  }

  @Test
  public void getAllByResume_withoutResults() {
    final List<TimelineEventView> timelineEvent =
        given()
            .contentType(JSON)
            .get(getFullPathVariable(format(TIMELINE_EVENTS_PATH, this.resume.getId())))
            .then()
            .statusCode(OK.value())
            .extract()
            .jsonPath()
            .getList("", TimelineEventView.class);

    assertThat(timelineEvent).isEmpty();
  }

  @Test
  public void getAllByResume_resumeNotFound() {
    final var resumeId = randomUUID();

    final ProblemDetail problemDetail =
        given()
            .contentType(JSON)
            .get(getFullPathVariable(format(TIMELINE_EVENTS_PATH, resumeId)))
            .then()
            .statusCode(NOT_FOUND.value())
            .extract()
            .as(ProblemDetail.class);

    assertThat(problemDetail.getDetail())
        .isEqualTo(format("Resume with id %s not found", resumeId));
  }

  @Test
  public void create_success() {
    final var timelineEventCreateOrder = timelineEventCreateOrder();

    final TimelineEventView createdTimelineEvent =
        givenAuthenticated()
            .contentType(JSON)
            .body(timelineEventCreateOrder)
            .post(getFullPathVariable(format(TIMELINE_EVENTS_PATH, this.resume.getId())))
            .then()
            .statusCode(CREATED.value())
            .extract()
            .as(TimelineEventView.class);

    assertThat(this.resumeRepository.findAll()).hasSize(1);

    assertThat(createdTimelineEvent)
        .extracting(
            TimelineEventView::getJobPosition,
            TimelineEventView::getInstitution,
            TimelineEventView::getDescription,
            TimelineEventView::getDateOfEvent)
        .containsExactly(
            timelineEventCreateOrder.getJobPosition(),
            timelineEventCreateOrder.getInstitution(),
            timelineEventCreateOrder.getDescription(),
            timelineEventCreateOrder.getDateOfEvent());
  }

  @Test
  public void create_unauthorized() {
    given()
        .contentType(JSON)
        .body(timelineEventCreateOrder())
        .post(getFullPathVariable(format(TIMELINE_EVENTS_PATH, this.resume.getId())))
        .then()
        .statusCode(UNAUTHORIZED.value());
  }

  @Test
  public void create_resumeNotFound() {
    final var resumeId = randomUUID();

    final ProblemDetail problemDetail =
        givenAuthenticated()
            .contentType(JSON)
            .body(timelineEventCreateOrder())
            .post(getFullPathVariable(format(TIMELINE_EVENTS_PATH, resumeId)))
            .then()
            .statusCode(NOT_FOUND.value())
            .extract()
            .as(ProblemDetail.class);

    assertThat(problemDetail.getTitle()).isEqualTo("Resume not found");
    assertThat(problemDetail.getDetail())
        .isEqualTo(format("Resume with id %s not found", resumeId));
  }

  @Test
  public void update_success() {
    final var existingTimelineEvent = this.timelineEventRepository.save(timelineEvent(this.resume));
    final var timelineEventUpdateOrder = timelineEventUpdateOrder();

    final TimelineEventView updatedTimelineEvent =
        givenAuthenticated()
            .header(
                IF_MODIFIED_SINCE,
                ofPattern(ISO_DATETIME_FORMAT).format(existingTimelineEvent.getLastModifiedAt()))
            .contentType(JSON)
            .body(timelineEventUpdateOrder)
            .put(
                getFullPathVariable(
                    format(
                        TIMELINE_EVENT_PATH, this.resume.getId(), existingTimelineEvent.getId())))
            .then()
            .statusCode(OK.value())
            .extract()
            .as(TimelineEventView.class);

    assertThat(updatedTimelineEvent)
        .extracting(
            TimelineEventView::getJobPosition,
            TimelineEventView::getInstitution,
            TimelineEventView::getDescription,
            TimelineEventView::getDateOfEvent)
        .containsExactly(
            timelineEventUpdateOrder.getJobPosition(),
            timelineEventUpdateOrder.getInstitution(),
            timelineEventUpdateOrder.getDescription(),
            timelineEventUpdateOrder.getDateOfEvent());
  }

  @Test
  public void update_resumeNotFound() {
    final var resumeId = randomUUID();

    final ProblemDetail problemDetail =
        givenAuthenticated()
            .header(IF_MODIFIED_SINCE, ofPattern(ISO_DATETIME_FORMAT).format(now()))
            .contentType(JSON)
            .body(timelineEventUpdateOrder())
            .put(getFullPathVariable(format(TIMELINE_EVENT_PATH, resumeId, randomUUID())))
            .then()
            .statusCode(NOT_FOUND.value())
            .extract()
            .as(ProblemDetail.class);

    assertThat(problemDetail.getTitle()).isEqualTo("Resume not found");
    assertThat(problemDetail.getDetail())
        .isEqualTo(format("Resume with id %s not found", resumeId));
  }

  @Test
  public void update_skillNotFound() {
    final var skillId = randomUUID();

    final ProblemDetail problemDetail =
        givenAuthenticated()
            .header(IF_MODIFIED_SINCE, ofPattern(ISO_DATETIME_FORMAT).format(now()))
            .contentType(JSON)
            .body(timelineEventUpdateOrder())
            .put(getFullPathVariable(format(TIMELINE_EVENT_PATH, this.resume.getId(), skillId)))
            .then()
            .statusCode(NOT_FOUND.value())
            .extract()
            .as(ProblemDetail.class);

    assertThat(problemDetail.getTitle()).isEqualTo("Timeline event not found");
    assertThat(problemDetail.getDetail())
        .isEqualTo(format("Timeline event with id %s not found", skillId));
  }

  @Test
  public void update_unauthorized() {
    given()
        .header(IF_MODIFIED_SINCE, ofPattern(ISO_DATETIME_FORMAT).format(now()))
        .contentType(JSON)
        .body(timelineEventUpdateOrder())
        .put(getFullPathVariable(format(TIMELINE_EVENT_PATH, this.resume.getId(), randomUUID())))
        .then()
        .statusCode(UNAUTHORIZED.value());
  }

  @Test
  public void update_modifiedSince() {
    final var existingTimelineEvent = this.timelineEventRepository.save(timelineEvent(this.resume));

    givenAuthenticated()
        .header(
            IF_MODIFIED_SINCE,
            ofPattern(ISO_DATETIME_FORMAT).format(existingTimelineEvent.getLastModifiedAt()))
        .contentType(JSON)
        .body(timelineEventUpdateOrder())
        .put(
            getFullPathVariable(
                format(TIMELINE_EVENT_PATH, this.resume.getId(), existingTimelineEvent.getId())))
        .then()
        .statusCode(OK.value());

    final ProblemDetail problemDetail =
        givenAuthenticated()
            .header(
                IF_MODIFIED_SINCE,
                ofPattern(ISO_DATETIME_FORMAT).format(existingTimelineEvent.getLastModifiedAt()))
            .contentType(JSON)
            .body(timelineEventUpdateOrder())
            .put(
                getFullPathVariable(
                    format(
                        TIMELINE_EVENT_PATH, this.resume.getId(), existingTimelineEvent.getId())))
            .then()
            .statusCode(PRECONDITION_FAILED.value())
            .extract()
            .as(ProblemDetail.class);

    assertThat(problemDetail.getTitle()).isEqualTo("Entity has been modified");
  }

  @Test
  public void delete_success() {
    final var existingTimelineEvent = this.timelineEventRepository.save(timelineEvent(this.resume));

    givenAuthenticated()
        .header(
            IF_MODIFIED_SINCE,
            ofPattern(ISO_DATETIME_FORMAT).format(existingTimelineEvent.getLastModifiedAt()))
        .contentType(JSON)
        .delete(
            getFullPathVariable(
                format(TIMELINE_EVENT_PATH, this.resume.getId(), existingTimelineEvent.getId())))
        .then()
        .statusCode(NO_CONTENT.value());

    final var deletedResume =
        this.timelineEventRepository.findById(existingTimelineEvent.getId()).orElseThrow();

    assertThat(deletedResume.getIsDeleted()).isTrue();
  }

  @Test
  public void delete_resumeNotFound() {
    final var resumeId = randomUUID();

    final ProblemDetail problemDetail =
        givenAuthenticated()
            .header(IF_MODIFIED_SINCE, ofPattern(ISO_DATETIME_FORMAT).format(now()))
            .contentType(JSON)
            .delete(getFullPathVariable(format(TIMELINE_EVENT_PATH, resumeId, randomUUID())))
            .then()
            .statusCode(NOT_FOUND.value())
            .extract()
            .as(ProblemDetail.class);

    assertThat(problemDetail.getTitle()).isEqualTo("Resume not found");
    assertThat(problemDetail.getDetail())
        .isEqualTo(format("Resume with id %s not found", resumeId));
  }

  @Test
  public void delete_unauthorized() {
    given()
        .header(IF_MODIFIED_SINCE, ofPattern(ISO_DATETIME_FORMAT).format(now()))
        .contentType(JSON)
        .delete(getFullPathVariable(format(TIMELINE_EVENT_PATH, randomUUID(), randomUUID())))
        .then()
        .statusCode(UNAUTHORIZED.value());
  }

  @Test
  public void delete_modifiedSince() {
    final var existingTimelineEvent = this.timelineEventRepository.save(timelineEvent(this.resume));

    givenAuthenticated()
        .header(
            IF_MODIFIED_SINCE,
            ofPattern(ISO_DATETIME_FORMAT).format(existingTimelineEvent.getLastModifiedAt()))
        .contentType(JSON)
        .body(timelineEventUpdateOrder())
        .put(
            getFullPathVariable(
                format(TIMELINE_EVENT_PATH, this.resume.getId(), existingTimelineEvent.getId())))
        .then()
        .statusCode(OK.value());

    final ProblemDetail problemDetail =
        givenAuthenticated()
            .header(
                IF_MODIFIED_SINCE,
                ofPattern(ISO_DATETIME_FORMAT).format(existingTimelineEvent.getLastModifiedAt()))
            .contentType(JSON)
            .delete(
                getFullPathVariable(
                    format(
                        TIMELINE_EVENT_PATH, this.resume.getId(), existingTimelineEvent.getId())))
            .then()
            .statusCode(PRECONDITION_FAILED.value())
            .extract()
            .as(ProblemDetail.class);

    assertThat(problemDetail.getTitle()).isEqualTo("Entity has been modified");
  }
}
