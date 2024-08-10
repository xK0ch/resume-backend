package de.fynnkoch.modules.skill;

import static de.fynnkoch.core.Constants.ISO_DATETIME_FORMAT;
import static de.fynnkoch.modules.resume.ResumeFactory.resume;
import static de.fynnkoch.modules.skill.SkillFactory.skill;
import static de.fynnkoch.modules.skill.SkillFactory.skillCreateOrder;
import static de.fynnkoch.modules.skill.SkillFactory.skillUpdateOrder;
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

import de.fynnkoch.AbstractIntegrationTest;
import de.fynnkoch.modules.resume.Resume;
import de.fynnkoch.modules.resume.ResumeRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ProblemDetail;

public class SkillControllerIntegrationTest extends AbstractIntegrationTest {

  private static final String SKILL_PATH = "/resumes/%s/skills/%s";
  private static final String SKILLS_PATH = "/resumes/%s/skills";

  @Autowired private SkillRepository skillRepository;
  @Autowired private ResumeRepository resumeRepository;

  private Resume resume;

  @BeforeEach
  protected void before() {
    this.resume = resumeRepository.save(resume());
  }

  @Test
  public void getAllByResume_success() {
    this.skillRepository.save(skill(this.resume));

    final List<SkillView> skill =
        given()
            .contentType(JSON)
            .get(getFullPathVariable(format(SKILLS_PATH, this.resume.getId())))
            .then()
            .statusCode(OK.value())
            .extract()
            .jsonPath()
            .getList("", SkillView.class);

    assertThat(skill).hasSize(1);
    assertThat(skill.getFirst())
        .extracting(SkillView::getName, SkillView::getSkillLevel)
        .containsExactly(skill().getName(), skill().getSkillLevel());
  }

  @Test
  public void getAllByResume_noResume() {
    this.skillRepository.save(skill(this.resume));

    final List<SkillView> skill =
        given()
            .contentType(JSON)
            .get(getFullPathVariable(format(SKILLS_PATH, randomUUID())))
            .then()
            .statusCode(OK.value())
            .extract()
            .jsonPath()
            .getList("", SkillView.class);

    assertThat(skill).isEmpty();
  }

  @Test
  public void getAllByResume_noSkill() {
    final List<SkillView> skill =
        given()
            .contentType(JSON)
            .get(getFullPathVariable(format(SKILLS_PATH, this.resume.getId())))
            .then()
            .statusCode(OK.value())
            .extract()
            .jsonPath()
            .getList("", SkillView.class);

    assertThat(skill).isEmpty();
  }

  @Test
  public void create_success() {
    final var skillCreateOrder = skillCreateOrder();

    final SkillView createdSkill =
        given()
            .contentType(JSON)
            .body(skillCreateOrder)
            .post(getFullPathVariable(format(SKILLS_PATH, this.resume.getId())))
            .then()
            .statusCode(CREATED.value())
            .extract()
            .as(SkillView.class);

    assertThat(this.resumeRepository.findAll()).hasSize(1);

    assertThat(createdSkill)
        .extracting(SkillView::getName, SkillView::getSkillLevel)
        .containsExactly(skillCreateOrder.getName(), skillCreateOrder.getSkillLevel());
  }

  @Test
  public void create_resumeNotFound() {
    final var resumeId = randomUUID();

    final ProblemDetail problemDetail =
        given()
            .contentType(JSON)
            .body(skillCreateOrder())
            .post(getFullPathVariable(format(SKILLS_PATH, resumeId)))
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
    final var existingSkill = this.skillRepository.save(skill(this.resume));
    final var skillUpdateOrder = skillUpdateOrder();

    final SkillView updatedSkill =
        given()
            .header(
                IF_MODIFIED_SINCE,
                ofPattern(ISO_DATETIME_FORMAT).format(existingSkill.getLastModifiedAt()))
            .contentType(JSON)
            .body(skillUpdateOrder)
            .put(
                getFullPathVariable(format(SKILL_PATH, this.resume.getId(), existingSkill.getId())))
            .then()
            .statusCode(OK.value())
            .extract()
            .as(SkillView.class);

    assertThat(updatedSkill)
        .extracting(SkillView::getName, SkillView::getSkillLevel)
        .containsExactly(skillUpdateOrder.getName(), skillUpdateOrder.getSkillLevel());
  }

