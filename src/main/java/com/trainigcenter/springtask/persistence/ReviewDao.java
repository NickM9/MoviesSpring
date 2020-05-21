package com.trainigcenter.springtask.persistence;

import com.trainigcenter.springtask.domain.Review;

import java.util.List;

public interface ReviewDao {

    Review findById(Integer id);

    List<Review> findAll(int movieId, int page, int size);

    Review add(Review review);

    Review findByMovieIdAndAuthorName(Integer movieId, String authorName);

    Review update(Review review);

    void delete(Review review);
}
