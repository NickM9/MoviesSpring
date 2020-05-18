package com.trainigcenter.springtask.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewDto {

    private MovieDto movie;
    private String authorName;
    private String title;
    private String text;
    private double rating;

}
