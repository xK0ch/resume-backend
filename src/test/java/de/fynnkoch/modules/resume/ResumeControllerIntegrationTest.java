package de.fynnkoch.modules.resume;

import static de.fynnkoch.core.Constants.ISO_DATETIME_FORMAT;
import static de.fynnkoch.modules.resume.ResumeFactory.resume;
import static de.fynnkoch.modules.resume.ResumeFactory.resumeCreateOrder;
import static de.fynnkoch.modules.resume.ResumeFactory.resumeUpdateOrder;
import static de.fynnkoch.modules.resume.Status.ACTIVE;
import static de.fynnkoch.modules.resume.Status.INACTIVE;
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
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ProblemDetail;

public class ResumeControllerIntegrationTest extends AbstractIntegrationTest {

  private static final String RESUME_PATH = "/resumes/%s";
  private static final String RESUMES_PATH = "/resumes";

  @Autowired private ResumeRepository resumeRepository;

  @Test
  public void getAll_withResults() {
    resumeRepository.save(resume());

    final List<ResumeView> resume =
        given()
            .contentType(JSON)
            .get(getFullPathVariable(RESUMES_PATH))
            .then()
            .statusCode(OK.value())
            .extract()
            .jsonPath()
            .getList("", ResumeView.class);

    assertThat(resume).hasSize(1);
    assertThat(resume.getFirst())
        .extracting(
            ResumeView::getFirstName,
            ResumeView::getLastName,
            ResumeView::getSex,
            ResumeView::getDateOfBirth,
            ResumeView::getAddress,
            ResumeView::getPostalCode,
            ResumeView::getCity,
            ResumeView::getCountry,
            ResumeView::getPhoneNumber,
            ResumeView::getEmail,
            ResumeView::getDescription,
            ResumeView::getLinkedin,
            ResumeView::getGithub,
            ResumeView::getStatus)
        .containsExactly(
            resume().getFirstName(),
            resume().getLastName(),
            resume().getSex(),
            resume().getDateOfBirth(),
            resume().getAddress(),
            resume().getPostalCode(),
            resume().getCity(),
            resume().getCountry(),
            resume().getPhoneNumber(),
            resume().getEmail(),
            resume().getDescription(),
            resume().getLinkedin(),
            resume().getGithub(),
            resume().getStatus());
  }

  @Test
  public void getAll_withoutResults() {
    final List<ResumeView> resume =
        given()
            .contentType(JSON)
            .get(getFullPathVariable(RESUMES_PATH))
            .then()
            .statusCode(OK.value())
            .extract()
            .jsonPath()
            .getList("", ResumeView.class);

    assertThat(resume).isEmpty();
  }

  @Test
  public void getOne_success() {
    final Resume savedResume = resumeRepository.save(resume());

    final ResumeView resume =
        given()
            .contentType(JSON)
            .get(getFullPathVariable(format(RESUME_PATH, savedResume.getId())))
            .then()
            .statusCode(OK.value())
            .extract()
            .as(ResumeView.class);

    assertThat(resume)
        .extracting(
            ResumeView::getFirstName,
            ResumeView::getLastName,
            ResumeView::getSex,
            ResumeView::getDateOfBirth,
            ResumeView::getAddress,
            ResumeView::getPostalCode,
            ResumeView::getCity,
            ResumeView::getCountry,
            ResumeView::getPhoneNumber,
            ResumeView::getEmail,
            ResumeView::getDescription,
            ResumeView::getLinkedin,
            ResumeView::getGithub,
            ResumeView::getStatus)
        .containsExactly(
            resume().getFirstName(),
            resume().getLastName(),
            resume().getSex(),
            resume().getDateOfBirth(),
            resume().getAddress(),
            resume().getPostalCode(),
            resume().getCity(),
            resume().getCountry(),
            resume().getPhoneNumber(),
            resume().getEmail(),
            resume().getDescription(),
            resume().getLinkedin(),
            resume().getGithub(),
            resume().getStatus());
  }

  @Test
  public void getOne_resumeNotFound() {
    final var resumeId = randomUUID();

    final ProblemDetail problemDetail =
        given()
            .contentType(JSON)
            .get(getFullPathVariable(format(RESUME_PATH, resumeId)))
            .then()
            .statusCode(NOT_FOUND.value())
            .extract()
            .as(ProblemDetail.class);

    assertThat(problemDetail.getTitle()).isEqualTo("Resume not found");
    assertThat(problemDetail.getDetail())
        .isEqualTo(format("Resume with id %s not found", resumeId));
  }