  @Test
  public void update_resumeNotFound() {
    final var resumeId = randomUUID();

    final ProblemDetail problemDetail =
        given()
            .header(IF_MODIFIED_SINCE, ofPattern(ISO_DATETIME_FORMAT).format(now()))
            .contentType(JSON)
            .body(skillUpdateOrder())
            .put(getFullPathVariable(format(SKILL_PATH, resumeId, randomUUID())))
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
        given()
            .header(IF_MODIFIED_SINCE, ofPattern(ISO_DATETIME_FORMAT).format(now()))
            .contentType(JSON)
            .body(skillUpdateOrder())
            .put(getFullPathVariable(format(SKILL_PATH, this.resume.getId(), skillId)))
            .then()
            .statusCode(NOT_FOUND.value())
            .extract()
            .as(ProblemDetail.class);

    assertThat(problemDetail.getTitle()).isEqualTo("Skill not found");
    assertThat(problemDetail.getDetail()).isEqualTo(format("Skill with id %s not found", skillId));
  }

  @Test
  public void update_modifiedSince() {
    final var existingSkill = this.skillRepository.save(skill(this.resume));

    given()
        .header(
            IF_MODIFIED_SINCE,
            ofPattern(ISO_DATETIME_FORMAT).format(existingSkill.getLastModifiedAt()))
        .contentType(JSON)
        .body(skillUpdateOrder())
        .put(getFullPathVariable(format(SKILL_PATH, this.resume.getId(), existingSkill.getId())))
        .then()
        .statusCode(OK.value());

    final ProblemDetail problemDetail =
        given()
            .header(
                IF_MODIFIED_SINCE,
                ofPattern(ISO_DATETIME_FORMAT).format(existingSkill.getLastModifiedAt()))
            .contentType(JSON)
            .body(skillUpdateOrder())
            .put(
                getFullPathVariable(format(SKILL_PATH, this.resume.getId(), existingSkill.getId())))
            .then()
            .statusCode(PRECONDITION_FAILED.value())
            .extract()
            .as(ProblemDetail.class);

    assertThat(problemDetail.getTitle()).isEqualTo("Entity has been modified");
  }

  @Test
  public void delete_success() {
    final var existingSkill = this.skillRepository.save(skill(this.resume));

    given()
        .header(
            IF_MODIFIED_SINCE,
            ofPattern(ISO_DATETIME_FORMAT).format(existingSkill.getLastModifiedAt()))
        .contentType(JSON)
        .delete(getFullPathVariable(format(SKILL_PATH, this.resume.getId(), existingSkill.getId())))
        .then()
        .statusCode(NO_CONTENT.value());

    final var deletedResume = this.skillRepository.findById(existingSkill.getId()).orElseThrow();

    assertThat(deletedResume.getIsDeleted()).isTrue();
  }

  @Test
  public void delete_resumeNotFound() {
    final var resumeId = randomUUID();

    final ProblemDetail problemDetail =
        given()
            .header(IF_MODIFIED_SINCE, ofPattern(ISO_DATETIME_FORMAT).format(now()))
            .contentType(JSON)
            .body(skillUpdateOrder())
            .delete(getFullPathVariable(format(SKILL_PATH, resumeId, randomUUID())))
            .then()
            .statusCode(NOT_FOUND.value())
            .extract()
            .as(ProblemDetail.class);

    assertThat(problemDetail.getTitle()).isEqualTo("Resume not found");
    assertThat(problemDetail.getDetail())
        .isEqualTo(format("Resume with id %s not found", resumeId));
  }

  @Test
  public void delete_modifiedSince() {
    final var existingSkill = this.skillRepository.save(skill(this.resume));

    given()
        .header(
            IF_MODIFIED_SINCE,
            ofPattern(ISO_DATETIME_FORMAT).format(existingSkill.getLastModifiedAt()))
        .contentType(JSON)
        .body(skillUpdateOrder())
        .put(getFullPathVariable(format(SKILL_PATH, this.resume.getId(), existingSkill.getId())))
        .then()
        .statusCode(OK.value());

    final ProblemDetail problemDetail =
        given()
            .header(
                IF_MODIFIED_SINCE,
                ofPattern(ISO_DATETIME_FORMAT).format(existingSkill.getLastModifiedAt()))
            .contentType(JSON)
            .body(skillUpdateOrder())
            .delete(
                getFullPathVariable(format(SKILL_PATH, this.resume.getId(), existingSkill.getId())))
            .then()
            .statusCode(PRECONDITION_FAILED.value())
            .extract()
            .as(ProblemDetail.class);

    assertThat(problemDetail.getTitle()).isEqualTo("Entity has been modified");
  }
}
