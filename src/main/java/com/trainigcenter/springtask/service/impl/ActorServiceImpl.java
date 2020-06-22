package com.trainigcenter.springtask.service.impl;

import com.trainigcenter.springtask.domain.Actor;
import com.trainigcenter.springtask.domain.exception.BadRequestException;
import com.trainigcenter.springtask.domain.exception.NotFoundException;
import com.trainigcenter.springtask.persistence.ActorRepository;
import com.trainigcenter.springtask.service.ActorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
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
    public Actor save(Actor actor) {
        Actor dbActor = null;

        if (actor.getId() != null) {
            dbActor = actorRepository.findById(actor.getId()).orElseThrow(() -> new NotFoundException("Actor id: " + actor.getId() + " not found"));
        }

        if ( (dbActor == null && actorRepository.existsByNameIgnoreCase(actor.getName()))
          || (dbActor != null && !actor.getName().equals(dbActor.getName())) ) {

            throw new BadRequestException("Actor name: " + actor.getName() + " already exists");
        }

        return actorRepository.save(actor);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Actor actor = actorRepository.findWithMoviesById(id).orElseThrow(() -> new NotFoundException("Actor id:" + id + " not found"));

        if (!actor.getActorMovies().isEmpty()) {
            throw new BadRequestException("Actor id: " + id + " take part in movies");
        }

        actorRepository.deleteById(actor.getId());
    }
}
