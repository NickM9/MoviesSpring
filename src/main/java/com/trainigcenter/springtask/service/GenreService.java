package com.trainigcenter.springtask.service;

import com.trainigcenter.springtask.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {

    List<Genre> getAll();

    Optional<Genre> getById(Integer id);

    Genre create(Genre genre);

    Genre update(Genre genre, Integer id);

    Optional<Genre> getByIdWithMovies(Integer id);

    void delete(Integer id);
}
