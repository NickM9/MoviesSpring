package com.trainigcenter.springtask.dto;

import com.trainigcenter.springtask.domain.Movie;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
public class ReviewDto {

    private MovieDto movie;
    private String authorName;
    private String title;
    private String text;
    private double rating;

}
