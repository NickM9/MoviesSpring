package com.trainigcenter.springtask.service.impl;

import com.trainigcenter.springtask.domain.Movie;
import com.trainigcenter.springtask.persistence.MovieRepository;
import com.trainigcenter.springtask.service.MovieService;
import com.trainigcenter.springtask.web.exception.MethodNotAllowedException;
import com.trainigcenter.springtask.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private static final Logger logger = LogManager.getLogger(MovieServiceImpl.class);

    private final MovieRepository movieRepository;

    @Override
    public Page<Movie> getAll(Pageable pageable) {
        return movieRepository.findAll(pageable);
    }

    @Override
    public Optional<Movie> getById(Integer id) {
        return movieRepository.findById(id);
    }

    @Override
    @Transactional
    public Movie create(Movie movie) {
        movie.setId(null);

        List<Movie> movies = movieRepository.findMoviesByTitle(movie.getTitle());

        for (Movie m : movies) {
            if (movie.equals(m)) {
                throw new MethodNotAllowedException("Movie name:" + m.getTitle() + " already exists with id: " + m.getId());
            }
        }

        return movieRepository.save(movie);
    }

    @Override
    @Transactional
    public Movie update(Movie movie, int id) {
        movie.setId(id);

        Optional<Movie> dbMovie = movieRepository.findById(movie.getId());
        dbMovie.orElseThrow(() -> new NotFoundException("Movie id: " + movie.getId() + " not found"));

        return movieRepository.save(movie);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        movieRepository.deleteById(id);
    }
}
