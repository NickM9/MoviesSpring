package com.trainigcenter.springtask.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class ReviewDto {

    private Integer id;

    @NotBlank
    @Size(min = 1, max = 45)
    private String authorName;

    @NotBlank
    @Size(min = 1, max = 45)
    private String title;

    @NotBlank
    @Size(min = 1)
    private String text;

    @Min(1)
    @Max(10)
    private double rating;

}
