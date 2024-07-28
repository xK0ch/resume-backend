package de.fynnkoch;

import de.fynnkoch.modules.contactinformation.ContactInformationRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@FieldDefaults(level = PRIVATE, makeFinal = true)
class DataBaseCleanerService {

    ContactInformationRepository contactInformationRepository;

    void resetAllTables() {
        contactInformationRepository.deleteAllInBatch();
    }
}
