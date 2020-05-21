package com.trainigcenter.springtask.web.controller;

import com.trainigcenter.springtask.domain.Movie;
import com.trainigcenter.springtask.domain.Review;
import com.trainigcenter.springtask.service.MovieService;
import com.trainigcenter.springtask.service.ReviewService;
import com.trainigcenter.springtask.web.dto.Error;
import com.trainigcenter.springtask.web.dto.ReviewDto;
import com.trainigcenter.springtask.web.exception.ForbiddenException;
import com.trainigcenter.springtask.web.exception.NotFoundException;
import com.trainigcenter.springtask.web.exception.WebException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movies/{movieId}/reviews")
public class ReviewController {

    private static final Logger logger = LogManager.getLogger(ReviewController.class);

    private final ReviewService reviewService;
    private final MovieService movieService;
    private final ModelMapper modelMapper;

    @Autowired
    public ReviewController(ReviewService reviewService, MovieService movieService, ModelMapper modelMapper) {
        this.reviewService = reviewService;
        this.movieService = movieService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<ReviewDto> getAll(@RequestParam(value = "page", defaultValue = "1") int page,
                                  @RequestParam(value = "size", defaultValue = "2") int size,
                                  @PathVariable("movieId") int movieId) {
        Optional.ofNullable(movieService.getById(movieId))
                .orElseThrow(() -> new NotFoundException("Movie id:" + movieId + " not found"));

        List<Review> allReviews = reviewService.getAll(movieId, page, size);
        return allReviews.stream()
                         .map(this::convertToDto)
                         .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ReviewDto getReviewById(@PathVariable("movieId") int movieId,
                                   @PathVariable("id") Integer id) {

        Optional.ofNullable(movieService.getById(movieId))
                .orElseThrow(() -> new NotFoundException("Movie id:" + movieId + " not found"));

        Review review = Optional.ofNullable(reviewService.getById(id, movieId))
                                .orElseThrow(() -> new NotFoundException("Review id:" + id + " not found"));

        return convertToDto(review);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewDto saveReview(@PathVariable("movieId") int movieId,
                                @Valid @RequestBody ReviewDto reviewDto) {

        Optional.ofNullable(movieService.getById(movieId))
                .orElseThrow(() -> new NotFoundException("Movie id:" + movieId + " not found"));

        reviewDto.setId(null);
        Review review = reviewService.add(convertFromDto(reviewDto));

        return convertToDto(review);
    }

    @PutMapping("/{id}")
    public ReviewDto updateReview(@PathVariable("movieId") int movieId,
                                  @PathVariable("id") Integer id,
                                  @Valid @RequestBody ReviewDto reviewDto) {

        Optional.ofNullable(movieService.getById(movieId))
                .orElseThrow(() -> new NotFoundException("Movie id:" + movieId + " not found"));

        Review review = convertFromDto(reviewDto);
        review.setId(id);

        review = Optional.ofNullable(reviewService.update(review))
                         .orElseThrow(() -> new NotFoundException("Review id:" + id + " not found"));

        return convertToDto(review);
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable("movieId") int movieId,
                             @PathVariable("id") Integer id) {

        Optional.ofNullable(movieService.getById(movieId))
                .orElseThrow(() -> new NotFoundException("Movie id:" + movieId + " not found"));

        Review review = Optional.ofNullable(reviewService.getById(id, movieId))
                                .orElseThrow(() -> new NotFoundException("Review id:" + id + " not found"));

        reviewService.delete(review);
    }

    @ExceptionHandler({NotFoundException.class, ForbiddenException.class})
    private ResponseEntity<Error> exceptionHandler(WebException e) {
        Error error = new Error(e.getStatus().value(), e.getMessage());
        return new ResponseEntity<>(error, e.getStatus());
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private Error notValidArgumentHandler(Exception e) {
        return new Error(HttpStatus.BAD_REQUEST.value(), "You need to initialize all fields");
    }

    private ReviewDto convertToDto(Review review) {
        return modelMapper.map(review, ReviewDto.class);
    }

    private Review convertFromDto(ReviewDto reviewDto) {
        return modelMapper.map(reviewDto, Review.class);
    }

}
