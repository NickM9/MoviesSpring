package com.trainigcenter.springtask.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.Set;

@Data
@NoArgsConstructor
public class MovieDto {

    private String title;
    private String description;
    private Duration duration;
    private Set<GenreDto> genres;
    private Set<ActorDto> actors;

}
