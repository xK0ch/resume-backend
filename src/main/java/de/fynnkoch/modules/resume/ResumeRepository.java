package de.fynnkoch.modules.resume;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, UUID> {

  List<Resume> findAllByIsDeletedIsFalse();

  Optional<Resume> findByIdAndIsDeletedIsFalse(UUID id);

  Optional<Resume> findByStatusAndIsDeletedIsFalse(Status status);
}
