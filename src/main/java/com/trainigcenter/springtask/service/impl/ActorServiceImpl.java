package com.trainigcenter.springtask.service.impl;

import com.trainigcenter.springtask.domain.Actor;
import com.trainigcenter.springtask.persistence.ActorDao;
import com.trainigcenter.springtask.service.ActorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ActorServiceImpl implements ActorService {

    private static final Logger logger = LogManager.getLogger(ActorServiceImpl.class);

    private final ActorDao actorDao;

    @Autowired
    public ActorServiceImpl(ActorDao actorDao) {
        this.actorDao = actorDao;
    }

    @Override
    public Actor getById(Integer id) {
        return actorDao.findById(id);
    }

    @Override
    public List<Actor> getAll() {
        return actorDao.findAll();
    }

    @Override
    public Actor getByName(String name) {
        return actorDao.findByName(name);
    }

    @Override
    @Transactional
    public Actor add(Actor actor) {
        Actor dbActor = actorDao.findByName(actor.getName());

        if (dbActor == null) {
            return actorDao.add(actor);
        }

        return dbActor;
    }

    @Override
    @Transactional
    public Actor update(Actor actor) {
        Actor dbActor = actorDao.findById(actor.getId());

        if (dbActor != null) {
            return actorDao.update(actor);
        }

        return dbActor;
    }

    @Override
    public Actor getByIdWithMovies(Integer id) {
        return actorDao.findByIdWithMovies(id);
    }

    @Override
    @Transactional
    public boolean delete(Actor actor) {

        if (!actor.getActorMovies().isEmpty()) {
            return false;
        }

        actorDao.delete(actor);
        return true;

    }

}
