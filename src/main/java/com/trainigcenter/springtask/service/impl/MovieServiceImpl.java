package com.trainigcenter.springtask.service.impl;

import com.trainigcenter.springtask.domain.Movie;
import com.trainigcenter.springtask.domain.util.Pagination;
import com.trainigcenter.springtask.persistence.MovieDao;
import com.trainigcenter.springtask.service.MovieService;
import com.trainigcenter.springtask.web.exception.MethodNotAllowedException;
import com.trainigcenter.springtask.web.exception.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    private static final Logger logger = LogManager.getLogger(MovieServiceImpl.class);

    private final MovieDao movieDao;

    @Autowired
    public MovieServiceImpl(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Override
    public Optional<Movie> getById(Integer id) {
        return movieDao.findById(id);
    }

    @Override
    public Pagination<Movie> getAll(int page, int size) {
        return movieDao.findAll(page, size);
    }

    @Override
    @Transactional
    public Movie create(Movie movie) {

        List<Movie> movies = movieDao.findMoviesByName(movie.getTitle());

        for (Movie m : movies) {
            if (movie.equals(m)) {
                throw new MethodNotAllowedException("Movie name:" + m.getTitle() + " already exists with id: " + m.getId());
            }
        }

        return movieDao.create(movie);
    }

    @Override
    @Transactional
    public Movie update(Movie movie) {
        Optional<Movie> dbMovie = movieDao.findById(movie.getId());
        dbMovie.orElseThrow(() -> new NotFoundException("Movie id:" + movie.getId() + " not found"));

        return movieDao.update(movie);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        movieDao.delete(id);
    }

    @Override
    public Pagination<Movie> getAllByGenre(Integer genreId, int page, int size) {
        return movieDao.findAllByGenre(genreId, page, size);
    }

}
