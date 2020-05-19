package com.trainigcenter.springtask.dao;

import com.trainigcenter.springtask.domain.Actor;

import java.util.Set;

public interface ActorDao {

    Actor findActorById(int actorId);

    Set<Actor> findAll();

    Actor findByActorName(String name);

    void addActor(Actor actor);

    Actor updateActor(Actor actor);

    void deleteActor(Actor actor);

    Actor findByActorIdWithMovies(Integer id);

}
