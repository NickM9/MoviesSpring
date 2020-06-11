package com.trainigcenter.springtask.persistence;

import com.trainigcenter.springtask.domain.Actor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ActorRepository extends JpaRepository<Actor, Integer> {

    List<Actor> findAll();

    Optional<Actor> findById(Integer id);

    Optional<Actor> findByNameIgnoreCase(String name);

    Actor save(Actor actor);

    @EntityGraph(attributePaths = {"actorMovies"})
    Optional<Actor> findOneWithMoviesById(Integer id);

    void deleteById(Integer id);
}
