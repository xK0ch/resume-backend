package de.fynnkoch.modules.contactinformation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContactInformationService {

    private final ContactInformationRepository contactInformationRepository;

    public ContactInformation getContactInformation(UUID id) {
        return contactInformationRepository.findById(id).orElseThrow();
    }

    public ContactInformation createContactInformation(ContactInformationCreateOrder contactInformationCreateOrder) {
        ContactInformation contactInformation = ContactInformation.builder()
                .firstName(contactInformationCreateOrder.getFirstName())
                .lastName(contactInformationCreateOrder.getLastName())
                .build();
        return contactInformationRepository.save(contactInformation);
    }

    public ContactInformation updateContactInformation(UUID id, ContactInformation contactInformation) {
        ContactInformation existingContactInformation = contactInformationRepository.findById(id).orElseThrow();
        existingContactInformation.setFirstName(contactInformation.getFirstName());
        existingContactInformation.setLastName(contactInformation.getLastName());
        return contactInformationRepository.save(existingContactInformation);
    }

    public void deleteContactInformation(UUID id) {
        contactInformationRepository.deleteById(id);
    }
}
