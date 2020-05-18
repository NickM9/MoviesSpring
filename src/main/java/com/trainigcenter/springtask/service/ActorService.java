package com.trainigcenter.springtask.service;

import java.util.Set;

import com.trainigcenter.springtask.domain.Actor;

public interface ActorService {

    public Actor getActorById(int actorId);
    
    public Set<Actor> getAll();

}
