package de.fynnkoch;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static java.lang.String.format;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class AbstractIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private DataBaseCleanerService dataBaseCleanerService;

    @BeforeEach
    protected void setup() {
        dataBaseCleanerService.resetAllTables();
    }

    public String getFullPathVariable(String path) {
        return format("http://localhost:%s%s", this.port, path);
    }
}
