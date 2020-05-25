package com.trainigcenter.springtask.web.controller;

import com.trainigcenter.springtask.domain.Review;
import com.trainigcenter.springtask.domain.util.Pagination;
import com.trainigcenter.springtask.service.MovieService;
import com.trainigcenter.springtask.service.ReviewService;
import com.trainigcenter.springtask.web.dto.ReviewDto;
import com.trainigcenter.springtask.web.exception.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    public Pagination<ReviewDto> getAll(@RequestParam(value = "page", defaultValue = "1") int page,
                                  @RequestParam(value = "size", defaultValue = "2") int size,
                                  @PathVariable("movieId") int movieId) {

        if (page < 1 || size < 1) {
            throw new NotFoundException("Page and size can't be less than 1");
        }

        movieService.getById(movieId).orElseThrow(() -> new NotFoundException("Movie id:" + movieId + " not found"));

        Pagination<Review> allReviews = reviewService.getAll(movieId, page, size).get();

        if (page > allReviews.getMaxPage()){
            throw new NotFoundException("Page " + page + " not found");
        }

        return convertToPaginationDto(allReviews);
    }

    @GetMapping("/{id}")
    public ReviewDto getReviewById(@PathVariable("movieId") int movieId,
                                   @PathVariable("id") Integer id) {

        movieService.getById(movieId).orElseThrow(() -> new NotFoundException("Movie id:" + movieId + " not found"));
        Review review = reviewService.getById(id, movieId).orElseThrow(() -> new NotFoundException("Review id:" + id + " not found"));

        return convertToDto(review);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewDto saveReview(@PathVariable("movieId") int movieId,
                                @Valid @RequestBody ReviewDto reviewDto) {

        movieService.getById(movieId).orElseThrow(() -> new NotFoundException("Movie id:" + movieId + " not found"));
        reviewDto.setId(null);
        Review review = reviewService.create(convertFromDto(reviewDto));

        return convertToDto(review);
    }

    @PutMapping("/{id}")
    public ReviewDto updateReview(@PathVariable("movieId") int movieId,
                                  @PathVariable("id") Integer id,
                                  @Valid @RequestBody ReviewDto reviewDto) {

        movieService.getById(movieId).orElseThrow(() -> new NotFoundException("Movie id:" + movieId + " not found"));

        Review review = convertFromDto(reviewDto);
        review.setId(id);

        return convertToDto(reviewService.update(review));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReview(@PathVariable("movieId") int movieId,
                             @PathVariable("id") Integer id) {

        movieService.getById(movieId).orElseThrow(() -> new NotFoundException("Movie id:" + movieId + " not found"));
        Review review = reviewService.getById(id, movieId).orElseThrow(() -> new NotFoundException("Review id:" + id + " not found"));
        reviewService.delete(review.getId());
    }

    private ReviewDto convertToDto(Review review) {
        return modelMapper.map(review, ReviewDto.class);
    }

    private Review convertFromDto(ReviewDto reviewDto) {
        return modelMapper.map(reviewDto, Review.class);
    }

    private Pagination<ReviewDto> convertToPaginationDto(Pagination<Review> pagination) {
        List<ReviewDto> reviews = pagination.getObjects()
                                           .stream()
                                           .map(this::convertToDto)
                                           .collect(Collectors.toList());

        return new Pagination<>(pagination.getMaxPage(), reviews);
    }

}
