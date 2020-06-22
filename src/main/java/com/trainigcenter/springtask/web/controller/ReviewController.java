package com.trainigcenter.springtask.web.controller;

import com.trainigcenter.springtask.domain.Pagination;
import com.trainigcenter.springtask.domain.Review;
import com.trainigcenter.springtask.domain.exception.NotFoundException;
import com.trainigcenter.springtask.service.ReviewService;
import com.trainigcenter.springtask.web.dto.ReviewDto;
import com.trainigcenter.springtask.web.dto.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/movies/{movieId}/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewMapper mapper;

    @GetMapping
    public Pagination<ReviewDto> getAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "size", defaultValue = "2") int size,
                                        @PathVariable("movieId") int movieId) {

        Pagination<Review> reviewPagination = reviewService.getAll(movieId, page, size);
        List<ReviewDto> reviewsDto = reviewPagination.getObjects()
                                                     .stream()
                                                     .map(review -> mapper.toDto(review))
                                                     .collect(Collectors.toList());

        Pagination<ReviewDto> paginationDto = new Pagination<>();

        paginationDto.setPage(reviewPagination.getPage());
        paginationDto.setMaxPage(reviewPagination.getMaxPage());
        paginationDto.setSize(reviewPagination.getSize());
        paginationDto.setObjects(reviewsDto);

        return paginationDto;
    }

    @GetMapping("/{id}")
    public ReviewDto getById(@PathVariable("movieId") int movieId,
                             @PathVariable("id") Integer id) {
        return mapper.toDto(reviewService.getById(id, movieId).orElseThrow(() -> new NotFoundException("Review id:" + id + " not found")));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewDto save(@PathVariable("movieId") int movieId,
                          @Valid @RequestBody ReviewDto reviewDto) {
        reviewDto.setId(null);
        Review review = reviewService.save(mapper.fromDto(reviewDto), movieId);
        return mapper.toDto(review);
    }

    @PutMapping("/{id}")
    public ReviewDto update(@PathVariable("movieId") int movieId,
                            @PathVariable("id") int id,
                            @Valid @RequestBody ReviewDto reviewDto) {
        reviewDto.setId(id);
        Review review = reviewService.save(mapper.fromDto(reviewDto), movieId);
        return mapper.toDto(review);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("movieId") int movieId,
                       @PathVariable("id") Integer id) {
        reviewService.delete(id, movieId);
    }
}
