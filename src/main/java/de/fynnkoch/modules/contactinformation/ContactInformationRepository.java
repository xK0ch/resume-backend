package de.fynnkoch.modules.contactinformation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ContactInformationRepository extends JpaRepository<ContactInformation, UUID> {
}
