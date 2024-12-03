package de.fynnkoch.modules.timelineevent;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimelineEventRepository extends JpaRepository<TimelineEvent, UUID> {

  List<TimelineEvent> findAllByResumeIdAndIsDeletedIsFalse(UUID resumeId);

  Optional<TimelineEvent> findByIdAndIsDeletedIsFalse(UUID id);
}
