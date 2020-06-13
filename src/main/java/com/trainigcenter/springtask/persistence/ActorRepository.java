package com.trainigcenter.springtask.persistence;

import com.trainigcenter.springtask.domain.Actor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActorRepository extends JpaRepository<Actor, Integer> {

    Optional<Actor> findByNameIgnoreCase(String name);

    @EntityGraph(attributePaths = {"actorMovies"})
    Optional<Actor> findWithMoviesById(Integer id);
}
