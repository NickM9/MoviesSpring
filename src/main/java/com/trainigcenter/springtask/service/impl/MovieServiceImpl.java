package com.trainigcenter.springtask.service.impl;

import com.trainigcenter.springtask.domain.Movie;
import com.trainigcenter.springtask.persistence.MovieDao;
import com.trainigcenter.springtask.service.MovieService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    private static final Logger logger = LogManager.getLogger(MovieServiceImpl.class);

    private final MovieDao movieDao;

    @Autowired
    public MovieServiceImpl(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    public Movie getById(Integer id) {
        return movieDao.findById(id);
    }

    @Override
    public List<Movie> getAll(int page, int size) {
        return movieDao.findAll(page, size);
    }

    @Override
    @Transactional
    public Movie add(Movie movie) {

        List<Movie> movies = movieDao.findMoviesByName(movie.getTitle());

        for (Movie m : movies) {
            if (movie.equals(m)) {
                return m;
            }
        }

        return movieDao.add(movie);
    }

    @Override
    @Transactional
    public Movie update(Movie movie) {

        Movie dbMovie = movieDao.findById(movie.getId());

        if (dbMovie != null) {
            return movieDao.update(movie);
        }

        return dbMovie;
    }

    @Override
    public void delete(Movie movie) {
        movieDao.delete(movie);

    }

    @Override
    public List<Movie> getAllByGenre(String genre, int page, int size) {
        return movieDao.findAllByGenre(genre, page, size);
    }

    @Override
    public List<Movie> getByRating(int rating, int page, int size) {
        return movieDao.findByRating(rating, page, size);
    }

}
