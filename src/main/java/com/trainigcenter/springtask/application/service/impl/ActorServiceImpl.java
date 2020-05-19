package com.trainigcenter.springtask.application.service.impl;

import com.trainigcenter.springtask.application.data.ActorDao;
import com.trainigcenter.springtask.application.domain.Actor;
import com.trainigcenter.springtask.application.service.ActorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ActorServiceImpl implements ActorService {

    private static final Logger logger = LogManager.getLogger(ActorServiceImpl.class);

    private final ActorDao actorDao;

    @Autowired
    public ActorServiceImpl(ActorDao actorDao) {
        this.actorDao = actorDao;
    }

    @Override
    public Actor getActorById(Integer actorId) {
        return actorDao.findActorById(actorId);
    }

    @Override
    public Set<Actor> getAll() {
        return actorDao.findAll();
    }

    @Override
    public Actor getActorByName(String name) {
        return actorDao.findByActorName(name);
    }

    @Override
    public void addActor(Actor actor) {
        Actor dbActor = actorDao.findByActorName(actor.getName());

        if (dbActor == null) {
            actorDao.addActor(actor);
        }
    }

    @Override
    public Actor updateActor(Actor actor) {
        Actor dbActor = actorDao.findActorById(actor.getId());

        if (dbActor != null) {
            return actorDao.updateActor(actor);
        }

        return dbActor;
    }

    public Actor getActorByIdWithMovies(Integer id) {
        return actorDao.findByActorIdWithMovies(id);
    }

    @Override
    public boolean deleteActor(Actor actor) {

        if (!actor.getActorMovies().isEmpty()) {
            return false;
        }

        actorDao.deleteActor(actor);
        return true;

    }

}
