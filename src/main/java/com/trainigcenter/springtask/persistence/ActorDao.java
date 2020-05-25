package com.trainigcenter.springtask.persistence;

import com.trainigcenter.springtask.domain.Actor;

import java.util.List;
import java.util.Optional;

public interface ActorDao {

    Optional<Actor> findById(Integer id);

    Optional<List<Actor>> findAll();

    Optional<Actor> findByName(String name);

    Actor create(Actor actor);

    Actor update(Actor actor);

    void delete(Integer id);

    Optional<Actor> findByIdWithMovies(Integer id);

}
