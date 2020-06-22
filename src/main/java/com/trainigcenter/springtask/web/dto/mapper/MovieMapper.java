package com.trainigcenter.springtask.web.dto.mapper;

import com.trainigcenter.springtask.domain.Movie;
import com.trainigcenter.springtask.web.dto.MovieDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ActorMapper.class, GenreMapper.class})
public interface MovieMapper {

    MovieDto toDto(Movie movie);

    Movie fromDto(MovieDto movieDto);

}
