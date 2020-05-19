package com.trainigcenter.springtask.service;

import com.trainigcenter.springtask.domain.Review;

import java.util.Set;

public interface ReviewService {

    Review getReviewById(int reviewId);

    Set<Review> getAll();

    void addReview(Review review);

    Review updateReview(Review review);

    void deleteReview(Review review);
}
