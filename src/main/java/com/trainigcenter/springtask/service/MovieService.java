package com.trainigcenter.springtask.service;

import com.trainigcenter.springtask.domain.Movie;
import com.trainigcenter.springtask.domain.util.Pagination;

import java.util.Optional;

public interface MovieService {

    Optional<Movie> getById(Integer id);

    Pagination<Movie> getAll(int page, int size);

    Movie create(Movie movie);

    Movie update(Movie movie, int id);

    void delete(Integer id);

    Pagination<Movie> getAllByGenre(Integer genreId, int page, int size);
}
