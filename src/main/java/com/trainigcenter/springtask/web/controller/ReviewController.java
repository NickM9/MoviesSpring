package com.trainigcenter.springtask.web.controller;

import com.trainigcenter.springtask.domain.Review;
import com.trainigcenter.springtask.service.ReviewService;
import com.trainigcenter.springtask.web.dto.ReviewDto;
import com.trainigcenter.springtask.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
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
@Log4j2
@RequestMapping("/movies/{movieId}/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final ModelMapper modelMapper;

    @GetMapping
    public Page<ReviewDto> getAll(@RequestParam(value = "page", defaultValue = "1") int page,
                                  @RequestParam(value = "size", defaultValue = "2") int size,
                                  @PathVariable("movieId") int movieId) {

        if (page < 1 || size < 1) {
            throw new NotFoundException("Page and size can't be less than 1");
        }

        return convertToPaginationDto(reviewService.getAll(movieId, page, size));
    }

    @GetMapping("/{id}")
    public ReviewDto getReviewById(@PathVariable("movieId") int movieId,
                                   @PathVariable("id") Integer id) {

        Review review = reviewService.getById(id, movieId).orElseThrow(() -> new NotFoundException("Review id:" + id + " not found"));
        return convertToDto(review);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewDto saveReview(@PathVariable("movieId") int movieId,
                                @Valid @RequestBody ReviewDto reviewDto) {

        Review review = convertFromDto(reviewDto);
        return convertToDto(reviewService.create(review, movieId));
    }

    @PutMapping("/{id}")
    public ReviewDto updateReview(@PathVariable("movieId") int movieId,
                                  @PathVariable("id") int id,
                                  @Valid @RequestBody ReviewDto reviewDto) {

        Review review = convertFromDto(reviewDto);
        return convertToDto(reviewService.update(review, id, movieId));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReview(@PathVariable("movieId") int movieId,
                             @PathVariable("id") Integer id) {

        reviewService.delete(id, movieId);
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
