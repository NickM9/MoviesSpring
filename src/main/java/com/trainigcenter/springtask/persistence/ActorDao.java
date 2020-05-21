package com.trainigcenter.springtask.persistence;

import com.trainigcenter.springtask.domain.Actor;

import java.util.List;

public interface ActorDao {

    Actor findById(Integer id);

    List<Actor> findAll();

    Actor findByName(String name);

    Actor add(Actor actor);

    Actor update(Actor actor);

    void delete(Actor actor);

    Actor findByIdWithMovies(Integer id);

}
