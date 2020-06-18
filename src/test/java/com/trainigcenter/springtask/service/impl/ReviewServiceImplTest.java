package com.trainigcenter.springtask.service.impl;

import com.trainigcenter.springtask.domain.Movie;
import com.trainigcenter.springtask.domain.Pagination;
import com.trainigcenter.springtask.domain.Review;
import com.trainigcenter.springtask.domain.exception.BadRequestException;
import com.trainigcenter.springtask.domain.exception.NotFoundException;
import com.trainigcenter.springtask.persistence.MovieRepository;
import com.trainigcenter.springtask.persistence.ReviewRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
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
    public void testGetAll() {
        Review review = new Review();
        List<Review> reviews = List.of(review);
        int movieId = anyInt();

        when(movieRepository.existsById(movieId)).thenReturn(true);

        Pagination<Review> expected = new Pagination<>();
        expected.setLocalPage(0);
        expected.setMaxPage(1);
        expected.setSize(2);
        expected.setObjects(reviews);

        Pageable pageable = PageRequest.of(0, 2, Sort.by("rating"));
        Page<Review> page = new PageImpl<>(reviews, pageable, reviews.size());

        when(reviewRepository.findAllByMovieId(movieId, pageable)).thenReturn(page);
        Pagination<Review> actual = reviewService.getAll(movieId,0, 2);

        assertEquals(expected, actual);
    }

    @Test
    public void testGetAllThrowNotFoundExceptionLessPage() {
        assertThrows(NotFoundException.class, () -> reviewService.getAll(anyInt(), 0, 0));
    }

    @Test
    public void testGetAllThrowNotFoundExceptionNotExistsById() {
        int movieId = anyInt();
        when(movieRepository.existsById(movieId)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> reviewService.getAll(movieId, 0, 0));
    }

    @Test
    public void testGetById() {
        int movieId = anyInt();
        when(movieRepository.existsById(movieId)).thenReturn(true);

        Review expected = new Review();
        expected.setId(anyInt());
        when(reviewRepository.findById(expected.getId())).thenReturn(Optional.of(expected));
        Review actual = reviewService.getById(expected.getId(), movieId).get();

        assertEquals(expected, actual);
    }

    @Test
    public void testGetByIdThrowNotFoundException(){
        int movieId = anyInt();
        when(movieRepository.existsById(movieId)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> reviewService.getById(anyInt(), movieId));
    }

    @Test
    public void testSaveNew() {
        Movie movie = new Movie();
        movie.setId(anyInt());
        when(movieRepository.findById(movie.getId())).thenReturn(Optional.of(movie));

        Review expected = new Review();
        when(reviewRepository.existsByMovieIdAndAuthorName(movie.getId(), "name")).thenReturn(false);
        when(reviewRepository.save(expected)).thenReturn(expected);

        Review actual = reviewService.save(expected, movie.getId());

        assertEquals(expected, actual);
    }

    @Test
    public void testSaveUpdate() {
        Movie movie = new Movie();
        movie.setId(anyInt());
        when(movieRepository.findById(movie.getId())).thenReturn(Optional.of(movie));

        Review expected = new Review();
        expected.setId(anyInt());
        when(reviewRepository.existsById(expected.getId())).thenReturn(true);


        when(reviewRepository.existsByMovieIdAndAuthorName(movie.getId(), "name")).thenReturn(false);
        when(reviewRepository.save(expected)).thenReturn(expected);

        Review actual = reviewService.save(expected, movie.getId());

        assertEquals(expected, actual);
    }

    @Test
    public void testSaveThrowNotFoundExceptionFindMovie(){
        int movieId = anyInt();
        when(movieRepository.findById(movieId)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> reviewService.save(new Review(), movieId));
    }

    @Test
    public void testSaveThrowNotFoundExceptionExistsById(){
        Review review = new Review();
        review.setId(anyInt());
        when(reviewRepository.existsById(review.getId())).thenReturn(false);
        assertThrows(NotFoundException.class, () -> reviewService.save(review, anyInt()));
    }

    @Test
    public void testDeleteVerify1Times() {
        int movieId = anyInt();
        when(movieRepository.existsById(movieId)).thenReturn(true);

        Review review = new Review();
        review.setId(anyInt());
        when(reviewRepository.existsById(review.getId())).thenReturn(true);

        reviewService.delete(review.getId(), movieId);
        verify(reviewRepository, times(1)).deleteById(review.getId());
    }

    @Test
    public void testDeleteThrowNotFoundExceptionNotExistsMovie(){
        int movieId = anyInt();
        when(movieRepository.existsById(movieId)).thenReturn(true);

        assertThrows(NotFoundException.class, () -> reviewService.delete(anyInt(), movieId));
    }

    @Test
    public void testDeleteThrowNotFoundExceptionNotExistsReview(){
        int reviewId = anyInt();
        when(reviewRepository.existsById(reviewId)).thenReturn(true);

        assertThrows(NotFoundException.class, () -> reviewService.delete(reviewId, anyInt()));
    }
}
