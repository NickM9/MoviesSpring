package com.trainigcenter.springtask.persistence;

import com.trainigcenter.springtask.domain.Genre;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {

    List<Genre> findAll();

    Optional<Genre> findById(Integer id);

    Optional<Genre> findByNameIgnoreCase(String name);

    Genre save(Genre genre);

    @EntityGraph(attributePaths = {"genreMovies"})
    Optional<Genre> findWithMoviesById(Integer id);

    void deleteById(Integer id);
}
