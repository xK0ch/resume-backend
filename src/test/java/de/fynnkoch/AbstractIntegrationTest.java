package de.fynnkoch;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static java.lang.String.format;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class AbstractIntegrationTest {

  @Value("${login.username}")
  private String username;

  @Value("${login.password}")
  private String password;

  @LocalServerPort private int port;

  @Autowired private DataBaseCleanerService dataBaseCleanerService;

  @BeforeEach
  protected void setup() {
    dataBaseCleanerService.resetAllTables();
  }

  protected String getFullPathVariable(final String path) {
    return format("http://localhost:%s%s", this.port, path);
  }

  protected RequestSpecification givenAuthenticated() {
    return given().contentType(JSON).auth().preemptive().basic(username, password);
  }
}
