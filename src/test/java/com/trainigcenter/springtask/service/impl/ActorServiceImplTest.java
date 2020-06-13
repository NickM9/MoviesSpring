package com.trainigcenter.springtask.service.impl;

import com.trainigcenter.springtask.domain.Actor;
import com.trainigcenter.springtask.domain.Genre;
import com.trainigcenter.springtask.domain.Movie;
import com.trainigcenter.springtask.persistence.ActorRepository;
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

public class ActorServiceImplTest {

    @Mock
    private ActorRepository actorRepository;

    @InjectMocks
    private ActorServiceImpl actorService;

    private Actor robertDowney;
    private Actor chrisEvans;
    private Actor chrisHemsworth;
    private List<Actor> actors;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        robertDowney = new Actor();
        robertDowney.setId(1);
        robertDowney.setName("Robert Downey Jr.");
        robertDowney.setBirthYear(1965);

        chrisEvans = new Actor();
        chrisEvans.setId(2);
        chrisEvans.setName("Chris Evans");
        chrisEvans.setBirthYear(1981);

        chrisHemsworth = new Actor();
        chrisHemsworth.setId(3);
        chrisHemsworth.setName("Chris Hemsworth");
        chrisHemsworth.setBirthYear(1983);

        actors = List.of(robertDowney, chrisEvans, chrisHemsworth);
    }

    @Test
    public void getAllTest() {
        when(actorRepository.findAll()).thenReturn(actors);
        List<Actor> genresActual = actorService.getAll();

        assertEquals(genresActual, actors);
    }

    @Test
    public void getByIdTest() {
        when(actorRepository.findById(robertDowney.getId())).thenReturn(Optional.of(robertDowney));
        Actor actor = actorService.getById(robertDowney.getId()).get();

        assertNotNull(actor);
    }

    @Test
    public void createTest() {
        Actor actor = new Actor();
        actor.setName("test actor");
        actor.setBirthYear(1998);

        when(actorRepository.save(actor)).thenReturn(actor);
        assertThat(actorService.create(actor), is(notNullValue()));
    }

    @Test
    public void getByIdWithMoviesTest() {
        Movie movie = new Movie();
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
        movie.setActors(new HashSet<>(actors));

        robertDowney.setActorMovies(Set.of(movie));

        when(actorRepository.findWithMoviesById(robertDowney.getId())).thenReturn(Optional.of(robertDowney));
        Actor actor = actorService.getByIdWithMovies(robertDowney.getId()).get();

        assertNotNull(actor);
    }

    @Test
    public void deleteTest() {
        Actor actor = new Actor();
        actor.setId(4);
        actor.setName("test");
        actor.setBirthYear(1998);
        actor.setActorMovies(Set.of());

        when(actorRepository.findWithMoviesById(actor.getId())).thenReturn(Optional.of(actor));

        actorService.delete(actor.getId());

        verify(actorRepository, times(1)).deleteById(actor.getId());
    }
}
