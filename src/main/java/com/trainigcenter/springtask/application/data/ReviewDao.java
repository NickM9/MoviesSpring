package com.trainigcenter.springtask.application.data;

import com.trainigcenter.springtask.application.domain.Review;

import java.util.Set;

public interface ReviewDao {

    Review findReviewById(int reviewId);

    Set<Review> findAll();

    void addReview(Review review);

    Review findByMovieIdAndAuthorName(Integer movieId, String authorName);

    Review updateReview(Review review);

    void deleteReview(Review review);
}
