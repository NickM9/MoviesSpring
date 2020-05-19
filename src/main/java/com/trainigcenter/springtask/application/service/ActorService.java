package com.trainigcenter.springtask.application.service;

import com.trainigcenter.springtask.application.domain.Actor;

import java.util.Set;

public interface ActorService {

    Actor getActorById(Integer actorId);

    Set<Actor> getAll();

    Actor getActorByName(String name);

    void addActor(Actor actor);

    Actor updateActor(Actor actor);

    boolean deleteActor(Actor actor);

    Actor getActorByIdWithMovies(Integer id);

}
