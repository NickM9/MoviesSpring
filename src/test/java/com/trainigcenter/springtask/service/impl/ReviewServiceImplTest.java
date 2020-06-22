package com.trainigcenter.springtask.service.impl;

import com.trainigcenter.springtask.domain.Movie;
import com.trainigcenter.springtask.domain.Review;
import com.trainigcenter.springtask.domain.exception.NotFoundException;
import com.trainigcenter.springtask.persistence.MovieRepository;
import com.trainigcenter.springtask.persistence.ReviewRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllVerifyFindAllByMovieId1Time() {
        List<Review> reviews = List.of(new Review());
        int movieId = anyInt();
        Pageable pageable = PageRequest.of(0, 2, Sort.by("rating"));

        when(movieRepository.existsById(movieId)).thenReturn(true);
        when(reviewRepository.findAllByMovieId(movieId, pageable)).thenReturn(new PageImpl<>(reviews, pageable, reviews.size()));
        reviewService.getAll(movieId, 0, 2);

        verify(reviewRepository, times(1)).findAllByMovieId(movieId, pageable);
    }

    @Test
    public void testGetAllThrowNotFoundExceptionIfPageLessThan0() {
        assertThrows(NotFoundException.class, () -> reviewService.getAll(0, 0, 0));
    }

    @Test
    public void testGetAllThrowNotFoundExceptionIfMovieNotExistsById() {
        int movieId = anyInt();
        when(movieRepository.existsById(movieId)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> reviewService.getAll(movieId, 0, 0));
    }

    @Test
    public void testGetByIdVerifyFindById1Time() {
        Movie movie = new Movie();
        movie.setId(anyInt());
        when(movieRepository.existsById(movie.getId())).thenReturn(true);

        Review review = new Review();
        review.setId(anyInt());
        review.setMovie(movie);
        when(reviewRepository.findById(review.getId())).thenReturn(Optional.of(review));
        reviewService.getById(review.getId(), movie.getId());

        verify(reviewRepository, times(1)).findById(review.getId());
    }

    @Test
    public void testGetByIdThrowNotFoundExceptionIfMovieNotExistsById() {
        int movieId = anyInt();
        when(movieRepository.existsById(movieId)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> reviewService.getById(anyInt(), movieId));
    }

    @Test
    public void testSaveVerifySave1Time() {
        Review review = new Review();
        int movieId = anyInt();

        when(movieRepository.findById(movieId)).thenReturn(Optional.of(new Movie()));
        reviewService.save(review, movieId);

        verify(reviewRepository, times(1)).save(review);
    }

    @Test
    public void testSaveThrowNotFoundExceptionIfMovieNotFoundById() {
        int movieId = anyInt();
        when(movieRepository.findById(movieId)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> reviewService.save(new Review(), movieId));
    }

    @Test
    public void testSaveThrowNotFoundExceptionIfNotExistsById() {
        Review review = new Review();
        review.setId(anyInt());
        when(reviewRepository.existsById(review.getId())).thenReturn(false);
        assertThrows(NotFoundException.class, () -> reviewService.save(review, anyInt()));
    }

    @Test
    public void testDeleteVerify1Times() {
        Movie movie = new Movie();
        movie.setId(anyInt());
        when(movieRepository.existsById(movie.getId())).thenReturn(true);

        Review review = new Review();
        review.setId(anyInt());
        review.setMovie(movie);
        when(reviewRepository.findById(review.getId())).thenReturn(Optional.of(review));

        reviewService.delete(review.getId(), movie.getId());

        verify(reviewRepository, times(1)).deleteById(review.getId());
    }

    @Test
    public void testDeleteThrowNotFoundExceptionIfMovieNotExistsById() {
        int movieId = anyInt();
        when(movieRepository.existsById(movieId)).thenReturn(true);

        assertThrows(NotFoundException.class, () -> reviewService.delete(anyInt(), movieId));
    }

    @Test
    public void testDeleteThrowNotFoundExceptionIfNotExistsById() {
        int reviewId = anyInt();
        when(reviewRepository.existsById(reviewId)).thenReturn(true);

        assertThrows(NotFoundException.class, () -> reviewService.delete(reviewId, anyInt()));
    }
}
