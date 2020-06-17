package com.trainigcenter.springtask.service;

import com.trainigcenter.springtask.domain.Movie;
import com.trainigcenter.springtask.domain.Pagination;

import java.util.Optional;

public interface MovieService {

    Pagination<Movie> getAll(int page, int size);

    Pagination<Movie> getAllByGenre(int page, int size, int genreId);

    Optional<Movie> getById(Integer id);

    Movie save(Movie movie);

    void delete(Integer id);
}