  @Test
  public void create_success() {
    final ResumeCreateOrder resumeCreateOrder = resumeCreateOrder();

    assertThat(this.resumeRepository.findAll()).isEmpty();

    final ResumeView createdResume =
        givenAuthenticated()
            .contentType(JSON)
            .body(resumeCreateOrder)
            .post(getFullPathVariable(RESUMES_PATH))
            .then()
            .statusCode(CREATED.value())
            .extract()
            .as(ResumeView.class);

    assertThat(this.resumeRepository.findAll()).hasSize(1);

    assertThat(createdResume)
        .extracting(
            ResumeView::getFirstName,
            ResumeView::getLastName,
            ResumeView::getSex,
            ResumeView::getDateOfBirth,
            ResumeView::getAddress,
            ResumeView::getPostalCode,
            ResumeView::getCity,
            ResumeView::getCountry,
            ResumeView::getPhoneNumber,
            ResumeView::getMobileNumber,
            ResumeView::getEmail,
            ResumeView::getDescription,
            ResumeView::getLinkedin,
            ResumeView::getGithub,
            ResumeView::getStatus)
        .containsExactly(
            resumeCreateOrder.getFirstName(),
            resumeCreateOrder.getLastName(),
            resumeCreateOrder.getSex(),
            resumeCreateOrder.getDateOfBirth(),
            resumeCreateOrder.getAddress(),
            resumeCreateOrder.getPostalCode(),
            resumeCreateOrder.getCity(),
            resumeCreateOrder.getCountry(),
            resumeCreateOrder.getPhoneNumber(),
            resumeCreateOrder.getMobileNumber(),
            resumeCreateOrder.getEmail(),
            resumeCreateOrder.getDescription(),
            resumeCreateOrder.getLinkedin(),
            resumeCreateOrder.getGithub(),
            INACTIVE);
  }

  @Test
  public void create_unauthorized() {
    given()
        .contentType(JSON)
        .body(resumeCreateOrder())
        .post(getFullPathVariable(RESUMES_PATH))
        .then()
        .statusCode(UNAUTHORIZED.value());
  }

  @Test
  public void update_success() {
    final var savedResume = resumeRepository.save(resume());

    final var resumeUpdateOrder = resumeUpdateOrder();

    final ResumeView updatedResume =
        givenAuthenticated()
            .header(
                IF_MODIFIED_SINCE,
                ofPattern(ISO_DATETIME_FORMAT).format(savedResume.getLastModifiedAt()))
            .contentType(JSON)
            .body(resumeUpdateOrder)
            .put(getFullPathVariable(format(RESUME_PATH, savedResume.getId())))
            .then()
            .statusCode(OK.value())
            .extract()
            .as(ResumeView.class);

    assertThat(updatedResume)
        .extracting(
            ResumeView::getFirstName,
            ResumeView::getLastName,
            ResumeView::getSex,
            ResumeView::getDateOfBirth,
            ResumeView::getAddress,
            ResumeView::getPostalCode,
            ResumeView::getCity,
            ResumeView::getCountry,
            ResumeView::getPhoneNumber,
            ResumeView::getMobileNumber,
            ResumeView::getEmail,
            ResumeView::getDescription,
            ResumeView::getLinkedin,
            ResumeView::getGithub,
            ResumeView::getStatus)
        .containsExactly(
            resumeUpdateOrder.getFirstName(),
            resumeUpdateOrder.getLastName(),
            resumeUpdateOrder.getSex(),
            resumeUpdateOrder.getDateOfBirth(),
            resumeUpdateOrder.getAddress(),
            resumeUpdateOrder.getPostalCode(),
            resumeUpdateOrder.getCity(),
            resumeUpdateOrder.getCountry(),
            resumeUpdateOrder.getPhoneNumber(),
            resumeUpdateOrder.getMobileNumber(),
            resumeUpdateOrder.getEmail(),
            resumeUpdateOrder.getDescription(),
            resumeUpdateOrder.getLinkedin(),
            resumeUpdateOrder.getGithub(),
            INACTIVE);
  }

