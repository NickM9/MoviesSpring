package com.trainigcenter.springtask.service.impl;

import com.trainigcenter.springtask.domain.Movie;
import com.trainigcenter.springtask.domain.Review;
import com.trainigcenter.springtask.persistence.ReviewRepository;
import com.trainigcenter.springtask.service.MovieService;
import com.trainigcenter.springtask.service.ReviewService;
import com.trainigcenter.springtask.web.exception.MethodNotAllowedException;
import com.trainigcenter.springtask.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final MovieService movieService;

    @Override
    public Page<Review> getAll(int movieId, int page, int size) {

        movieService.getById(movieId).orElseThrow(() -> new NotFoundException("Movie id: " + movieId + " not found"));

        Pageable pageable = PageRequest.of(page - 1, size);

        Page<Review> reviews = reviewRepository.findAllByMovieId(movieId, pageable);

        if (page > reviews.getTotalPages()) {
            throw new NotFoundException("Page " + page + " not found");
        }

        return reviews;
    }

    @Override
    public Optional<Review> getById(Integer id, Integer movieId) {
        movieService.getById(movieId).orElseThrow(() -> new NotFoundException("Movie id:" + movieId + " not found"));
        return reviewRepository.findById(id);
    }

    @Override
    @Transactional
    public Review create(Review review, Integer movieId) {
        Movie movie = movieService.getById(movieId).orElseThrow(() -> new NotFoundException("Movie id:" + movieId + " not found"));

        review.setId(null);
        review.setMovie(movie);

        Optional<Review> dbReview = reviewRepository.findByMovieIdAndAuthorName(review.getMovie().getId(), review.getAuthorName());

        if (dbReview.isPresent()) {
            throw new MethodNotAllowedException("Review id:" + dbReview.get().getId() + " already exists");
        }

        return reviewRepository.save(review);
    }

    @Override
    @Transactional
    public Review update(Review review, Integer id, Integer movieId) {
        Movie movie = movieService.getById(movieId).orElseThrow(() -> new NotFoundException("Movie id:" + movieId + " not found"));

        review.setId(id);
        review.setMovie(movie);

        Optional<Review> dbReview = reviewRepository.findById(review.getId());
        dbReview.orElseThrow(() -> new NotFoundException("Review id:" + review.getId() + " not found"));

        return reviewRepository.save(review);
    }

    @Override
    @Transactional
    public void delete(Integer id, Integer movieId) {
        movieService.getById(movieId).orElseThrow(() -> new NotFoundException("Movie id:" + movieId + " not found"));
        reviewRepository.findById(id).orElseThrow(() -> new NotFoundException("Review id:" + id + " not found"));

        reviewRepository.deleteById(id);
    }
}
