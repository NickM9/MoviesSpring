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

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    public void testGetAll() {
        Actor actor = new Actor();
        List<Actor> expected = List.of(actor);

        when(actorRepository.findAll()).thenReturn(expected);
        List<Actor> actual = actorService.getAll();

        assertEquals(expected, actual);
    }

    @Test
    public void testGetById() {
        Actor expected = new Actor();
        expected.setId(anyInt());

        when(actorRepository.findById(expected.getId())).thenReturn(Optional.of(expected));
        Actor actual = actorService.getById(expected.getId()).get();

        assertEquals(expected, actual);
    }

    @Test
    public void testSaveNew() {
        Actor expected = new Actor();
        expected.setName(anyString());

        when(actorRepository.existsByNameIgnoreCase(expected.getName())).thenReturn(false);
        when(actorService.save(expected)).thenReturn(expected);
        Actor actual = actorService.save(expected);

        assertEquals(expected, actual);
    }

    @Test
    public void testSaveUpdate() {
        Actor expected = new Actor();
        expected.setId(anyInt());
        when(actorRepository.existsById(expected.getId())).thenReturn(true);

        expected.setName(anyString());
        when(actorRepository.existsByNameIgnoreCase(expected.getName())).thenReturn(false);

        when(actorRepository.save(expected)).thenReturn(expected);
        Actor actual = actorService.save(expected);

        assertEquals(expected, actual);
    }

    @Test
    public void testSaveThrowNotFoundException() {
        Actor actor = new Actor();
        actor.setId(anyInt());
        when(actorRepository.existsById(actor.getId())).thenReturn(false);

        assertThrows(NotFoundException.class, () -> actorService.save(actor));
    }

    @Test
    public void testSaveThrowBadRequestException() {
        Actor actor = new Actor();
        actor.setName(anyString());

        when(actorRepository.existsByNameIgnoreCase(actor.getName())).thenReturn(true);
        when(actorRepository.save(actor)).thenReturn(actor);

        assertThrows(BadRequestException.class, () -> actorService.save(actor));
    }

    @Test
    public void testDeleteVerify1Times() {
        Actor actor = new Actor();
        actor.setId(anyInt());
        actor.setActorMovies(Set.of());

        when(actorRepository.findWithMoviesById(actor.getId())).thenReturn(Optional.of(actor));
        actorService.delete(actor.getId());

        verify(actorRepository, times(1)).deleteById(actor.getId());
    }

    @Test
    public void testDeleteThrowNotFoundException() {
        Actor actor = new Actor();
        actor.setId(anyInt());

        when(actorRepository.findWithMoviesById(actor.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> actorService.delete(actor.getId()));
    }

    @Test
    public void testDeleteThrowBadRequestException() {
        Actor actor = new Actor();
        actor.setId(anyInt());
        actor.setActorMovies(Set.of(new Movie()));

        when(actorRepository.findWithMoviesById(actor.getId())).thenReturn(Optional.of(actor));

        assertThrows(BadRequestException.class, () -> actorService.delete(actor.getId()));
    }
}
