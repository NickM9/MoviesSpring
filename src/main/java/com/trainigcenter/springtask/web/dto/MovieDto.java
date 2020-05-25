package com.trainigcenter.springtask.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.trainigcenter.springtask.domain.Review;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class MovieDto {

    private Integer id;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    @JsonFormat(shape = Shape.STRING)
    private Duration duration;

    @NotNull
    private List<GenreDto> genres;

    @NotNull
    private List<ActorDto> actors;

}
