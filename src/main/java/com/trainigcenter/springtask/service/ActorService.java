package com.trainigcenter.springtask.service;

import java.util.Set;

import com.trainigcenter.springtask.domain.Actor;

public interface ActorService {

    public Actor getActorById(int actorId);
    public Set<Actor> getAll();
    public Actor getActorByName(String name);
    public void addActor(Actor actor);
    public Actor updateActor(Actor actor);
    public void deleteActor(Actor actor);

}
