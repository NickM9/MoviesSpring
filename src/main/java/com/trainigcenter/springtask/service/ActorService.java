package com.trainigcenter.springtask.service;

import com.trainigcenter.springtask.domain.Actor;

import java.util.List;
import java.util.Optional;

public interface ActorService {

    List<Actor> getAll();

    Optional<Actor> getById(Integer id);

    Actor save(Actor actor);

    void delete(Integer id);
}
