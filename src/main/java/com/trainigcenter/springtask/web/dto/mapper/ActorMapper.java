package com.trainigcenter.springtask.web.dto.mapper;

import com.trainigcenter.springtask.domain.Actor;
import com.trainigcenter.springtask.web.dto.ActorDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActorMapper {

    ActorDto toDto(Actor actor);

    Actor fromDto(ActorDto actorDto);

}
