package de.fynnkoch.modules.skill;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends JpaRepository<Skill, UUID> {

  List<Skill> findAllByResumeIdAndIsDeletedIsFalse(UUID resumeId);

  Optional<Skill> findByIdAndIsDeletedIsFalse(UUID id);
}
