package de.fynnkoch;

import static java.lang.String.format;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class AbstractIntegrationTest {

  @LocalServerPort private int port;

  @Autowired private DataBaseCleanerService dataBaseCleanerService;

  @BeforeEach
  protected void setup() {
    dataBaseCleanerService.resetAllTables();
  }

  public String getFullPathVariable(final String path) {
    return format("http://localhost:%s%s", this.port, path);
  }
}
