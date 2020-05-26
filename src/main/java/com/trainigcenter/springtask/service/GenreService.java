package com.trainigcenter.springtask.service;

import com.trainigcenter.springtask.domain.Genre;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface GenreService {

    Optional<Genre> getById(Integer id);

    List<Genre> getAll();

    Genre create(Genre genre);

    Genre update(Genre genre, int id);

    void delete(Integer id);

    Optional<Genre> getByIdWithMovies(Integer id);
}
