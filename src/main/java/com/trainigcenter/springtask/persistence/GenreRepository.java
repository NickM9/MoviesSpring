package com.trainigcenter.springtask.persistence;

import com.trainigcenter.springtask.domain.Genre;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {

    boolean existsByNameIgnoreCase(String name);

    @EntityGraph(attributePaths = {"genreMovies"})
    Optional<Genre> findWithMoviesById(Integer id);
}
