package com.trainigcenter.springtask.service.impl;

import com.trainigcenter.springtask.domain.Genre;
import com.trainigcenter.springtask.domain.Movie;
import com.trainigcenter.springtask.domain.exception.BadRequestException;
import com.trainigcenter.springtask.domain.exception.NotFoundException;
import com.trainigcenter.springtask.persistence.GenreRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GenreServiceImplTest {

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private GenreServiceImpl genreService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllVerifyFindAll1Time() {
        when(genreRepository.findAll()).thenReturn(List.of());
        genreService.getAll();

        verify(genreRepository, times(1)).findAll();
    }

    @Test
    public void testGetByIdVerifyFindById1Time() {
        int id = anyInt();
        when(genreRepository.findById(id)).thenReturn(Optional.of(new Genre()));
        genreService.getById(id);

        verify(genreRepository, times(1)).findById(id);
    }

    @Test
    public void testSaveVerifySave1Time() {
        Genre genre = new Genre();
        genreService.save(genre);

        verify(genreRepository, times(1)).save(genre);
    }

    @Test
    public void testSaveThrowNotFoundExceptionIfNotExistsById() {
        Genre genre = new Genre();
        genre.setId(anyInt());
        when(genreRepository.existsById(genre.getId())).thenReturn(false);

        assertThrows(NotFoundException.class, () -> genreService.save(genre));
    }

    @Test
    public void testSaveThrowBadRequestExceptionIfNotExistsByName() {
        Genre genre = new Genre();
        genre.setName(anyString());
        when(genreRepository.existsByNameIgnoreCase(genre.getName())).thenReturn(true);

        assertThrows(BadRequestException.class, () -> genreService.save(genre));
    }

    @Test
    public void testDeleteVerifyDelete1Time() {
        Genre genre = new Genre();
        genre.setId(anyInt());
        genre.setGenreMovies(Set.of());

        when(genreRepository.findWithMoviesById(genre.getId())).thenReturn(Optional.of(genre));
        genreService.delete(genre.getId());

        verify(genreRepository, times(1)).deleteById(genre.getId());
    }

    @Test
    public void testDeleteThrowNotFoundExceptionIfNotFindWithMoviesById() {
        Genre genre = new Genre();
        genre.setId(anyInt());
        when(genreRepository.findWithMoviesById(genre.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> genreService.delete(genre.getId()));
    }

    @Test
    public void testDeleteThrowBadRequestExceptionIfGenreGetMoviesNotEmpty() {
        Genre genre = new Genre();
        genre.setId(anyInt());
        genre.setGenreMovies(Set.of(new Movie()));

        when(genreRepository.findWithMoviesById(genre.getId())).thenReturn(Optional.of(genre));

        assertThrows(BadRequestException.class, () -> genreService.delete(genre.getId()));
    }
}
