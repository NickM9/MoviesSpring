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

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    public void testGetAll() {
        Genre genre = new Genre();
        List<Genre> expected = List.of(genre);

        when(genreRepository.findAll()).thenReturn(expected);
        List<Genre> actual = genreService.getAll();

        assertEquals(expected, actual);
    }

    @Test
    public void testGetById() {
        Genre expected = new Genre();
        expected.setId(anyInt());

        when(genreRepository.findById(expected.getId())).thenReturn(Optional.of(expected));
        Genre actual = genreService.getById(expected.getId()).get();

        assertEquals(expected, actual);
    }

    @Test
    public void testSaveNew() {
        Genre expected = new Genre();
        expected.setName(anyString());

        when(genreRepository.existsByNameIgnoreCase(expected.getName())).thenReturn(false);
        when(genreRepository.save(expected)).thenReturn(expected);
        Genre actual = genreService.save(expected);

        assertEquals(expected, actual);
    }

    @Test
    public void testSaveUpdate() {
        Genre expected = new Genre();
        expected.setId(anyInt());
        when(genreRepository.existsById(expected.getId())).thenReturn(true);

        expected.setName(anyString());
        when(genreRepository.existsByNameIgnoreCase(expected.getName())).thenReturn(false);

        when(genreRepository.save(expected)).thenReturn(expected);
        Genre actual = genreService.save(expected);

        assertEquals(expected, actual);
    }

    @Test
    public void testSaveThrowNotFoundException() {
        Genre genre = new Genre();
        genre.setId(anyInt());
        when(genreRepository.existsById(genre.getId())).thenReturn(false);

        assertThrows(NotFoundException.class, () -> genreService.save(genre));
    }

    @Test
    public void testSaveThrowBadRequestException() {
        Genre genre = new Genre();
        genre.setName(anyString());

        when(genreRepository.existsByNameIgnoreCase(genre.getName())).thenReturn(true);
        when(genreRepository.save(genre)).thenReturn(genre);

        assertThrows(BadRequestException.class, () -> genreService.save(genre));
    }

    @Test
    public void testDeleteVerify1Times() {
        Genre genre = new Genre();
        genre.setId(anyInt());
        genre.setGenreMovies(Set.of());

        when(genreRepository.findWithMoviesById(genre.getId())).thenReturn(Optional.of(genre));
        genreService.delete(genre.getId());

        verify(genreRepository, times(1)).deleteById(genre.getId());
    }

    @Test
    public void testDeleteThrowNotFoundException() {
        Genre genre = new Genre();
        genre.setId(anyInt());

        when(genreRepository.findWithMoviesById(genre.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> genreService.delete(genre.getId()));
    }

    @Test
    public void testDeleteThrowBadRequestException() {
        Genre genre = new Genre();
        genre.setId(anyInt());
        genre.setGenreMovies(Set.of(new Movie()));

        when(genreRepository.findWithMoviesById(genre.getId())).thenReturn(Optional.of(genre));

        assertThrows(BadRequestException.class, () -> genreService.delete(genre.getId()));
    }
}
