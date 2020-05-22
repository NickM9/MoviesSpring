package com.trainigcenter.springtask.service;

import com.trainigcenter.springtask.domain.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    Optional<Movie> getById(Integer id);

    Optional<List<Movie>> getAll(int page, int size);

    Movie add(Movie movie);

    Movie update(Movie movie);

    void delete(Integer id);

    Optional<List<Movie>> getAllByGenre(String genre, int page, int size);
}
