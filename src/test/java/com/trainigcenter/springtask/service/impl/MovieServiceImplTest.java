package com.trainigcenter.springtask.service.impl;

import com.trainigcenter.springtask.domain.Movie;
import com.trainigcenter.springtask.domain.exception.BadRequestException;
import com.trainigcenter.springtask.domain.exception.NotFoundException;
import com.trainigcenter.springtask.persistence.MovieRepository;
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
    public void testGetAllVerifyFindAll1Time() {
        List<Movie> movies = List.of(new Movie());
        Pageable pageable = PageRequest.of(0, 2, Sort.by("title"));

        when(movieRepository.findAll(pageable)).thenReturn(new PageImpl<>(movies, pageable, movies.size()));
        movieService.getAll(0, 2);

        verify(movieRepository, times(1)).findAll(pageable);
    }

    @Test
    public void testGetAllThrowNotFoundExceptionIfPageLessThan0() {
        assertThrows(NotFoundException.class, () -> movieService.getAll(0, 0));
    }

    @Test
    public void testGetByIdVerifyFindById1Time() {
        int id = anyInt();
        when(movieRepository.findById(id)).thenReturn(Optional.of(new Movie()));
        movieService.getById(id);

        verify(movieRepository, times(1)).findById(id);
    }

    @Test
    public void testSaveVerifySave1Time() {
        Movie movie = new Movie();
        movieService.save(movie);

        verify(movieRepository, times(1)).save(movie);
    }

    @Test
    public void testSaveThrowNotFoundExceptionIfExistsByIdFalse() {
        Movie movie = new Movie();
        movie.setId(anyInt());
        when(movieRepository.existsById(movie.getId())).thenReturn(false);

        assertThrows(NotFoundException.class, () -> movieService.save(movie));
    }

    @Test
    public void testSaveThrowBadRequestExceptionIfFindByTitleNotEmptyAndEquals() {
        Movie movie = new Movie();
        movie.setTitle(anyString());

        when(movieRepository.findByTitle(movie.getTitle())).thenReturn(List.of(movie));
        assertThrows(BadRequestException.class, () -> movieService.save(movie));
    }

    @Test
    public void testDeleteVerifyDelete1Time() {
        int id = anyInt();
        when(movieRepository.existsById(id)).thenReturn(true);
        movieService.delete(id);

        verify(movieRepository, times(1)).deleteById(id);
    }

    @Test
    public void testDeleteThrowNotFoundExceptionIfExistsByIdFalse() {
        int id = anyInt();
        when(movieRepository.existsById(id)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> movieService.delete(id));
    }
}
