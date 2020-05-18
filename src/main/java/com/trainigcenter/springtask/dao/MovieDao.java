package com.trainigcenter.springtask.dao;

import java.util.Set;
import com.trainigcenter.springtask.domain.Movie;

public interface MovieDao {

    public Movie findMovieById(int movieId);

    public Set<Movie> findAll();
}
