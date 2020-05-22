package com.trainigcenter.springtask.service;

import com.trainigcenter.springtask.domain.Actor;

import java.util.List;
import java.util.Optional;

public interface ActorService {

    Optional<Actor> getById(Integer id);

    Optional<List<Actor>> getAll();

    Optional<Actor> getByName(String name);

    Actor add(Actor actor);

    Actor update(Actor actor);

    void delete(Integer id);

    Optional<Actor> getByIdWithMovies(Integer id);

}
