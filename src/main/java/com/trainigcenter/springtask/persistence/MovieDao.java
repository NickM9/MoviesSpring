package com.trainigcenter.springtask.persistence;

import com.trainigcenter.springtask.domain.Movie;

import java.util.List;

public interface MovieDao {

    Movie findById(Integer id);

    List<Movie> findMoviesByName(String name);

    List<Movie> findAll(int page, int size);

    //Integer findCount();

    Movie add(Movie movie);

    Movie update(Movie movie);

    void delete(Movie movie);

    List<Movie> findAllByGenre(String genre, int page, int size);

    List<Movie> findByRating(int rating, int page, int size);
}
