package com.trainigcenter.springtask.persistence;

import com.trainigcenter.springtask.domain.Review;
import com.trainigcenter.springtask.domain.util.Pagination;

import java.util.List;
import java.util.Optional;

public interface ReviewDao {

    Optional<Review> findById(Integer id);

    Pagination<Review> findAll(Integer movieId, int page, int size);

    Review create(Review review);

    Optional<Review> findByMovieIdAndAuthorName(Integer movieId, String authorName);

    Review update(Review review);

    void delete(Integer id);
}
