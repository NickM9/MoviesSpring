package com.trainigcenter.springtask.persistence;

import com.trainigcenter.springtask.domain.Movie;
import com.trainigcenter.springtask.domain.util.Pagination;

import java.util.List;
import java.util.Optional;

public interface MovieDao {

    Optional<Movie> findById(Integer id);

    Optional<List<Movie>> findMoviesByName(String name);

    Optional<Pagination<Movie>> findAll(int page, int size);

    Movie create(Movie movie);

    Movie update(Movie movie);

    void delete(Integer id);

    Optional<Pagination<Movie>> findAllByGenre(Integer genreId, int page, int size);
}
