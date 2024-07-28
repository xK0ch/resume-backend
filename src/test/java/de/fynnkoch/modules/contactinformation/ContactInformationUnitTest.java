package de.fynnkoch.modules.contactinformation;

import de.fynnkoch.AbstractUnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;

import static de.fynnkoch.modules.contactinformation.ContactInformationFactory.contactInformation;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ContactInformationUnitTest implements AbstractUnitTest {

    @Mock
    private ContactInformationRepository contactInformationRepository;

    private ContactInformationService contactInformationService;

    @BeforeEach
    public void setup() {
        contactInformationService = new ContactInformationService(contactInformationRepository);
    }

    @Test
    void getContactInformation() {
        when(contactInformationRepository.findById(any())).thenReturn(Optional.of(contactInformation()));
        final ContactInformation contactInformation = contactInformationService.getContactInformation(randomUUID());
        assertThat(contactInformation).isNotNull();
    }
}
