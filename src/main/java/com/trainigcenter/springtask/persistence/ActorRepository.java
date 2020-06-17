package com.trainigcenter.springtask.persistence;

import com.trainigcenter.springtask.domain.Actor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

    boolean existsByNameIgnoreCase(String name);

    @EntityGraph(attributePaths = {"actorMovies"})
    Optional<Actor> findWithMoviesById(Integer id);
}
