package com.trainigcenter.springtask.dao;

import java.util.Set;

import com.trainigcenter.springtask.domain.Actor;

public interface ActorDao {

    public Actor findActorById(int actorId);
    
    public Set<Actor> findAll();

}