  @Test
  public void update_resumeNotFound() {
    final var resumeId = randomUUID();

    final ProblemDetail problemDetail =
        givenAuthenticated()
            .contentType(JSON)
            .header(IF_MODIFIED_SINCE, ofPattern(ISO_DATETIME_FORMAT).format(now()))
            .body(resumeUpdateOrder())
            .put(getFullPathVariable(format(RESUME_PATH, resumeId)))
            .then()
            .statusCode(NOT_FOUND.value())
            .extract()
            .as(ProblemDetail.class);

    assertThat(problemDetail.getTitle()).isEqualTo("Resume not found");
    assertThat(problemDetail.getDetail())
        .isEqualTo(format("Resume with id %s not found", resumeId));
  }

  @Test
  public void update_unauthorized() {
    given()
        .contentType(JSON)
        .header(IF_MODIFIED_SINCE, ofPattern(ISO_DATETIME_FORMAT).format(now()))
        .body(resumeUpdateOrder())
        .put(getFullPathVariable(format(RESUME_PATH, randomUUID())))
        .then()
        .statusCode(UNAUTHORIZED.value());
  }

  @Test
  public void update_modifiedSince() {
    final var savedResume = resumeRepository.save(resume());

    givenAuthenticated()
        .contentType(JSON)
        .header(
            IF_MODIFIED_SINCE,
            ofPattern(ISO_DATETIME_FORMAT).format(savedResume.getLastModifiedAt()))
        .body(resumeUpdateOrder())
        .put(getFullPathVariable(format(RESUME_PATH, savedResume.getId())))
        .then()
        .statusCode(OK.value());

    final ProblemDetail problemDetail =
        givenAuthenticated()
            .contentType(JSON)
            .header(
                IF_MODIFIED_SINCE,
                ofPattern(ISO_DATETIME_FORMAT).format(savedResume.getLastModifiedAt()))
            .body(resumeUpdateOrder())
            .put(getFullPathVariable(format(RESUME_PATH, savedResume.getId())))
            .then()
            .statusCode(PRECONDITION_FAILED.value())
            .extract()
            .as(ProblemDetail.class);

    assertThat(problemDetail.getTitle()).isEqualTo("Entity has been modified");
  }

  @Test
  public void toggleStatus_successInactive() {
    final var savedResume = resumeRepository.save(resume());

    final ResumeView updatedResume =
        givenAuthenticated()
            .header(
                IF_MODIFIED_SINCE,
                ofPattern(ISO_DATETIME_FORMAT).format(savedResume.getLastModifiedAt()))
            .contentType(JSON)
            .patch(getFullPathVariable(format(RESUME_PATH, savedResume.getId())))
            .then()
            .statusCode(OK.value())
            .extract()
            .as(ResumeView.class);

    assertThat(updatedResume.getStatus()).isEqualTo(ACTIVE);
  }

  @Test
  public void toggleStatus_successActive() {
    final var savedResume = resumeRepository.save(resume(ACTIVE));

    final ResumeView updatedResume =
        givenAuthenticated()
            .header(
                IF_MODIFIED_SINCE,
                ofPattern(ISO_DATETIME_FORMAT).format(savedResume.getLastModifiedAt()))
            .contentType(JSON)
            .patch(getFullPathVariable(format(RESUME_PATH, savedResume.getId())))
            .then()
            .statusCode(OK.value())
            .extract()
            .as(ResumeView.class);

    assertThat(updatedResume.getStatus()).isEqualTo(INACTIVE);
  }

  @Test
  public void toggleStatus_successWithOtherActiveResume() {
    final var currentlyActiveResume = resumeRepository.save(resume(ACTIVE));
    final var currentlyInactiveResume = resumeRepository.save(resume());

    final ResumeView updatedResume =
        givenAuthenticated()
            .header(
                IF_MODIFIED_SINCE,
                ofPattern(ISO_DATETIME_FORMAT).format(currentlyInactiveResume.getLastModifiedAt()))
            .contentType(JSON)
            .patch(getFullPathVariable(format(RESUME_PATH, currentlyInactiveResume.getId())))
            .then()
            .statusCode(OK.value())
            .extract()
            .as(ResumeView.class);

    assertThat(
            this.resumeRepository.findById(currentlyActiveResume.getId()).orElseThrow().getStatus())
        .isEqualTo(INACTIVE);
    assertThat(updatedResume.getStatus()).isEqualTo(ACTIVE);
  }

