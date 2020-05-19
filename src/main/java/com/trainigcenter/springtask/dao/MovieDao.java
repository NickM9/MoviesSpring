package com.trainigcenter.springtask.dao;

import com.trainigcenter.springtask.domain.Movie;

import java.util.List;
import java.util.Set;

public interface MovieDao {

    Movie findMovieById(int movieId);

    Set<Movie> findMoviesByName(String name);

    List<Movie> findAll(int page);

    void addMovie(Movie movie);

    Movie updateMovie(Movie movie);

    void deleteMovie(Movie movie);

    Set<Movie> findAllFilterByGenre(String genre);

    List<Movie> findMostPopularMovies(int raiting);
}
