package com.trainigcenter.springtask.service;

import com.trainigcenter.springtask.domain.Movie;

import java.util.List;
import java.util.Set;

public interface MovieService {

    Movie getMovieById(int movieId);

    List<Movie> getAll(int page);

    void addMovie(Movie movie);

    Movie updateMovie(Movie movie);

    void deleteMovie(Movie movie);

    Set<Movie> getAllFilterByGenre(String genre);

    List<Movie> getMostPopularMovies(int raiting);
}
