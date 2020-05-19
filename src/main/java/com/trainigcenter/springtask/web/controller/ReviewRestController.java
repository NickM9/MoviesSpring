package com.trainigcenter.springtask.web.controller;

import com.trainigcenter.springtask.application.domain.Review;
import com.trainigcenter.springtask.web.dto.Error;
import com.trainigcenter.springtask.web.dto.ReviewDto;
import com.trainigcenter.springtask.web.exception.NotFoundException;
import com.trainigcenter.springtask.application.service.ReviewService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reviews")
public class ReviewRestController {

    private static final Logger logger = LogManager.getLogger(ReviewRestController.class);

    private final ReviewService reviewService;
    private final ModelMapper modelMapper;

    @Autowired
    public ReviewRestController(ReviewService reviewService, ModelMapper modelMapper) {
        this.reviewService = reviewService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public Set<ReviewDto> getAll() {
        Set<Review> allReviews = reviewService.getAll();
        Set<ReviewDto> allReviewDto = allReviews.stream()
				                                .map(this::convertToDto)
				                                .collect(Collectors.toSet());
        return allReviewDto;
    }

    @GetMapping("/{reviewId}")
    public ReviewDto getReviewById(@PathVariable int reviewId) {
        Review review = reviewService.getReviewById(reviewId);
        ReviewDto reviewDto = convertToDto(review);
        return reviewDto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveReview(@Valid @RequestBody ReviewDto reviewDto) {
        reviewDto.setId(null);
        Review review = convertFromDto(reviewDto);
        reviewService.addReview(review);
    }

    @PutMapping("/{reviewId}")
    public ReviewDto updateMovie(@PathVariable("reviewId") Integer reviewId, @Valid @RequestBody ReviewDto reviewDto) {

        Review review = convertFromDto(reviewDto);
        review.setId(reviewId);

        review = reviewService.updateReview(review);
        notNullReview(review);

        return convertToDto(review);
    }

    @DeleteMapping("/{reviewId}")
    public void deleteMovie(@PathVariable("reviewId") Integer reviewId) {

        Review review = reviewService.getReviewById(reviewId);
        notNullReview(review);

        reviewService.deleteReview(review);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private Error handleBadInput(MethodArgumentNotValidException e) {
        Error error = new Error(HttpStatus.BAD_REQUEST.value(), "Bad request. You should init all movie fields");
        return error;
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    private Error reviewNotFound(NotFoundException e) {
        Error error = new Error(HttpStatus.NOT_FOUND.value(), e.getName());
        return error;
    }


    private void notNullReview(Review review) {
        if (review == null) {
            throw new NotFoundException("Review is not exist");
        }
    }

    private ReviewDto convertToDto(Review review) {
        return modelMapper.map(review, ReviewDto.class);
    }

    private Review convertFromDto(ReviewDto reviewDto) {
        return modelMapper.map(reviewDto, Review.class);
    }

}
