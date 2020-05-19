package com.trainigcenter.springtask.application.service.impl;

import com.trainigcenter.springtask.application.data.MovieDao;
import com.trainigcenter.springtask.application.domain.Movie;
import com.trainigcenter.springtask.application.service.MovieService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class MovieServiceImpl implements MovieService {

    private static final Logger logger = LogManager.getLogger(MovieServiceImpl.class);

    private final MovieDao movieDao;

    @Autowired
    public MovieServiceImpl(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    public Movie getMovieById(int movieId) {
        return movieDao.findMovieById(movieId);
    }

    @Override
    public List<Movie> getAll(int page) {
        return movieDao.findAll(page);
    }

    @Override
    public void addMovie(Movie movie) {

        Set<Movie> movies = movieDao.findMoviesByName(movie.getTitle());
        boolean unique = true;

        for (Movie m : movies) {
            if (movie.equals(m)) {
                unique = false;
                break;
            }
        }

        if (unique) {
            movieDao.addMovie(movie);
        }

    }

    @Override
    public Movie updateMovie(Movie movie) {

        Movie dbMovie = movieDao.findMovieById(movie.getId());

        if (dbMovie != null) {
            return movieDao.updateMovie(movie);
        }

        return dbMovie;
    }

    @Override
    public void deleteMovie(Movie movie) {
        movieDao.deleteMovie(movie);

    }

    @Override
    public Set<Movie> getAllFilterByGenre(String genre) {
        return movieDao.findAllFilterByGenre(genre);
    }

    @Override
    public List<Movie> getMostPopularMovies(int raiting) {
        return movieDao.findMostPopularMovies(raiting);
    }

}
