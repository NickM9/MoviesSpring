package com.trainigcenter.springtask.web.controller;

import com.trainigcenter.springtask.domain.Review;
import com.trainigcenter.springtask.service.MovieService;
import com.trainigcenter.springtask.service.ReviewService;
import com.trainigcenter.springtask.web.dto.ReviewDto;
import com.trainigcenter.springtask.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies/{movieId}/reviews")
public class ReviewController {

    private static final Logger logger = LogManager.getLogger(ReviewController.class);

    private final ReviewService reviewService;
    private final MovieService movieService;
    private final ModelMapper modelMapper;

    @GetMapping
    public Page<ReviewDto> getAll(@RequestParam(value = "page", defaultValue = "1") int page,
                                  @RequestParam(value = "size", defaultValue = "2") int size,
                                  @PathVariable("movieId") int movieId) {

        if (page < 1 || size < 1) {
            throw new NotFoundException("Page and size can't be less than 1");
        }

        movieService.getById(movieId).orElseThrow(() -> new NotFoundException("Movie id: " + movieId + " not found"));

        Pageable pageable = PageRequest.of(page - 1, size);

        Page<Review> reviews = reviewService.getAll(movieId, pageable);

        if (page > reviews.getTotalPages()) {
            throw new NotFoundException("Page " + page + " not found");
        }

        return convertToPaginationDto(reviews);
    }

    @GetMapping("/{id}")
    public ReviewDto getReviewById(@PathVariable("movieId") int movieId,
                                   @PathVariable("id") Integer id) {

        movieService.getById(movieId).orElseThrow(() -> new NotFoundException("Movie id:" + movieId + " not found"));
        Review review = reviewService.getById(id).orElseThrow(() -> new NotFoundException("Review id:" + id + " not found"));

        return convertToDto(review);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewDto saveReview(@PathVariable("movieId") int movieId,
                                @Valid @RequestBody ReviewDto reviewDto) {

        movieService.getById(movieId).orElseThrow(() -> new NotFoundException("Movie id:" + movieId + " not found"));
        Review review = convertFromDto(reviewDto);

        return convertToDto(reviewService.create(review, movieId));
    }

    @PutMapping("/{id}")
    public ReviewDto updateReview(@PathVariable("movieId") int movieId,
                                  @PathVariable("id") int id,
                                  @Valid @RequestBody ReviewDto reviewDto) {

        movieService.getById(movieId).orElseThrow(() -> new NotFoundException("Movie id:" + movieId + " not found"));
        Review review = convertFromDto(reviewDto);

        return convertToDto(reviewService.update(review, id, movieId));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReview(@PathVariable("movieId") int movieId,
                             @PathVariable("id") Integer id) {

        movieService.getById(movieId).orElseThrow(() -> new NotFoundException("Movie id:" + movieId + " not found"));
        Review review = reviewService.getById(id).orElseThrow(() -> new NotFoundException("Review id:" + id + " not found"));
        reviewService.delete(review.getId());
    }

    private ReviewDto convertToDto(Review review) {
        return modelMapper.map(review, ReviewDto.class);
    }

    private Review convertFromDto(ReviewDto reviewDto) {
        return modelMapper.map(reviewDto, Review.class);
    }

    private Page<ReviewDto> convertToPaginationDto(Page<Review> reviewPagination) {
        return reviewPagination.map(this::convertToDto);
    }
}
