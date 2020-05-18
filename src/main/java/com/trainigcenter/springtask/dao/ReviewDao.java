package com.trainigcenter.springtask.dao;

import java.util.Set;

import com.trainigcenter.springtask.domain.Review;

public interface ReviewDao {

    public Review findReviewById(int reviewId);
    
    public Set<Review> findAll();

}
