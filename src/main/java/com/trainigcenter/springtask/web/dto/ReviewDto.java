package com.trainigcenter.springtask.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ReviewDto {

    private Integer id;

    @NotNull
    private MovieDto movie;

    @NotBlank
    private String authorName;

    @NotBlank
    private String title;

    @NotBlank
    private String text;

    @Min(1)
    @Max(10)
    private double rating;

}
