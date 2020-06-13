package com.trainigcenter.springtask.service;

import com.trainigcenter.springtask.domain.Actor;

import java.util.List;
import java.util.Optional;

public interface ActorService {

    List<Actor> getAll();

    Optional<Actor> getById(Integer id);

    Actor create(Actor actor);

    Actor update(Actor actor, Integer id);

    Optional<Actor> getByIdWithMovies(Integer id);

    void delete(Integer id);
}
