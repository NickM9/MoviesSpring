package com.trainigcenter.springtask.service.impl;

import com.trainigcenter.springtask.domain.Actor;
import com.trainigcenter.springtask.domain.Genre;
import com.trainigcenter.springtask.domain.Movie;
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

import java.time.Duration;
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

public class MovieServiceImplTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieServiceImpl movieService;

    private Movie movie;
    private List<Movie> movies;
    private Pageable pageable;
    private Page page;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        movie = new Movie();
        movie.setId(1);
        movie.setTitle("The Avengers");
        movie.setDescription("Avengers description. Very good!");
        movie.setDuration(Duration.ofSeconds(8520));

        Genre action = new Genre();
        action.setId(1);
        action.setName("Action");

        Genre comedy = new Genre();
        comedy.setId(2);
        comedy.setName("Comedy");

        Genre scienceFiction = new Genre();
        scienceFiction.setId(3);
        scienceFiction.setName("Science fiction");

        movie.setGenres(Set.of(action, comedy, scienceFiction));

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

        movies = List.of(movie);
        pageable = PageRequest.of(0, 2);
        page = new PageImpl(movies);
    }

    @Test
    public void getAllTest() {
        when(movieRepository.findAll(pageable)).thenReturn(page);
        List<Movie> moviesActual = movieService.getAll(pageable).getContent();

        assertEquals(moviesActual, movies);
    }

    @Test
    public void getByIdTest() {
        when(movieRepository.findById(movie.getId())).thenReturn(Optional.of(movie));
        Movie m = movieService.getById(movie.getId()).get();

        assertNotNull(m);
    }

    @Test
    public void createTest() {
        Movie m = new Movie();
        m.setTitle("title");
        m.setDescription("description");
        m.setDuration(Duration.ofSeconds(8000));
        m.setActors(Set.of());
        m.setGenres(Set.of());

        when(movieRepository.save(m)).thenReturn(m);
        assertThat(movieService.create(m), is(notNullValue()));
    }

    @Test
    public void deleteTest() {
        Movie m = new Movie();
        m.setId(999);
        m.setTitle("title");
        m.setDescription("description");
        m.setDuration(Duration.ofSeconds(8000));
        m.setActors(Set.of());
        m.setGenres(Set.of());

        movieService.delete(m.getId());
        verify(movieRepository, times(1)).deleteById(m.getId());
    }
}
