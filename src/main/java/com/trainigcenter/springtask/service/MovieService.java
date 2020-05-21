package com.trainigcenter.springtask.service;

import com.trainigcenter.springtask.domain.Movie;

import java.util.List;

public interface MovieService {

    Movie getById(Integer id);

    List<Movie> getAll(int page, int size);

    Movie add(Movie movie);

    Movie update(Movie movie);

    void delete(Movie movie);

    List<Movie> getAllByGenre(String genre, int page, int size);

    List<Movie> getByRating(int rating, int page, int size);
}
