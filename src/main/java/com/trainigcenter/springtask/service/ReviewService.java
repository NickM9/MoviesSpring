package com.trainigcenter.springtask.service;

import java.util.Set;

import com.trainigcenter.springtask.domain.Review;

public interface ReviewService {

    public Review getReviewById(int reviewId);
    public Set<Review> getAll();

}
