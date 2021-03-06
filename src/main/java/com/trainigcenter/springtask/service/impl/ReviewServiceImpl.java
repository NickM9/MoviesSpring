package com.trainigcenter.springtask.service.impl;

import com.trainigcenter.springtask.domain.Movie;
import com.trainigcenter.springtask.domain.Pagination;
import com.trainigcenter.springtask.domain.Review;
import com.trainigcenter.springtask.domain.exception.BadRequestException;
import com.trainigcenter.springtask.domain.exception.NotFoundException;
import com.trainigcenter.springtask.persistence.MovieRepository;
import com.trainigcenter.springtask.persistence.ReviewRepository;
import com.trainigcenter.springtask.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;

    @Override
    @Transactional
    public Pagination<Review> getAll(int movieId, int page, int size) {
        if (page < 0 || size < 1) {
            throw new NotFoundException("Page can't be less than 0. Size can't be less than 1");
        }

        if (!movieRepository.existsById(movieId)) {
            throw new NotFoundException("Movie id: " + movieId + " not found");
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("rating"));
        Pagination<Review> reviewsPagination = convertToPagination(reviewRepository.findAllByMovieId(movieId, pageable));

        if (reviewsPagination.getObjects().isEmpty()){
            throw new NotFoundException("There are no reviews for movie id: " + movieId);
        }

        if (page >= reviewsPagination.getMaxPage()) {
            throw new NotFoundException("Page " + page + " not found");
        }

        return reviewsPagination;
    }

    @Override
    @Transactional
    public Optional<Review> getById(Integer id, Integer movieId) {
        if (!movieRepository.existsById(movieId)) {
            throw new NotFoundException("Movie id:" + movieId + " not found");
        }

        Optional<Review> review = reviewRepository.findById(id);

        if (review.get().getMovie().getId() != movieId) {
            throw new NotFoundException("There id no review id " + review.get().getId() + " for movie id " + review.get().getMovie().getId());
        }

        return review;
    }

    @Override
    @Transactional
    public Review save(Review review, Integer movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new NotFoundException("Movie id:" + movieId + " not found"));
        review.setMovie(movie);

        if (review.getId() != null) {
            Review dbReview = reviewRepository.findById(review.getId()).orElseThrow(() -> new NotFoundException("Review id:" + review.getId() + " not found"));

            if (dbReview.getMovie().getId() != movieId) {
                throw new NotFoundException("There id no review id " + dbReview.getId() + " for movie id " + movieId);
            }
        }

        if (review.getId() == null && reviewRepository.existsByMovieIdAndAuthorName(review.getMovie().getId(), review.getAuthorName())) {
            throw new BadRequestException("Review from author" + review.getAuthorName() + " already exists");
        }

        return reviewRepository.save(review);
    }

    @Override
    @Transactional
    public void delete(Integer id, Integer movieId) {
        if (!movieRepository.existsById(movieId)) {
            throw new NotFoundException("Movie id:" + movieId + " not found");
        }

        Review dbReview = reviewRepository.findById(id).orElseThrow(() -> new NotFoundException("Review id:" + id + " not found"));

        if (dbReview.getMovie().getId() != movieId){
            throw new NotFoundException("There id no review id " + dbReview.getId() + " for movie id " + movieId);
        }

        reviewRepository.deleteById(id);
    }

    private Pagination<Review> convertToPagination(Page<Review> reviewPage) {
        Pagination<Review> pagination = new Pagination<>();

        pagination.setPage(reviewPage.getNumber());
        pagination.setMaxPage(reviewPage.getTotalPages());
        pagination.setSize(reviewPage.getSize());
        pagination.setObjects(reviewPage.getContent());

        return pagination;
    }
}
