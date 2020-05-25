package com.trainigcenter.springtask.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.trainigcenter.springtask.domain.Review;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class MovieDto {

    private Integer id;

    @NotBlank
    @Size(min = 1, max = 140)
    private String title;

    @NotBlank
    @Size(min = 1, max = 500)
    private String description;

    @NotNull
    @JsonFormat(shape = Shape.STRING)
    private Duration duration;

    @NotNull
    private List<GenreDto> genres;

    @NotNull
    private List<ActorDto> actors;

}
