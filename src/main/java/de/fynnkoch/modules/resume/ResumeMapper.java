package de.fynnkoch.modules.resume;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ResumeMapper {

  ResumeView toView(Resume resume);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "lastModifiedAt", ignore = true)
  @Mapping(target = "isDeleted", constant = "false")
  @Mapping(target = "status", constant = "INACTIVE")
  Resume fromCreateOrder(ResumeCreateOrder resumeCreateOrder);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "lastModifiedAt", ignore = true)
  @Mapping(target = "isDeleted", ignore = true)
  @Mapping(target = "status", ignore = true)
  Resume fromUpdateOrder(@MappingTarget Resume resume, ResumeUpdateOrder resumeUpdateOrder);
}
