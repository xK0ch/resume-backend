package de.fynnkoch.modules.skill;

import de.fynnkoch.modules.resume.Resume;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface SkillMapper {

  SkillView toView(Skill skill);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "lastModifiedAt", ignore = true)
  @Mapping(target = "isDeleted", constant = "false")
  Skill fromCreateOrder(SkillCreateOrder skillCreateOrder, Resume resume);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "lastModifiedAt", ignore = true)
  @Mapping(target = "isDeleted", ignore = true)
  @Mapping(target = "resume", ignore = true)
  Skill fromUpdateOrder(@MappingTarget Skill skill, SkillUpdateOrder skillUpdateOrder);
}
