package de.fynnkoch.modules.contactinformation;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contact-information")
public class ContactInformationController {

    private final ContactInformationService contactInformationService;

    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public ContactInformation getContactInformation(@PathVariable UUID id) {
        return contactInformationService.getContactInformation(id);
    }

    @Transactional
    @PostMapping
    public ContactInformation createContactInformation(@RequestBody ContactInformationCreateOrder contactInformation) {
        return contactInformationService.createContactInformation(contactInformation);
    }

    @Transactional
    @PutMapping("/{id}")
    public ContactInformation updateContactInformation(@PathVariable UUID id, @RequestBody ContactInformation contactInformation) {
        return contactInformationService.updateContactInformation(id, contactInformation);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public void deleteContactInformation(@PathVariable UUID id) {
        contactInformationService.deleteContactInformation(id);
    }
}
