package de.fynnkoch.modules.resume;

import static de.fynnkoch.modules.resume.ResumeFactory.resume;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;

import de.fynnkoch.AbstractIntegrationTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ResumeControllerIntegrationTest extends AbstractIntegrationTest {

    private static final String RESUME_PATH = "/resumes";

    @Autowired
    private ResumeRepository resumeRepository;

    @Test
    public void getAlResumes() {

        resumeRepository.save(resume());

        final List<ResumeView> resume =
                given().contentType(JSON)
                        .get(getFullPathVariable(RESUME_PATH))
                        .then()
                        .statusCode(OK.value())
                        .extract()
                        .jsonPath()
                        .getList("",ResumeView.class);

        assertThat(resume).hasSize(1);
        assertThat(resume.getFirst()).extracting(
                ResumeView::getFirstName,
                ResumeView::getLastName
        ).containsExactly("Tom", "Smith");
    }
}
