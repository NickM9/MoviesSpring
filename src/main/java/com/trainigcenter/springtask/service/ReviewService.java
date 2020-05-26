package com.trainigcenter.springtask.service;

import com.trainigcenter.springtask.domain.Review;
import com.trainigcenter.springtask.domain.util.Pagination;

import java.util.List;
import java.util.Optional;

public interface ReviewService {

    Optional<Review> getById(Integer id);

    Pagination<Review> getAll(Integer movieId, int page, int size);

    Review create(Review review);

    Review update(Review review);

    void delete(Integer id);
}
