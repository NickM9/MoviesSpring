package com.trainigcenter.springtask.service;

import com.trainigcenter.springtask.domain.Review;

import java.util.List;

public interface ReviewService {

    Review getById(Integer id);

    List<Review> getAll(int movieId, int page, int size);

    Review add(Review review);

    Review update(Review review);

    void delete(Review review);
}
