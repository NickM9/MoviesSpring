package com.trainigcenter.springtask.service.impl;

import com.trainigcenter.springtask.domain.Actor;
import com.trainigcenter.springtask.domain.Genre;
import com.trainigcenter.springtask.domain.Movie;
import com.trainigcenter.springtask.persistence.GenreRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GenreServiceImplTest {

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private GenreServiceImpl genreService;

    private Genre action;
    private Genre comedy;
    private Genre scienceFiction;
    private List<Genre> genres;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        action = new Genre();
        action.setId(1);
        action.setName("Action");

        comedy = new Genre();
        comedy.setId(2);
        comedy.setName("Comedy");

        scienceFiction = new Genre();
        scienceFiction.setId(3);
        scienceFiction.setName("Science fiction");

        genres = List.of(action, comedy, scienceFiction);
    }

    @Test
    public void getAllTest() {
        when(genreRepository.findAll()).thenReturn(genres);
        List<Genre> genresActual = genreService.getAll();

        assertEquals(genresActual, genres);
    }

    @Test
    public void getByIdTest() {
        when(genreRepository.findById(action.getId())).thenReturn(Optional.of(action));
        Genre genre = genreService.getById(action.getId()).get();

        assertNotNull(genre);
    }

    @Test
    public void createTest() {
        Genre war = new Genre();
        war.setName("war");

        when(genreRepository.save(war)).thenReturn(war);
        assertThat(genreService.create(war), is(notNullValue()));
    }

    @Test
    public void getByIdWithMoviesTest() {
        Movie movie = new Movie();
        movie.setId(1);
        movie.setTitle("The Avengers");
        movie.setDescription("Avengers description. Very good!");
        movie.setDuration(Duration.ofSeconds(8520));
        movie.setGenres(new HashSet<>(genres));

        Actor robertDowney = new Actor();
        robertDowney.setId(1);
        robertDowney.setName("Robert Downey Jr.");
        robertDowney.setBirthYear(1965);

        Actor chrisEvans = new Actor();
        chrisEvans.setId(2);
        chrisEvans.setName("Chris Evans");
        chrisEvans.setBirthYear(1981);

        Actor chrisHemsworth = new Actor();
        chrisHemsworth.setId(3);
        chrisHemsworth.setName("Chris Hemsworth");
        chrisHemsworth.setBirthYear(1983);

        movie.setActors(Set.of(robertDowney, chrisEvans, chrisHemsworth));
        action.setGenreMovies(Set.of(movie));

        when(genreRepository.findWithMoviesById(action.getId())).thenReturn(Optional.of(action));
        Genre genre = genreService.getByIdWithMovies(action.getId()).get();

        assertNotNull(genre);
    }

    @Test
    public void deleteTest() {
        Genre genre = new Genre();
        genre.setId(4);
        genre.setName("war");
        genre.setGenreMovies(Set.of());

        when(genreRepository.findWithMoviesById(genre.getId())).thenReturn(Optional.of(genre));

        genreService.delete(genre.getId());

        verify(genreRepository, times(1)).deleteById(genre.getId());
    }
}
