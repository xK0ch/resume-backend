package de.fynnkoch.modules.contactinformation;

import de.fynnkoch.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static de.fynnkoch.modules.contactinformation.ContactInformationFactory.contactInformation;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;

public class ContactInformationControllerIntegrationTest extends AbstractIntegrationTest {

    private static final String CONTACT_INFORMATION_PATH = "/contact-information";

    @Autowired
    private ContactInformationRepository contactInformationRepository;

    @Test
    public void getContactInformation() {
        final ContactInformation savedContactInformation = contactInformation();

        contactInformationRepository.save(savedContactInformation);

        final ContactInformation contactInformation =
                given().contentType(JSON)
                        .get(getFullPathVariable(format("%s/%s", CONTACT_INFORMATION_PATH, savedContactInformation.getId())))
                        .then()
                        .statusCode(OK.value())
                        .extract()
                        .as(ContactInformation.class);

        assertThat(contactInformation.getFirstName()).isEqualTo(savedContactInformation.getFirstName());
        assertThat(contactInformation.getLastName()).isEqualTo(savedContactInformation.getLastName());
        assertThat(contactInformation)
                .extracting(
                        ContactInformation::getId,
                        ContactInformation::getCreatedAt,
                        ContactInformation::getLastModifiedAt
                ).isNotNull();
    }
}
