package com.trainigcenter.springtask.dao;

import java.util.Set;

import com.trainigcenter.springtask.domain.Actor;
import com.trainigcenter.springtask.domain.Genre;

public interface ActorDao {

    public Actor findActorById(int actorId);
    public Set<Actor> findAll();
    public Actor findByActorName(String name);
    public void addActor(Actor actor);
    public Actor updateActor(Actor actor);
    public void deleteActor(Actor actor);

}
