package com.trainigcenter.springtask.controller;

import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.trainigcenter.springtask.domain.Review;
import com.trainigcenter.springtask.dto.ReviewDto;
import com.trainigcenter.springtask.service.ReviewService;

@RestController
@RequestMapping("/reviews")
public class ReviewRestController {
	
	private ReviewService reviewService;
	private ModelMapper modelMapper;
	
	@Autowired
	public ReviewRestController(ReviewService reviewService, ModelMapper modelMapper) {
		this.reviewService = reviewService;
		this.modelMapper = modelMapper;
	}
	
	@GetMapping
    public Set<ReviewDto> getAll() {
		Set<Review> allReviews = reviewService.getAll();
		Set<ReviewDto> allReviewDto = allReviews.stream().map(this::convertoDto).collect(Collectors.toSet());
        return allReviewDto;
    }
	
	@GetMapping("/{reviewId}")
	public ReviewDto getReviewById(@PathVariable int reviewId) {
		Review review = reviewService.getReviewById(reviewId);
		ReviewDto reviewDto = convertoDto(review);
		return reviewDto;
	}
	
	private ReviewDto convertoDto(Review review) {
		return modelMapper.map(review, ReviewDto.class);
	}

}
