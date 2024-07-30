package de.fynnkoch.modules.resume;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface ResumeMapper {

    ResumeView toView(Resume resume);
}
