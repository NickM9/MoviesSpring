package com.trainigcenter.springtask.service.impl;

import com.trainigcenter.springtask.domain.Actor;
import com.trainigcenter.springtask.domain.Movie;
import com.trainigcenter.springtask.domain.exception.BadRequestException;
import com.trainigcenter.springtask.domain.exception.NotFoundException;
import com.trainigcenter.springtask.persistence.ActorRepository;
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

public class ActorServiceImplTest {

    @Mock
    private ActorRepository actorRepository;

    @InjectMocks
    private ActorServiceImpl actorService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllVerifyFindAll1Time() {
        when(actorRepository.findAll()).thenReturn(List.of());
        actorService.getAll();

        verify(actorRepository, times(1)).findAll();
    }

    @Test
    public void testGetByIdVerifyFindById1Time() {
        int id = anyInt();
        when(actorRepository.findById(id)).thenReturn(Optional.of(new Actor()));
        actorService.getById(id);

        verify(actorRepository, times(1)).findById(id);
    }

    @Test
    public void testSaveVerifySave1Time() {
        Actor actor = new Actor();
        actorService.save(actor);

        verify(actorRepository, times(1)).save(actor);
    }

    @Test
    public void testSaveThrowNotFoundExceptionIfExistsByIdTrue() {
        Actor actor = new Actor();
        actor.setId(anyInt());
        when(actorRepository.existsById(actor.getId())).thenReturn(false);

        assertThrows(NotFoundException.class, () -> actorService.save(actor));
    }

    @Test
    public void testSaveThrowBadRequestExceptionIfExistsByNameTrue() {
        Actor actor = new Actor();
        actor.setName(anyString());
        when(actorRepository.existsByNameIgnoreCase(actor.getName())).thenReturn(true);

        assertThrows(BadRequestException.class, () -> actorService.save(actor));
    }

    @Test
    public void testDeleteVerifyDelete1Time() {
        Actor actor = new Actor();
        actor.setId(anyInt());
        actor.setActorMovies(Set.of());

        when(actorRepository.findWithMoviesById(actor.getId())).thenReturn(Optional.of(actor));
        actorService.delete(actor.getId());

        verify(actorRepository, times(1)).deleteById(actor.getId());
    }

    @Test
    public void testDeleteThrowNotFoundExceptionIfFindWithMoviesByIdOptionalEmpty() {
        Actor actor = new Actor();
        actor.setId(anyInt());
        when(actorRepository.findWithMoviesById(actor.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> actorService.delete(actor.getId()));
    }

    @Test
    public void testDeleteThrowBadRequestExceptionIfGenreGetMoviesNotEmpty() {
        Actor actor = new Actor();
        actor.setId(anyInt());
        actor.setActorMovies(Set.of(new Movie()));

        when(actorRepository.findWithMoviesById(actor.getId())).thenReturn(Optional.of(actor));

        assertThrows(BadRequestException.class, () -> actorService.delete(actor.getId()));
    }
}
