package com.trainigcenter.springtask.service.impl;

import com.trainigcenter.springtask.domain.Actor;
import com.trainigcenter.springtask.persistence.ActorRepository;
import com.trainigcenter.springtask.service.ActorService;
import com.trainigcenter.springtask.web.exception.MethodNotAllowedException;
import com.trainigcenter.springtask.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActorServiceImpl implements ActorService {

    private static final Logger logger = LogManager.getLogger(ActorServiceImpl.class);

    private final ActorRepository actorRepository;

    @Override
    public List<Actor> getAll() {
        return actorRepository.findAll();
    }

    @Override
    public Optional<Actor> getById(Integer id) {
        return actorRepository.findById(id);
    }

    @Override
    @Transactional
    public Actor create(Actor actor) {
        actor.setId(null);

        Optional<Actor> dbActor = actorRepository.findByNameIgnoreCase(actor.getName());

        if (dbActor.isPresent()) {
            throw new MethodNotAllowedException("Actor name: " + dbActor.get().getName() + " already exists with id: " + dbActor.get().getId());
        }

        return actorRepository.save(actor);
    }

    @Override
    @Transactional
    public Actor update(Actor actor, int id) {
        actor.setId(id);

        Optional<Actor> dbActor = actorRepository.findById(actor.getId());
        dbActor.orElseThrow(() -> new NotFoundException("Actor id: " + dbActor.get().getId() + " not found"));

        return actorRepository.save(actor);
    }

    @Override
    public Optional<Actor> getByIdWithMovies(Integer id) {
        return actorRepository.findOneWithMoviesById(id);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Actor actor = actorRepository.findOneWithMoviesById(id).get();

        if (!actor.getActorMovies().isEmpty()) {
            throw new MethodNotAllowedException("Actor id: " + id + " take part in movies");
        }

        actorRepository.deleteById(actor.getId());
    }

}
