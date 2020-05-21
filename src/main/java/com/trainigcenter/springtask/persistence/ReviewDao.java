package com.trainigcenter.springtask.persistence;

import com.trainigcenter.springtask.domain.Review;

import java.util.List;

public interface ReviewDao {

    Review findById(Integer id, Integer movieId);

    List<Review> findAll(Integer movieId, int page, int size);

    Review add(Review review);

    Review findByMovieIdAndAuthorName(Integer movieId, String authorName);

    Review update(Review review);

    void delete(Review review);
}
