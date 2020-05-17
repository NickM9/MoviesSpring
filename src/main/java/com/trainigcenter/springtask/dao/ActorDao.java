package com.trainigcenter.springtask.dao;

import com.trainigcenter.springtask.domain.Actor;
import com.trainigcenter.springtask.domain.Genre;

public interface ActorDao {

    public Actor findActorById(int actorId);

}
