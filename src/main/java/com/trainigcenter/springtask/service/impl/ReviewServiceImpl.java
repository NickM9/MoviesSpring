package com.trainigcenter.springtask.service.impl;

import com.trainigcenter.springtask.domain.Review;
import com.trainigcenter.springtask.persistence.ReviewRepository;
import com.trainigcenter.springtask.service.MovieService;
import com.trainigcenter.springtask.service.ReviewService;
import com.trainigcenter.springtask.web.controller.MovieController;
import com.trainigcenter.springtask.web.exception.MethodNotAllowedException;
import com.trainigcenter.springtask.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private static final Logger logger = LogManager.getLogger(ReviewServiceImpl.class);

    private final ReviewRepository reviewRepository;
    private final MovieService movieService;

    @Override
    public Page<Review> getAll(Integer movieId, Pageable pageable) {
        return reviewRepository.findAllByMovieId(movieId, pageable);
    }

    @Override
    public Optional<Review> getById(Integer id) {
        return reviewRepository.findById(id);
    }

    @Override
    @Transactional
    public Review create(Review review, int movieId) {

        review.setId(null);
        review.setMovie(movieService.getById(movieId).get());

        Optional<Review> dbReview = reviewRepository.findByMovieIdAndAuthorName(review.getMovie().getId(), review.getAuthorName());

        if (dbReview.isPresent()) {
            throw new MethodNotAllowedException("Review id:" + dbReview.get().getId() + " already exists");
        }

        return reviewRepository.save(review);
    }

    @Override
    @Transactional
    public Review update(Review review, int id, int movieId) {
        review.setId(id);
        review.setMovie(movieService.getById(movieId).get());

        Optional<Review> dbReview = reviewRepository.findById(review.getId());
        dbReview.orElseThrow(() -> new NotFoundException("Review id:" + review.getId() + " not found"));

        return reviewRepository.save(review);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        reviewRepository.deleteById(id);
    }
}
