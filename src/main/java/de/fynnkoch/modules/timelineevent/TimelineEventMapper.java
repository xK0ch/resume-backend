package de.fynnkoch.modules.timelineevent;

import de.fynnkoch.modules.resume.Resume;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TimelineEventMapper {

  TimelineEventView toView(TimelineEvent timelineEvent);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "lastModifiedAt", ignore = true)
  @Mapping(target = "isDeleted", constant = "false")
  @Mapping(target = "description", source = "timelineEventCreateOrder.description")
  TimelineEvent fromCreateOrder(TimelineEventCreateOrder timelineEventCreateOrder, Resume resume);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "lastModifiedAt", ignore = true)
  @Mapping(target = "isDeleted", ignore = true)
  @Mapping(target = "resume", ignore = true)
  TimelineEvent fromUpdateOrder(
      @MappingTarget TimelineEvent timelineEvent,
      TimelineEventUpdateOrder timelineEventUpdateOrder);
}
