package com.trainigcenter.springtask.service;

import com.trainigcenter.springtask.domain.Pagination;
import com.trainigcenter.springtask.domain.Review;

import java.util.Optional;

public interface ReviewService {

    Pagination<Review> getAll(int movieId, int page, int size);

    Optional<Review> getById(Integer id, Integer movieId);

    Review save(Review review, Integer movieId);

    void delete(Integer id, Integer movieId);
}