  @Test
  public void toggleStatus_unauthorized() {
    given()
        .header(IF_MODIFIED_SINCE, ofPattern(ISO_DATETIME_FORMAT).format(now()))
        .contentType(JSON)
        .patch(getFullPathVariable(format(RESUME_PATH, randomUUID())))
        .then()
        .statusCode(UNAUTHORIZED.value());
  }

  @Test
  public void toggleStatus_resumeNotFound() {
    final var resumeId = randomUUID();

    final ProblemDetail problemDetail =
        givenAuthenticated()
            .header(IF_MODIFIED_SINCE, ofPattern(ISO_DATETIME_FORMAT).format(now()))
            .contentType(JSON)
            .patch(getFullPathVariable(format(RESUME_PATH, resumeId)))
            .then()
            .statusCode(NOT_FOUND.value())
            .extract()
            .as(ProblemDetail.class);

    assertThat(problemDetail.getTitle()).isEqualTo("Resume not found");
    assertThat(problemDetail.getDetail())
        .isEqualTo(format("Resume with id %s not found", resumeId));
  }

  @Test
  public void toggleStatus_modifiedSince() {
    final var savedResume = resumeRepository.save(resume());

    givenAuthenticated()
        .contentType(JSON)
        .header(
            IF_MODIFIED_SINCE,
            ofPattern(ISO_DATETIME_FORMAT).format(savedResume.getLastModifiedAt()))
        .body(resumeUpdateOrder())
        .put(getFullPathVariable(format(RESUME_PATH, savedResume.getId())))
        .then()
        .statusCode(OK.value());

    final ProblemDetail problemDetail =
        givenAuthenticated()
            .header(
                IF_MODIFIED_SINCE,
                ofPattern(ISO_DATETIME_FORMAT).format(savedResume.getLastModifiedAt()))
            .contentType(JSON)
            .patch(getFullPathVariable(format(RESUME_PATH, savedResume.getId())))
            .then()
            .statusCode(PRECONDITION_FAILED.value())
            .extract()
            .as(ProblemDetail.class);

    assertThat(problemDetail.getTitle()).isEqualTo("Entity has been modified");
  }

  @Test
  public void delete_success() {
    final var savedResume = resumeRepository.save(resume());

    givenAuthenticated()
        .contentType(JSON)
        .header(
            IF_MODIFIED_SINCE,
            ofPattern(ISO_DATETIME_FORMAT).format(savedResume.getLastModifiedAt()))
        .delete(getFullPathVariable(format(RESUME_PATH, savedResume.getId())))
        .then()
        .statusCode(NO_CONTENT.value());

    final var deletedResume = this.resumeRepository.findById(savedResume.getId()).orElseThrow();

    assertThat(deletedResume.getIsDeleted()).isTrue();
  }

  @Test
  public void delete_unauthorized() {
    given()
        .contentType(JSON)
        .header(IF_MODIFIED_SINCE, ofPattern(ISO_DATETIME_FORMAT).format(now()))
        .delete(getFullPathVariable(format(RESUME_PATH, randomUUID())))
        .then()
        .statusCode(UNAUTHORIZED.value());
  }

  @Test
  public void delete_modifiedSince() {
    final var savedResume = resumeRepository.save(resume());

    givenAuthenticated()
        .contentType(JSON)
        .header(
            IF_MODIFIED_SINCE,
            ofPattern(ISO_DATETIME_FORMAT).format(savedResume.getLastModifiedAt()))
        .body(resumeUpdateOrder())
        .put(getFullPathVariable(format(RESUME_PATH, savedResume.getId())))
        .then()
        .statusCode(OK.value());

    final ProblemDetail problemDetail =
        givenAuthenticated()
            .contentType(JSON)
            .header(
                IF_MODIFIED_SINCE,
                ofPattern(ISO_DATETIME_FORMAT).format(savedResume.getLastModifiedAt()))
            .delete(getFullPathVariable(format(RESUME_PATH, savedResume.getId())))
            .then()
            .statusCode(PRECONDITION_FAILED.value())
            .extract()
            .as(ProblemDetail.class);

    assertThat(problemDetail.getTitle()).isEqualTo("Entity has been modified");
  }
}
