package com.trainigcenter.springtask.persistence;

import com.trainigcenter.springtask.domain.Genre;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface GenreDao {

    Optional<Genre> findById(Integer id);

    Optional<Genre> findByName(String name);

    List<Genre> findAll();

    Genre create(Genre genre);

    Genre update(Genre genre);

    void delete(Integer id);

    Optional<Genre> findByIdWithMovies(Integer id);
}
