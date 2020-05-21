package com.trainigcenter.springtask.service;

import com.trainigcenter.springtask.domain.Actor;

import java.util.List;

public interface ActorService {

    Actor getById(Integer id);

    List<Actor> getAll();

    Actor getByName(String name);

    Actor add(Actor actor);

    Actor update(Actor actor);

    boolean delete(Actor actor);

    Actor getByIdWithMovies(Integer id);

}
