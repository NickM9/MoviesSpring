package com.trainigcenter.springtask.service;

import com.trainigcenter.springtask.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ReviewService {

    Page<Review> getAll(Integer movieId, Pageable pageable);

    Optional<Review> getById(Integer id);

    Review create(Review review, int movieId);

    Review update(Review review, int id, int movieId);

    void delete(Integer id);

}
