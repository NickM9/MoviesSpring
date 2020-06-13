package com.trainigcenter.springtask.service.impl;

import com.trainigcenter.springtask.domain.Actor;
import com.trainigcenter.springtask.persistence.ActorRepository;
import com.trainigcenter.springtask.service.ActorService;
import com.trainigcenter.springtask.web.exception.MethodNotAllowedException;
import com.trainigcenter.springtask.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class ActorServiceImpl implements ActorService {

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
    public Actor update(Actor actor, Integer id) {
        actor.setId(id);

        actorRepository.findById(actor.getId())
                       .orElseThrow(() -> new NotFoundException("Actor id: " + id + " not found"));

        Optional<Actor> dbActor = actorRepository.findByNameIgnoreCase(actor.getName());

        if (dbActor.isPresent()) {
            throw new MethodNotAllowedException("Actor name: " + dbActor.get().getName() + " already exists with id: " + dbActor.get().getId());
        }

        return actorRepository.save(actor);
    }

    @Override
    public Optional<Actor> getByIdWithMovies(Integer id) {
        return actorRepository.findWithMoviesById(id);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Actor actor = actorRepository.findWithMoviesById(id).get();

        if (!actor.getActorMovies().isEmpty()) {
            throw new MethodNotAllowedException("Actor id: " + id + " take part in movies");
        }

        actorRepository.deleteById(actor.getId());
    }

}
