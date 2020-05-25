package com.trainigcenter.springtask.service.impl;

import com.trainigcenter.springtask.domain.Actor;
import com.trainigcenter.springtask.persistence.ActorDao;
import com.trainigcenter.springtask.service.ActorService;
import com.trainigcenter.springtask.web.exception.MethodNotAllowedException;
import com.trainigcenter.springtask.web.exception.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ActorServiceImpl implements ActorService {

    private static final Logger logger = LogManager.getLogger(ActorServiceImpl.class);

    private final ActorDao actorDao;

    @Autowired
    public ActorServiceImpl(ActorDao actorDao) {
        this.actorDao = actorDao;
    }

    @Override
    public Optional<Actor> getById(Integer id) {
        return actorDao.findById(id);
    }

    @Override
    public List<Actor> getAll() {
        return actorDao.findAll();
    }

    @Override
    public Optional<Actor> getByName(String name) {
        return actorDao.findByName(name);
    }

    @Override
    @Transactional
    public Actor create(Actor actor) {
        Optional<Actor> dbActor = actorDao.findByName(actor.getName());

        if (dbActor.isPresent()) {
            throw new MethodNotAllowedException("Actor name:" + actor.getName() + " already exists with id: " + dbActor.get().getId());
        }

        return actorDao.create(actor);
    }

    @Override
    @Transactional
    public Actor update(Actor actor) {
        Optional<Actor> dbActor = actorDao.findById(actor.getId());
        dbActor.orElseThrow(() -> new NotFoundException("Actor id:" + actor.getId() + " not found"));

        return actorDao.update(actor);
    }

    @Override
    public Optional<Actor> getByIdWithMovies(Integer id) {
        return actorDao.findByIdWithMovies(id);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Actor actor = actorDao.findByIdWithMovies(id).get();

        if (!actor.getActorMovies().isEmpty()) {
            throw new MethodNotAllowedException("Actor id:" + id + " take part in movies");
        }

        actorDao.delete(actor.getId());
    }

}
