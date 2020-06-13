package com.trainigcenter.springtask.service;

import com.trainigcenter.springtask.domain.Review;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ReviewService {

    Page<Review> getAll(int movieId, int page, int size);

    Optional<Review> getById(Integer id, Integer movieId);

    Review create(Review review, Integer movieId);

    Review update(Review review, Integer id, Integer movieId);

    void delete(Integer id, Integer movieId);

}
