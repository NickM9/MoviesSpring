package com.trainigcenter.springtask.service;

import com.trainigcenter.springtask.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {

    List<Genre> getAll();

    Optional<Genre> getById(Integer id);

    Genre save(Genre genre);

    void delete(Integer id);
}
