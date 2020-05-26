package com.trainigcenter.springtask.service;

import com.trainigcenter.springtask.domain.Review;
import com.trainigcenter.springtask.domain.util.Pagination;

import java.util.List;
import java.util.Optional;

public interface ReviewService {

    Optional<Review> getById(Integer id);

    Pagination<Review> getAll(Integer movieId, int page, int size);

    Review create(Review review, int movieId);

    Review update(Review review, int id, int movieId);

    void delete(Integer id);
}
