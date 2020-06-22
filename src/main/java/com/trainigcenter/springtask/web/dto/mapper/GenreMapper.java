package com.trainigcenter.springtask.web.dto.mapper;

import com.trainigcenter.springtask.domain.Genre;
import com.trainigcenter.springtask.web.dto.GenreDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenreMapper {

    GenreDto toDto(Genre genre);

    Genre fromDto(GenreDto genreDto);

}
