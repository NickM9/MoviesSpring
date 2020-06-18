package com.trainigcenter.springtask.service.impl;

import com.trainigcenter.springtask.domain.Movie;
import com.trainigcenter.springtask.domain.Pagination;
import com.trainigcenter.springtask.domain.exception.BadRequestException;
import com.trainigcenter.springtask.domain.exception.NotFoundException;
import com.trainigcenter.springtask.persistence.MovieRepository;
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

public class MovieServiceImplTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieServiceImpl movieService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAll() {
        Movie movie = new Movie();
        List<Movie> movies = List.of(movie);

        Pagination<Movie> expected = new Pagination<>();
        expected.setLocalPage(0);
        expected.setMaxPage(1);
        expected.setSize(2);
        expected.setObjects(movies);

        Pageable pageable = PageRequest.of(0, 2, Sort.by("title"));
        Page<Movie> page = new PageImpl<>(movies, pageable, movies.size());

        when(movieRepository.findAll(pageable)).thenReturn(page);
        Pagination<Movie> actual = movieService.getAll(0, 2);

        assertEquals(expected, actual);
    }

    @Test
    public void testGetAllThrowNotFoundExceptionLessPage() {
        assertThrows(NotFoundException.class, () -> movieService.getAll(0, 0));
    }

    @Test
    public void testGetById() {
        Movie expected = new Movie();
        expected.setId(anyInt());

        when(movieRepository.findById(expected.getId())).thenReturn(Optional.of(expected));
        Movie actual = movieService.getById(expected.getId()).get();

        assertEquals(expected, actual);
    }

    @Test
    public void testSaveNew() {
        Movie expected = new Movie();
        expected.setTitle(anyString());

        when(movieRepository.findByTitle(expected.getTitle())).thenReturn(List.of());
        when(movieRepository.save(expected)).thenReturn(expected);
        Movie actual = movieService.save(expected);

        assertEquals(expected, actual);
    }

    @Test
    public void testSaveUpdate() {
        Movie expected = new Movie();
        expected.setId(anyInt());
        when(movieRepository.existsById(expected.getId())).thenReturn(true);

        expected.setTitle(anyString());
        when(movieRepository.findByTitle(expected.getTitle())).thenReturn(List.of(new Movie()));

        when(movieRepository.save(expected)).thenReturn(expected);
        Movie actual = movieService.save(expected);

        assertEquals(expected, actual);
    }

    @Test
    public void testSaveThrowNotFoundException() {
        Movie movie = new Movie();
        movie.setId(anyInt());
        when(movieRepository.existsById(movie.getId())).thenReturn(false);

        assertThrows(NotFoundException.class, () -> movieService.save(movie));
    }

    @Test
    public void testSaveThrowBadRequestException() {
        Movie movie = new Movie();
        movie.setTitle(anyString());

        when(movieRepository.findByTitle(movie.getTitle())).thenReturn(List.of(movie));
        assertThrows(BadRequestException.class, () -> movieService.save(movie));
    }

    @Test
    public void testDeleteVerify1Times() {
        Movie movie = new Movie();
        movie.setId(anyInt());

        when(movieRepository.existsById(movie.getId())).thenReturn(true);
        movieService.delete(movie.getId());

        verify(movieRepository, times(1)).deleteById(movie.getId());
    }

    @Test
    public void testDeleteThrowNotFoundException() {
        Movie movie = new Movie();
        movie.setId(anyInt());

        when(movieRepository.existsById(movie.getId())).thenReturn(false);
        assertThrows(NotFoundException.class, () -> movieService.delete(movie.getId()));
    }
}
